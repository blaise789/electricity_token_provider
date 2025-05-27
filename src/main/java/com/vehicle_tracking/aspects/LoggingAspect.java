package com.vehicle_tracking.aspects;

import com.vehicle_tracking.annotations.Auditable;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    // Create a dedicated logger for audit messages
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT_LOGGER");
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for methods annotated with @Auditable
    @Pointcut("@annotation(auditable)")
    public void auditableMethod(Auditable auditable) {
    }

    // Pointcut for all public methods in your controller package (adjust package name)
    // This is an alternative if you don't want to use @Auditable everywhere
    @Pointcut("within(com.vehicle_tracking.controllers..*) && execution(public * *(..))")
    public void controllerMethods() {
    }


    @Around("auditableMethod(auditableAnnotation)") // Use this if using @Auditable
    // @Around("controllerMethods()") // Or use this for all controller methods
    public Object logUserOperation(ProceedingJoinPoint joinPoint, Auditable auditableAnnotation) throws Throwable {
        // public Object logUserOperation(ProceedingJoinPoint joinPoint) throws Throwable { // If using controllerMethods pointcut
        long startTime = System.currentTimeMillis();
        String username = getUsername();
        String action = auditableAnnotation.action(); // If using @Auditable
        // String action = joinPoint.getSignature().getName(); // If using controllerMethods pointcut, action could be method name
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();
        String remoteAddr = getRemoteAddr();

        // Log entry details
        auditLogger.info("[AUDIT_START] User: '{}', Action: '{}', Method: '{}', Args: {}, IP: {}",
                username, action, methodName, Arrays.toString(filterSensitiveArgs(methodArgs)), remoteAddr);

        Object result;
        try {
            result = joinPoint.proceed(); // Execute the actual method
            long elapsedTime = System.currentTimeMillis() - startTime;
            auditLogger.info("[AUDIT_SUCCESS] User: '{}', Action: '{}', Method: '{}', Result: {}, Duration: {}ms, IP: {}",
                    username, action, methodName, resultToString(result), elapsedTime, remoteAddr);
            return result;
        } catch (Throwable throwable) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            auditLogger.error("[AUDIT_FAILURE] User: '{}', Action: '{}', Method: '{}', Exception: {}, Duration: {}ms, IP: {}",
                    username, action, methodName, throwable.getMessage(), elapsedTime, remoteAddr, throwable);
            throw throwable; // Re-throw the exception
        }
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return "ANONYMOUS"; // For operations before login like signup or login attempt
    }

    private String getRemoteAddr() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getRemoteAddr();
        }
        return "UNKNOWN_IP";
    }

    private String resultToString(Object result) {
        if (result == null) {
            return "null";
        }
        // Avoid logging large collections or sensitive data directly from results
        // You might want to customize this based on what your methods return
        if (result.toString().length() > 200) { // Example limit
            return result.getClass().getSimpleName() + "[truncated]";
        }
        return result.toString();
    }

    private Object[] filterSensitiveArgs(Object[] args) {
        // Example: If you have a DTO for password, you might want to mask it
        // This is a simple example, you might need more sophisticated filtering
        return Arrays.stream(args)
                .map(arg -> {
                    if (arg instanceof String && ((String) arg).matches(".*password.*")) {
                        return "*******";
                    }
                    if (arg != null && arg.toString().contains("password")) { // very basic check
                        return arg.getClass().getSimpleName() + "[SENSITIVE_DATA_MASKED]";
                    }
                    return arg;
                })
                .toArray();
    }

    // If you want to log general method entry/exit for debugging (not for audit trail usually)
    @Before("execution(* com.vehicle_tracking.services.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.debug("ENTERING: {}.{}() with arguments: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.vehicle_tracking.services.*.*(..))", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        logger.debug("EXITING: {}.{}() with result: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                resultToString(result));
    }
}
