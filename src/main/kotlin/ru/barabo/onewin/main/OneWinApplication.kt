package ru.barabo.onewin.main

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(/*exclude = [
    SecurityAutoConfiguration::class,
    ManagementWebSecurityAutoConfiguration::class]*/)
@ComponentScan(basePackages = ["ru.barabo.onewin.client.controller"])
@Configuration
@EnableJpaRepositories(basePackages = ["ru.barabo.onewin.client.repository"])
@EntityScan(basePackages = ["ru.barabo.onewin.client.entity"])
class OneWinApplication {
}

fun main(args: Array<String>) {
    runApplication<OneWinApplication>(*args)
}