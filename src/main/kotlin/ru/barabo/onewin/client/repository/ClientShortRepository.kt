package ru.barabo.onewin.client.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.barabo.onewin.client.entity.ClientShort

interface ClientShortRepository : CrudRepository<ClientShort, Long> {

    @Query(name = "clientShort", nativeQuery = true)
    fun findClientShorts(): Iterable<ClientShort>

}