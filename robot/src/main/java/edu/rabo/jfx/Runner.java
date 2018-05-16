package edu.rabo.jfx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import edu.rabo.jfx.config.BaseConfig;
import edu.rabo.jfx.config.ClientInitializer;
import edu.rabo.jfx.config.IClientInitializer;

public class Runner {

	@SuppressWarnings({ "resource" })
	public static void main(String[] args) throws Exception {
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "prod");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BaseConfig.class);
		final IClientInitializer clientInitializer = context.getBean(ClientInitializer.class);
		clientInitializer.initialize();
	}
}