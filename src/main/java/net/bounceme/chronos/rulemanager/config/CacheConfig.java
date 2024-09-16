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

	@Bean
	public SpringRemoteCacheManager cacheManager() {
		// Configurar el RemoteCacheManager con los detalles de conexión
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer().host(cacheServer) 
				.port(cachePort); 

		builder.security().authentication().username(authUserName) 
				.password(authPassword) 
				.realm(authRealm) 
				.saslMechanism(authMechanism); 

		// Configurar el marshaller para utilizar JSON
        builder.marshaller(new SimpleKeyMarshaller());
		
		RemoteCacheManager remoteCacheManager = new RemoteCacheManager(builder.build());

		return new SpringRemoteCacheManager(remoteCacheManager);
	}
}
