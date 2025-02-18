package net.bounceme.chronos.rulemanager.config;


import java.net.URI;
import java.net.URISyntaxException;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EHCacheConfig {

    @Bean
    CacheManager cacheManager() throws URISyntaxException {
        // Cargar la configuración de Ehcache desde el archivo XML
        URI ehcacheConfigUri = getClass().getResource("/ehcache.xml").toURI();

        // Obtener el proveedor de caching de Ehcache
        CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");

        // Crear el CacheManager de JSR-107 usando la configuración de Ehcache
        javax.cache.CacheManager jsr107CacheManager = cachingProvider.getCacheManager(
            ehcacheConfigUri,
            getClass().getClassLoader()
        );

        // Envolver el CacheManager de JSR-107 en un JCacheCacheManager de Spring
        return new JCacheCacheManager(jsr107CacheManager);
    }
}

