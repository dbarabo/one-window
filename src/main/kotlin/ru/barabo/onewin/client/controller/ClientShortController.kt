package ru.barabo.onewin.client.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.barabo.onewin.client.entity.ClientShort
import ru.barabo.onewin.client.repository.ClientShortRepository

@RestController
@RequestMapping("/api")
class ClientShortController(private val clientShortRepository: ClientShortRepository) {

    @GetMapping("/clients")
    fun getClients(): Iterable<ClientShort> {
        return clientShortRepository.findClientShorts()
    }
}