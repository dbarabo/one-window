package ru.barabo.onewin.client.controller

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*


//import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider

@Configuration
@CrossOrigin
@EnableWebSecurity
class LdapSecurityConfig(var customUserDetailsService: CustomUserDetailsService, var passwordEncoder: PasswordEncoder) {
    /*@Bean
    fun authenticationProvider(): ActiveDirectoryLdapAuthenticationProvider {
        return ActiveDirectoryLdapAuthenticationProvider("", "ldap://192.168.0.8:389") //"dc=ptkb,dc=local" 192.168.0.8
    }*/

    /*@Bean
    @Primary
    @Throws(java.lang.Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.getAuthenticationManager()
    }*/

    private lateinit var provider: AuthenticationProvider

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder: AuthenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        provider = DaoAuthenticationProvider().apply {
            this.setUserDetailsService(customUserDetailsService)
            this.setPasswordEncoder(passwordEncoder)
        }

        return provider
    }


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {

        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")

        //configuration.allowCredentials = true
        configuration.exposedHeaders = listOf("Authorization")

        val source: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }


    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain? {

        http.csrf().disable()
        http.headers().frameOptions().disable()//для пост была выключена

        http.logout().disable()

        //http.cors().and().csrf().disable() //для пост была выключена

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //STATELESS


        http
            .cors().and() // новое - без него работало
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } //STATELESS
            .authorizeHttpRequests()
            .antMatchers("/auth/**").permitAll()
            //.antMatchers("/api/infobki/**").permitAll()
            //.antMatchers("/api/**").permitAll()
            .anyRequest().authenticated()

        http.authenticationProvider(authenticationProvider())

        http.addFilterBefore(authenticationTokenFilterBean(authenticationManager),
            UsernamePasswordAuthenticationFilter::class.java)

        http.headers().cacheControl()

        return http.build()
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationTokenFilterBean(authenticationManager: AuthenticationManager): AuthenticationTokenFilter {
        val authenticationTokenFilter = AuthenticationTokenFilter(customUserDetailsService, provider)
        authenticationTokenFilter.setAuthenticationManager(authenticationManager)

        return authenticationTokenFilter
    }



}