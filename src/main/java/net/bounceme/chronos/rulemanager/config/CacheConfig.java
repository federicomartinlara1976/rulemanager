package net.bounceme.chronos.rulemanager.config;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.MarshallerUtil;
import org.infinispan.commons.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.spring.remote.provider.SpringRemoteCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.rulemanager.support.MethodDTOListMarshaller;
import net.bounceme.chronos.rulemanager.support.MethodDTOMarshaller;

@Configuration
@EnableCaching
@Slf4j
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
	
	RemoteCacheManager remoteCacheManager;

	@Bean
	SpringRemoteCacheManager cacheManager() {
		log.info("Iniciando caché");
		
		// Configurar el RemoteCacheManager con los detalles de conexión
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer().host(cacheServer).port(cachePort);

		builder.addServer().host(cacheServer).port(cachePort).security().authentication().username(authUserName)
				.password(authPassword).realm(authRealm).saslMechanism(authMechanism).socketTimeout(5000)
				.connectionTimeout(5000);
		
		// Configurar el marshaller para utilizar ProtoStream
        builder.marshaller(new ProtoStreamMarshaller());

		remoteCacheManager = new RemoteCacheManager(builder.build());
		
		// Registrar el marshaller personalizado para SimpleKey
        SerializationContext ctx = MarshallerUtil.getSerializationContext(remoteCacheManager);
        
        try {
            ctx.registerProtoFiles(FileDescriptorSource.fromResources("dtos.proto"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to register ProtoBuf schema", e);
        }
        
        ctx.registerMarshaller(new MethodDTOMarshaller());
        ctx.registerMarshaller(new MethodDTOListMarshaller());

		return new SpringRemoteCacheManager(remoteCacheManager);
	}
	
	@PreDestroy
    public void cleanup() {
        // Cerrar el RemoteCacheManager cuando la aplicación se detenga
		log.info("Parando caché");
        remoteCacheManager.stop();
    }
}
