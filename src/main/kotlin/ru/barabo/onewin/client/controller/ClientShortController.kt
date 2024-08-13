package ru.barabo.onewin.client.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.barabo.onewin.client.entity.BkiInfo
import ru.barabo.onewin.client.entity.ClientShort
import ru.barabo.onewin.client.repository.BkiInfoRepository
import ru.barabo.onewin.client.repository.ClientShortRepository
import ru.barabo.onewin.http.HttpClient

@CrossOrigin
@RestController
@RequestMapping("/api")
class ClientShortController(private val clientShortRepository: ClientShortRepository,
                            private val bkiInfoRepository: BkiInfoRepository
) {

    private val logger = LoggerFactory.getLogger(ClientShortController::class.java)

    @GetMapping("/clients")
    fun getClients(): Iterable<ClientShort> {
        return clientShortRepository.findClientShorts()
    }

    @GetMapping("/infobki/{id}")
    fun getBkiInfo(@PathVariable("id") id: Long): Iterable<BkiInfo> {
        logger.error("getBkiInfo=$id")
        return bkiInfoRepository.retrieveInfo(id)
    }

    @PutMapping("/clients/{id}")
    fun sendRequest(@PathVariable("id") id: Long): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.OK).body("")
        /*
        return try {
            HttpClient.requestAndAnswer(id, isOneWinRequest = true)

            ResponseEntity.status(HttpStatus.OK).body("")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }*/
    }
}