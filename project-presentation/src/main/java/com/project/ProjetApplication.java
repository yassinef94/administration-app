package com.project;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class ProjetApplication {

	private static final Logger logger = LoggerFactory.getLogger(ProjetApplication.class);
	
	@Value("${server.port}")
	private Integer port;

	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(ProjetApplication.class);
		Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null)
			protocol = "https";

		logger.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n\t"
						+ "Profile(s): \t{}\n\t"
						+ "Swagger Link: \t{}://{}:{}{}/swagger-ui/index.html?configUrl={}/v3/api-docs/swagger-config \n"
						+ "----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, 
				env.getProperty("server.port"), env.getProperty("server.servlet.context-path"), protocol,
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), env.getProperty("server.servlet.context-path"),
				env.getActiveProfiles(), protocol, 
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), env.getProperty("server.servlet.context-path"), env.getProperty("server.servlet.context-path"));
	}
	
	@Bean
	public ServletWebServerFactory servletContainer() {
		// Enable SSL Trafic
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		// Add HTTP to HTTPS redirect
//		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
		return tomcat;
	}

	/*
	 * We need to redirect from HTTP to HTTPS. Without SSL, this application used
	 * port 8088. With SSL it will use port default port. So, any request for 8088 needs to
	 * be redirected to HTTPS on default port.
	 */
//	private Connector httpToHttpsRedirectConnector() {
//		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//		connector.setScheme("http");
//		connector.setPort(8088);
//		connector.setSecure(false);
//		connector.setRedirectPort(port);
//		return connector;
//	}

}
