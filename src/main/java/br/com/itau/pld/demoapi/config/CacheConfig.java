package br.com.itau.pld.demoapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Configuration
@EnableCaching
@ComponentScan
public class CacheConfig extends CachingConfigurerSupport {

    public static final String EHCACHE_XML = "ehcache.xml";

    public static final long ONE_DAY = 86400l;

    @Value("${jedis.host}")
    private String jedisHost;

    @Value("${jedis.port}")
    private int jedisPort;

    @Value("${jedis.password}")
    private String jedisPassword;

    @Value("${jedis.poolConfig.maxTotal}")
    private int jedisMaxTotal;

    @Value("${jedis.poolConfig.maxIdle}")
    private int jedisMaxIdle;

    @Value("${jedis.poolConfig.minIdle}")
    private int jedisMinIdle;

    @Value("${jedis.poolConfig.maxWaitMillis}")
    private int jedisMaxWaitMillis;

    @Value("${jedis.defaultTtl}")
    public long jedisDefaultTtl;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(getJedisConnectionFactory(jedisHost, jedisPassword, jedisPort, jedisMaxTotal, jedisMinIdle, jedisMaxIdle, jedisMaxWaitMillis));
        return template;
    }

    @Override
    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        for (Map.Entry<String, Long> cacheNameAndTimeout : getExpires().entrySet())
            cacheConfigurations.put(cacheNameAndTimeout.getKey(), RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                            Duration.ofSeconds(ONE_DAY)));

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(jedisDefaultTtl));

        RedisCacheManager redisCacheManager = RedisCacheManager
                        .builder(getJedisConnectionFactory(jedisHost, jedisPassword, jedisPort, jedisMaxTotal, jedisMinIdle, jedisMaxIdle, jedisMaxWaitMillis))
                        .cacheDefaults(redisCacheConfiguration)
                        .withInitialCacheConfigurations(cacheConfigurations)
                        .transactionAware()
                        .build();

        redisCacheManager.setTransactionAware(false);
        return redisCacheManager;
    }

    private Map<String, Long> getExpires() {
        Map<String, Long> expiresMap = new HashMap<>();
        expiresMap.put("ListaRestritaRedisCacheComponent::findById", ONE_DAY);
        return expiresMap;
    }

    public static JedisConnectionFactory getJedisConnectionFactory(String jedisHost, String jedisPassword, int jedisPort, int jedisMaxTotal, int jedisMinIdle, int jedisMaxIdle, int jedisMaxWaitMillis) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(jedisMaxTotal);
        poolConfig.setMinIdle(jedisMinIdle);
        poolConfig.setMaxIdle(jedisMaxIdle);
        poolConfig.setMaxWaitMillis(jedisMaxWaitMillis);

        final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setHostName(jedisHost);
        if (isNotBlank(jedisPassword)) {//homologacao
            jedisConnectionFactory.setPassword(jedisPassword);
        }
        jedisConnectionFactory.setConvertPipelineAndTxResults(false);
        jedisConnectionFactory.setPort(jedisPort);
        return jedisConnectionFactory;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Primary
    @Bean(name = "ehCacheManager")
    public CacheManager ehCacheManager() {
        return new EhCacheCacheManager(getEhCacheManagerFactoryBean().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean getEhCacheManagerFactoryBean() {
        final EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource(EHCACHE_XML));
        factoryBean.setShared(true);
        return factoryBean;
    }

}
