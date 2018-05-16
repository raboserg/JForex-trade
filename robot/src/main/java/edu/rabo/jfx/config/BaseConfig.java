package edu.rabo.jfx.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.dukascopy.api.system.ClientFactory;
import com.dukascopy.api.system.IClient;
import com.dukascopy.api.system.ITesterClient;
import com.dukascopy.api.system.TesterFactory;

import edu.rabo.jfx.account.AccountService;
import edu.rabo.jfx.system.RealSystemListener;
import edu.rabo.jfx.system.TestSystemListener;

@Configuration
@PropertySource("classpath:jforex.properties")
public class BaseConfig {

	@Value("${jforex.urn}")
	private String url;
	@Value("${jforex.username}")
	private String user;
	@Value("${jforex.password}")
	private String password;
	@Value("${jforex.type.live.account}")
	private boolean isLiveAccount;

	public AccountService createAccountService() {
		final AccountService account = new AccountService();
		account.setUrl(url);
		account.setUser(user);
		account.setPassword(password);
		account.setLiveAccount(isLiveAccount);
		return account;
	}
	
	@Bean
	public IClientInitializer clientInitializer() throws Exception {
		final ClientInitializer clientInitializer = new ClientInitializer();
		clientInitializer.setAccountService(createAccountService());
		return clientInitializer;
	}
	
	@Bean
	@Profile("prod")
	IClient getClient() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		final IClient client = ClientFactory.getDefaultInstance();
		client.setSystemListener(new RealSystemListener(client));
		return client;
	}

	@Bean
	@Profile("test")
	IClient getTestClient() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		final ITesterClient client = TesterFactory.getDefaultInstance();
		client.setSystemListener(new TestSystemListener(client));
		return client;
	}

	
	@SuppressWarnings("unused")
	private void inspectBeans(ApplicationContext ctx) {
		System.out.println("Let's inspect the beans provided by Spring Boot:");
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
}
