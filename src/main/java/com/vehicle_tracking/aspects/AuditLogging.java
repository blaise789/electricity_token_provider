package com.vehicle_tracking.aspects;
// package com.vehicle_tracking.aspects;

import com.fasterxml.jackson.databind.ObjectMapper; // For serializing args/result if complex
import com.vehicle_tracking.annotations.Auditable;
import com.vehicle_tracking.models.AuditLog;
import com.vehicle_tracking.repositories.AuditLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async; // To save logs asynchronously
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class AuditLogging {

    private static final Logger auditFileLogger = LoggerFactory.getLogger("AUDIT_LOGGER"); // For file-based audit
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private ObjectMapper objectMapper; // For better serialization of objects

    @Pointcut("@annotation(auditable)")
    public void auditableMethod(Auditable auditable) {}

    @Around("auditableMethod(auditableAnnotation)")
    public Object logUserOperation(ProceedingJoinPoint joinPoint, Auditable auditableAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        String username = getUsername();
        String action = auditableAnnotation.action();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();
        String remoteAddr = getRemoteAddr();
        String argsAsString = serializeArgs(methodArgs);

        auditFileLogger.info("[AUDIT_START] User: '{}', Action: '{}', Method: '{}', Args: {}, IP: {}",
                username, action, methodName, argsAsString, remoteAddr);

        Object result = null;
        String status = "FAILURE";
        String resultOrError = "";
        try {
            result = joinPoint.proceed();
            status = "SUCCESS";
            resultOrError = serializeResult(result);
            return result;
        } catch (Throwable throwable) {
            resultOrError = throwable.getClass().getName() + ": " + throwable.getMessage();
            auditFileLogger.error("[AUDIT_FAILURE] User: '{}', Action: '{}', Method: '{}', Exception: {}, IP: {}",
                    username, action, methodName, resultOrError, remoteAddr, throwable);
            throw throwable;
        } finally {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if ("SUCCESS".equals(status)) {
                auditFileLogger.info("[AUDIT_SUCCESS] User: '{}', Action: '{}', Method: '{}', Result: {}, Duration: {}ms, IP: {}",
                        username, action, methodName, resultOrError, elapsedTime, remoteAddr);
            }
            // Save to DB
            saveAuditLogAsync(LocalDateTime.now(), username, action, methodName, argsAsString, remoteAddr, status, resultOrError, elapsedTime);
        }
    }

    @Async // Make DB saving asynchronous so it doesn't slow down the main request
    public void saveAuditLogAsync(LocalDateTime timestamp, String username, String action, String methodName,
                                  String arguments, String ipAddress, String status, String resultOrError, Long durationMs) {
        try {
            AuditLog logEntry = new AuditLog(timestamp, username, action, methodName, arguments, ipAddress, status, resultOrError, durationMs);
            auditLogRepository.save(logEntry);
        } catch (Exception e) {
            logger.error("Failed to save audit log to database: {}", e.getMessage(), e);
        }
    }


    private String serializeArgs(Object[] args) {
        try {
            // Be careful with large objects or sensitive data.
            // You might want to filter or summarize.
            return objectMapper.writeValueAsString(Arrays.stream(args).map(this::filterSensitiveArg).toArray());
        } catch (Exception e) {
            return "Error serializing args: " + Arrays.toString(args);
        }
    }

    private Object filterSensitiveArg(Object arg) {
        // Customize this method extensively to mask or remove sensitive data
        if (arg instanceof String && ((String) arg).matches(".*password.*")) {
            return "********";
        }
        if (arg != null) {
            String argString = arg.toString();
            // Example: if arg is a request DTO that has a password field
            // if (arg instanceof UserRegistrationRequest) {
            //    ((UserRegistrationRequest) arg).setPassword("********");
            // }
            if (argString.toLowerCase().contains("password=") || argString.toLowerCase().contains("\"password\":")) {
                return arg.getClass().getSimpleName() + "[CONTAINS_SENSITIVE_DATA_MASKED]";
            }
        }
        return arg;
    }


    private String serializeResult(Object result) {
        if (result == null) return "null";
        try {
            // Again, be careful with large or sensitive results.
            String resStr = objectMapper.writeValueAsString(result);
            if (resStr.length() > 1000) { // Truncate very long results
                return resStr.substring(0, 997) + "...";
            }
            return resStr;
        } catch (Exception e) {
            return "Error serializing result: " + result.toString();
        }
    }


    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return "ANONYMOUS";
    }

    private String getRemoteAddr() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getRemoteAddr();
        }
        return "UNKNOWN_IP";
    }
}