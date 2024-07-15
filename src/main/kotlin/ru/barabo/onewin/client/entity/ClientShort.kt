package ru.barabo.onewin.client.entity

import org.hibernate.annotations.Loader
import org.hibernate.annotations.NamedNativeQuery
import javax.persistence.Entity
import javax.persistence.Table

@Entity
//@Table(name = "CLIENT", schema = "od")
@NamedNativeQuery(
    name = "clientShort",
    callable = true,
    query = "{ ? = call od.PTKB_CASH.getClientsForOneWindow }",
    resultClass = ClientShort::class
)
@Loader(namedQuery = "clientShort")
class ClientShort : BaseEntity() {
    var label: String = ""
}