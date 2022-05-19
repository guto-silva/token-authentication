package br.dev.guto.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyAuthEntryPoint entryPoint;
	
	@Override
	public void configure(HttpSecurity security) throws Exception{
		security.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(entryPoint)
				.and()
				.authorizeRequests()
				.antMatchers("/hello").permitAll() //antMatchers is used to configure access to the endpoints /hello
				.antMatchers(HttpMethod.POST,"/login").permitAll()
				.anyRequest().authenticated();
		
		security.addFilterBefore(new MyFilter(), UsernamePasswordAuthenticationFilter.class);
	}	
}
