package ru.barabo.onewin.client.controller

import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.web.bind.annotation.*

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException

@CrossOrigin
@RestController
@RequestMapping("/auth")
class AuthController(var authenticationManager: AuthenticationManager,
                     var customUserDetailsService: CustomUserDetailsService) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun loginUser(@RequestBody username: UserName?): String { //RequestParam

        logger.error("username=$username")
        logger.error("username.username=${username?.username?:"NULL"}")

        val user = username?.username?.let { customUserDetailsService.findByUserName(it) }
            ?: throw UsernameNotFoundException("user $username not found")

        return user.token!!

//        val authorities: List<GrantedAuthority> = ArrayList<GrantedAuthority>()
//        val authentication: Authentication = authenticationManager.authenticate(
//            UsernamePasswordAuthenticationToken(username, password, authorities)
//        )
//        SecurityContextHolder.getContext().setAuthentication(authentication)
    }
}

open class UserName {
    var username: String? = null
}