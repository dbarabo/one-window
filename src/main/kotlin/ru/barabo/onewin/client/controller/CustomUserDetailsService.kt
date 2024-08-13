package ru.barabo.onewin.client.controller

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService : UserDetailsService {

    private val logger = LoggerFactory.getLogger(CustomUserDetailsService::class.java)

    val userStore: MutableList<MyUserDetails> = ArrayList()

    init {
        userStore += MyUserDetails("debara", defaultPassword())
    }

    lateinit var pswdEncoder: PasswordEncoder

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        if(!(this::pswdEncoder.isInitialized)) {
            pswdEncoder = BCryptPasswordEncoder() //PasswordEncoderFactories.createDelegatingPasswordEncoder()
        }

        return pswdEncoder
    }

    fun checkLdapAuth(userPasswordOrToken: String?, remoteHost: String): UserDetails {
        if(userPasswordOrToken == null) {
            return checkAuthByHost(remoteHost)
        }

        val type = userPasswordOrToken.substringBefore('@', "!").trim().lowercase()

        val value = userPasswordOrToken.substringAfter('@', "")

        return when(type) {
            "token" -> checkAuthByToken(value)
            "!" -> throw UsernameNotFoundException("Invalid request authorization")
            else -> checkLdapAuthByUserPassword(type, value, remoteHost)
        }
    }

    private fun checkAuthByToken(token: String): UserDetails {
        if(token.isEmpty()) throw UsernameNotFoundException("token is absent")

        val user = findByToken(token) ?: throw UsernameNotFoundException("токен не найден: $token")

        return user
    }

    private fun checkAuthByHost(remoteHost: String): UserDetails {
        if(remoteHost.isEmpty()) throw UsernameNotFoundException("remoteHost is absent")

        return findByHost(remoteHost) ?: throw UsernameNotFoundException("remote host is not found: $remoteHost")
    }

    private fun findByHost(remoteHost: String): MyUserDetails? = userStore.firstOrNull { it.host == remoteHost }

    private fun findByToken(token: String): MyUserDetails? = userStore.firstOrNull { it.token == token }

    fun findByUserName(userName: String): MyUserDetails? = userStore.firstOrNull { it.username == userName }

    private fun defaultPassword(): String {
        if(!(this::pswdEncoder.isInitialized)) {
            passwordEncoder()
        }
        return pswdEncoder.encode("12345678")
    }
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {

        val user = username?.let { findByUserName(it) } ?: throw UsernameNotFoundException("User is null")

        return user
    }

    private fun checkLdapAuthByUserPassword(user: String, password: String, remoteHost: String): UserDetails {

        if(password.isEmpty()) throw UsernameNotFoundException("password is empty")

        val userLdap = LdapCheckAuth().check(user, password)

        userLdap.token = generateToken()

        val findUser = findByUserName(userLdap.username!!)
            ?: MyUserDetails(userLdap.username!!, defaultPassword(), userLdap.token!!, remoteHost)

        if(findUser.token.isEmpty()) {
            findUser.token = userLdap.token!!
        }

        if(findUser.host.isEmpty()) {
            findUser.host = remoteHost
        }

        userStore += findUser

        return findUser
    }

    private fun generateToken(): String = UUID.randomUUID().toString().lowercase()
}

class MyUserDetails(var user: String = "", var pswd: String = "",
                    var token: String = "", var host: String = "") : UserDetails {

    private val authority = listOf(GrantedAuthority { "READ_POST_PUT_GET" })

    override fun getAuthorities(): List<GrantedAuthority> {
        return  authority
    }

    override fun getPassword(): String = pswd

    override fun getUsername(): String = user

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun equals(other: Any?): Boolean {
        return if (other is MyUserDetails) this.user == other.user else false
    }

    override fun hashCode(): Int {
        return this.username.hashCode()
    }
}