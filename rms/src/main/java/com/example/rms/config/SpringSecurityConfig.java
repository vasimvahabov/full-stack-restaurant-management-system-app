package com.example.rms.config;
   
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;  
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter; 
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; 

@Configuration 
public class SpringSecurityConfig{
   
  private final AuthEntryPoint _authEntryPoint;
  private final LoginAuthFilter _loginAuthFilter;
  private final JWTAuthFilter _jwtAuthFilter;
	
  public SpringSecurityConfig(AuthEntryPoint authEntryPoint,
                             LoginAuthFilter loginAuthFilter,JWTAuthFilter jwtAuthFilter){ 
    this._authEntryPoint=authEntryPoint;
    this._loginAuthFilter=loginAuthFilter;
    this._jwtAuthFilter=jwtAuthFilter; 
  } 
	
  @Bean 
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
    httpSecurity
      .exceptionHandling().authenticationEntryPoint(_authEntryPoint)
      .and() 
      .addFilterBefore(_loginAuthFilter,BasicAuthenticationFilter.class)
      .addFilterBefore(_jwtAuthFilter,BasicAuthenticationFilter.class)
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .csrf().disable()
      .cors().and()
      .authorizeHttpRequests(authz-> authz 
    	.requestMatchers(
    	  "/admin/logIn", 
    	  "/user/logIn"
    	).permitAll()   
        .requestMatchers("/admin/**","/position/**","employee/**").hasAnyRole("admin") 
        .requestMatchers("/**").hasAnyRole("admin","user")
        .anyRequest().authenticated()
      ).httpBasic();
     
    return httpSecurity.build();
  }
  
//  @Bean
//  public PasswordEncoder encoder(){
//    return new BCryptPasswordEncoder();
//  }
  
  
  @Bean  
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
	  .addMapping("/**")
	  .allowedOrigins("http://localhost:4200")
	  .allowedMethods("*")
	  .allowCredentials(true);
      }
    };
  }
  
}
