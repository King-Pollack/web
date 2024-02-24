package com.king.app.common.config;

import com.king.app.common.oauth2.handler.OAuth2LoginFailureHandler;
import com.king.app.common.oauth2.handler.OAuth2LoginSuccessHandler;
import com.king.app.common.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableRedisHttpSession
public class WebSecurityConfig {

	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.sessionManagement(sessionManagement ->
						sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				.authorizeHttpRequests(authorizeHttpRequests ->
						authorizeHttpRequests
								.dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
								.requestMatchers("/js/**", "/assets/**").permitAll()
								.requestMatchers("/waiting/login").permitAll()
								.requestMatchers("/waiting/stream/total-team").permitAll()
								.anyRequest().authenticated()
				)
				.oauth2Login(oauth2Login ->
						oauth2Login
								.redirectionEndpoint(redirectionEndpoint ->
										redirectionEndpoint.baseUri("/api/users/oauth2/login"))
								.userInfoEndpoint(userInfoEndpoint ->
										userInfoEndpoint.userService(customOAuth2UserService))
								.successHandler(oAuth2LoginSuccessHandler)
								.failureHandler(oAuth2LoginFailureHandler));

		return http.build();
	}
}
