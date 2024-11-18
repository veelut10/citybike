package estaciones.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecuritySuccessHandler successHandler;

	@Autowired
	private JwtRequestFilter authenticationRequestFilter;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.httpBasic().disable()
				.authorizeRequests()
				.antMatchers("/publico/**")
				.permitAll()
				.antMatchers("/estaciones/**")
				.authenticated()
				.and()
				.oauth2Login()
				.successHandler(this.successHandler)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Establece el filtro de autenticación en la cadena de filtros de seguridad
		httpSecurity.addFilterBefore(this.authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
