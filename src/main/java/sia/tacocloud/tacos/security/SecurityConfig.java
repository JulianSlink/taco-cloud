package sia.tacocloud.tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    DataSource dataSource;

    //tag::configureHttpSecurity[]
    //tag::authorizeRequests[]
    //tag::customLoginPage[]

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/design", "orders")
//                role should not start with 'ROLE_' since it is automatically inserted. Got 'ROLE_USER'
//                使用版本5.7.1，角色的前缀应该忽略
                .hasRole("USER")
                .antMatchers("/", "/**").permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design")

                .and()
                .logout()
                .logoutSuccessUrl("/");
        //end::customLoginPage[]

        //在星期二具有ROLE_USER的用户才可以创建taco
        /*http
                .authorizeRequests()
                .antMatchers("/design", "orders")
                .access("hasRole('ROLE_USER') &&" +
                        "T(java.util.Calendar).getInstance().get(" +
                        "T(java.util.Calendar).DAY_OF_WEEK) == " +
                        "T(java.util.Calendar).TUESDAY")
                .antMatchers("/", "/**").permitAll();*/
    }


    //end::configureHttpSecurity[]
    //end::authorizeRequests[]
    //end::customLoginPage[]

    //tag::customUserDetailsService_withPasswordEncoder[]
    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());

    }
    //end::customUserDetailsService_withPasswordEncoder[]

    //tag::configureAuthentication_jdbc_withQueries[]

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from Users " +
                                "where username=?")
                .authoritiesByUsernameQuery(
                        "select username, authority from UserAuthorities " +
                                "where username=?"
                )
        .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
    }*/

    //end::configureAuthentication_jdbc_withQueries[]

    // JDBC Authentication example
    //tag::configureAuthentication_jdbc[]

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource);
    }*/

    //end::configureAuthentication_jdbc[]

    //tag::configureAuthentication_inMemory[]
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("buzz")
                .password("{noop}infinity")
                .authorities("ROLE_USER")
                .and()
                .withUser("woody")
                .password("{noop}bullseye")
                .authorities("ROLE_USER");
    }*/
    //end::configureAuthentication_inMemory[]
}
