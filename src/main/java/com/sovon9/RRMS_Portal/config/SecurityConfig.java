package com.sovon9.RRMS_Portal.config;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig
{
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
//	{
//		UserDetails userDetails = User.withUsername("sovon").password(passwordEncoder.encode("abc@123")).roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(userDetails);
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
//	{
//		httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/signin", "/login").permitAll()
//				// .requestMatchers("/home").hasRole("USER")
//				.anyRequest().permitAll()) //authenticated()
//
////				.exceptionHandling(exception -> exception.accessDeniedPage("/access-denied") // Custom access denied
////																								// page for 403
////						.authenticationEntryPoint((request, response, authException) -> {
////							response.sendRedirect("/unauthorized"); // Redirect to custom unauthorized page for 401
////						}))
//
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.csrf(csrf -> csrf.disable())
//				// .formLogin(login->login.loginPage("/login").permitAll().defaultSuccessUrl("/home",true))
//				.logout(logout -> logout.permitAll());
//		return httpSecurity.build();
//	}
//	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception
//	{
//		return builder.getAuthenticationManager();
//	}
}
