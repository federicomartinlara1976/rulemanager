package net.bounceme.chronos.rulemanager.config;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.spring.remote.provider.SpringRemoteCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.bounceme.chronos.rulemanager.support.SimpleKeyMarshaller;

@Configuration
@EnableCaching
public class CacheConfig {
	
	@Value("${spring.cache.server}")
	private String cacheServer;
	
	@Value("${spring.cache.port}")
	private Integer cachePort;
	
	@Value("${infinispan.remote.auth-username}")
	private String authUserName;
	
	@Value("${infinispan.remote.auth-password}")
	private String authPassword;
	
	@Value("${infinispan.remote.auth-realm}")
	private String authRealm;
	
	@Value("${infinispan.remote.sasl-mechanism}")
	private String authMechanism;

//    @Bean
//    @SneakyThrows
//    public CacheManager cacheManager() {
//        CachingProvider cachingProvider = Caching.getCachingProvider(EhcacheCachingProvider.class.getName());
//        javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager(
//            getClass().getResource("/ehcache.xml").toURI(), getClass().getClassLoader());
//        return new JCacheCacheManager(cacheManager);
//    }

	@Bean
	public SpringRemoteCacheManager cacheManager() {
		// Configurar el RemoteCacheManager con los detalles de conexión
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer().host(cacheServer) // Cambia esto por la IP o el hostname de tu servidor Infinispan
				.port(cachePort); // Cambia esto por el puerto de tu servidor Infinispan

		builder.security().authentication().username(authUserName) // Usuario para autenticación
				.password(authPassword) // Contraseña para autenticación
				.realm(authRealm) // Cambia esto si es necesario
				.saslMechanism(authMechanism); // Mecanismo SASL de autenticación

		// Configurar el marshaller para utilizar JSON
        builder.marshaller(new SimpleKeyMarshaller());
		
		// Crear y devolver el RemoteCacheManager
		RemoteCacheManager remoteCacheManager = new RemoteCacheManager(builder.build());

		return new SpringRemoteCacheManager(remoteCacheManager);
	}
}
