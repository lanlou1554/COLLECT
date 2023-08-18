package com.seiii.collect.config;



import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
@Profile({"prod"})
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public EhCacheManagerFactoryBean ehCache(){
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setShared(true);
        bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return bean;
    }

    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cacheManager){
        EhCacheCacheManager manager = new EhCacheCacheManager();
        manager.setCacheManager(cacheManager);
        return manager;
    }

}
