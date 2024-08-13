package ru.barabo.onewin.client.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.web.bind.annotation.CrossOrigin
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


@Configuration
@CrossOrigin
class AuthenticationTokenFilter(var customUserDetailsService: CustomUserDetailsService,
                                var authenticationProvider: AuthenticationProvider) : UsernamePasswordAuthenticationFilter() {

    private val logger = LoggerFactory.getLogger(AuthenticationTokenFilter::class.java)

    @Throws(IOException::class, ServletException::class)
    @CrossOrigin
    override fun doFilter(request: ServletRequest, response: ServletResponse?, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val authToken = httpRequest.getHeader("Authorization")

        val userDetails = customUserDetailsService.checkLdapAuth(authToken, httpRequest.remoteHost)

        val authenticationToken: UsernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(userDetails, "12345678", userDetails.authorities)
        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        val authentication = authenticationProvider.authenticate(authenticationToken)

        if(SecurityContextHolder.getContext().authentication == null ) {
            SecurityContextHolder.getContext().authentication = authenticationToken

            SecurityContextHolder.getContext().authentication = authentication

            val session: HttpSession = request.getSession(true)
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext())
        }

        chain.doFilter(request, response)
    }

    @Autowired
    override fun setAuthenticationManager(authenticationManager: AuthenticationManager?) {
        super.setAuthenticationManager(authenticationManager)
    }
}