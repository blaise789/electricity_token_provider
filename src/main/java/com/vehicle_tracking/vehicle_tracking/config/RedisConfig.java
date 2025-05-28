package com.vehicle_tracking.vehicle_tracking.config;//package com.vehicle_tracking.vehicle_tracking.config; // Ensure this matches your package structure
//
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.cache.CacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//
//@Configuration
//public class RedisConfig {
//
//    /**
//     * Creates a pre-configured ObjectMapper instance specifically for Redis serialization.
//     * This ObjectMapper includes the JavaTimeModule for handling Java 8+ Date/Time types
//     * and disables writing dates as timestamps for better readability.
//     * It also enables default typing, which is crucial for deserializing complex objects
//     * stored in Redis, especially collections or polymorphic types.
//     *
//     * @return A configured ObjectMapper instance.
//     */
//    @Bean("redisObjectMapper") // Qualify the bean name if you have other ObjectMapper beans
//    @Primary // Consider making this primary if it should be the default for Redis
//    public ObjectMapper objectMapperForRedis() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        // Serialize LocalDate/LocalDateTime as "YYYY-MM-DD" / "YYYY-MM-DDTHH:mm:ss"
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        // Enable default typing. This stores the fully qualified class name of the
//        // serialized object in the JSON. It's important for correct deserialization,
//        // especially for generic types like PageImpl or collections of custom objects.
//        // LaissezFaireSubTypeValidator.instance allows all valid subtypes.
//        // For enhanced security in untrusted environments, you might configure a more restrictive validator.
//        objectMapper.activateDefaultTyping(
//                LaissezFaireSubTypeValidator.instance, // More permissive, suitable for trusted environments
//                ObjectMapper.DefaultTyping.NON_FINAL, // Serialize type for non-final classes
//                JsonTypeInfo.As.PROPERTY // Store type info as a property (e.g., "@class":"com.example.MyType")
//        );
//        return objectMapper;
//    }
//
//    /**
//     * Configures the RedisTemplate for general Redis operations.
//     * It uses StringRedisSerializer for keys and a GenericJackson2JsonRedisSerializer
//     * (configured with the custom redisObjectMapper) for values, hash keys, and hash values.
//     *
//     * @param redisConnectionFactory The Redis connection factory.
//     * @param redisObjectMapper      The custom ObjectMapper configured for Redis.
//     * @return A configured RedisTemplate instance.
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory,
//            ObjectMapper redisObjectMapper) { // Spring will inject the "redisObjectMapper" bean
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        StringRedisSerializer stringSerializer = new StringRedisSerializer();
//        GenericJackson2JsonRedisSerializer jacksonSerializer =
//                new GenericJackson2JsonRedisSerializer(redisObjectMapper);
//
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(jacksonSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(jacksonSerializer);
//
//        redisTemplate.afterPropertiesSet(); // Ensures an init method is called after properties are set
//        return redisTemplate;
//    }
//
//    /**
//     * Configures the CacheManager for Spring's caching abstraction (@Cacheable, @CachePut, etc.).
//     * It sets default cache configurations, including key/value serializers and TTL.
//     * Values are serialized using GenericJackson2JsonRedisSerializer with the custom redisObjectMapper.
//     *
//     * @param redisConnectionFactory The Redis connection factory.
//     * @param redisObjectMapper      The custom ObjectMapper configured for Redis.
//     * @return A configured CacheManager instance.
//     */
//    @Bean
//    public CacheManager cacheManager(
//            RedisConnectionFactory redisConnectionFactory,
//            ObjectMapper redisObjectMapper) { // Spring will inject the "redisObjectMapper" bean
//
//        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer =
//                new GenericJackson2JsonRedisSerializer(redisObjectMapper);
//
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
//                .entryTtl(Duration.ofMinutes(30)) // Default Time-To-Live for cache entries
//                .disableCachingNullValues(); // Optional: prevent caching of null values
//
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(config)
//                // Optional: Configure specific caches with different TTLs or configurations
//                // .withCacheConfiguration("users",
//                //    config.entryTtl(Duration.ofHours(1)))
//                // .withCacheConfiguration("products",
//                //    config.entryTtl(Duration.ofMinutes(10)))
//                .build();
//    }
//
//    /*
//     * Your previous ValueOperations bean was:
//     * @Bean
//     * public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate){
//     *     return redisTemplate.opsForValue();
//     * }
//     * This bean is specific to a RedisTemplate<String, String>.
//     * If you need ValueOperations for the RedisTemplate<String, Object> defined above,
//     * you would typically obtain it directly from the template instance where needed, e.g.:
//     *
//     * @Autowired
//     * private RedisTemplate<String, Object> redisTemplate;
//     *
//     * public void someMethod() {
//     *     ValueOperations<String, Object> ops = this.redisTemplate.opsForValue();
//     *     // use ops
//     * }
//     *
//     * Defining ValueOperations as a separate bean like this is less common unless you have
//     * a very specific use case or multiple differently typed RedisTemplates.
//     */
//}