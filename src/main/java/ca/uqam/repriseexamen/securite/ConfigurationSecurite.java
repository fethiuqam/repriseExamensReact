package ca.uqam.repriseexamen.securite;

import ca.uqam.repriseexamen.securite.jwt.JWTAuthenticationFilter;
import ca.uqam.repriseexamen.securite.jwt.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurite extends WebSecurityConfigurerAdapter {

    @Autowired
    private InformationUtilisateurServiceImpl utilisateurService;

    @Autowired
    private JWTTokenHelper jWTTokenHelper;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder encodeurMotDePasse(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(utilisateurService).passwordEncoder(encodeurMotDePasse());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests((request) ->request.antMatchers("/api/login/**").permitAll()
                        //Roles
                        .antMatchers(HttpMethod.GET,"/api/roles/**").hasAuthority("GererRoles")
                        .antMatchers(HttpMethod.POST,"/api/roles/**").hasAuthority("GererRoles")
                        .antMatchers(HttpMethod.PUT,"/api/roles/**").hasAuthority("GererRoles")
                        .antMatchers(HttpMethod.PATCH,"/api/roles/**").hasAuthority("GererRoles")
                        //Utilisateurs
                        .antMatchers(HttpMethod.GET,"/api/utilisateurs/**").hasAuthority("GererUsagers")
                        .antMatchers(HttpMethod.POST,"/api/utilisateurs/**").hasAuthority("GererUsagers")
                        .antMatchers(HttpMethod.PUT,"/api/utilisateurs/**").hasAuthority("GererUsagers")
                        .antMatchers(HttpMethod.PATCH,"/api/utilisateurs/**").hasAuthority("GererUsagers")
                        //DEFAULT
                        .antMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JWTAuthenticationFilter(utilisateurService, jWTTokenHelper),
                        UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable().cors().and().headers().frameOptions().disable();
    }
}
