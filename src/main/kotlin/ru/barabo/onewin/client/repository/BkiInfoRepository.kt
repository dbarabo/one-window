package ru.barabo.onewin.client.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.barabo.onewin.client.entity.BkiInfo

interface BkiInfoRepository : CrudRepository<BkiInfo, Long> {

    @Query(nativeQuery = true, name = "bkiInfo")
    fun retrieveInfo(id: Long): Iterable<BkiInfo>
}