package nu.ssis.a18mosu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	LibraryUserDetailsService libraryUserDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll()
		.and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and()
			.authorizeRequests()
				.antMatchers("/admin", "/admin/**").hasRole("USER")
				.antMatchers("/user", "/user/**").hasRole("USER")
				.antMatchers("/book/**/edit", "/book/**/edit/**").hasRole("USER")
				.antMatchers("/book/**/comment/**").hasRole("USER")
				.antMatchers("/loan", "/loan/**").hasRole("USER")
		.and()
			.oauth2Login()
				.loginPage("/")
				.userInfoEndpoint()
				.oidcUserService(libraryUserDetailsService);
		// @formatter:on
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

}
