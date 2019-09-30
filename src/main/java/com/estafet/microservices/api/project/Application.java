package com.estafet.microservices.api.project;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * The {@code Application} for the {@code project-api} microservice.
 *
 * <p>This is a <a href="https://spring.io/projects/spring-boot">Spring Boot</a> application.</p>
 *
 * @author Dennis Williams, Estafet Ltd.
 *
 * @see SpringBootApplication
 * @see EnableJms
 * @see EnableDiscoveryClient
 *
 */
@SpringBootApplication
@EnableJms
@EnableDiscoveryClient
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        return new com.uber.jaeger.Configuration("project-api",
                com.uber.jaeger.Configuration.SamplerConfiguration.fromEnv(),
                com.uber.jaeger.Configuration.ReporterConfiguration.fromEnv())
                .getTracer();
    }

    @Bean
    public JmsListenerContainerFactory<DefaultMessageListenerContainer> myFactory(final ConnectionFactory connectionFactory,
            final DefaultJmsListenerContainerFactoryConfigurer configurer) {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

}
