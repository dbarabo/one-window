package ru.barabo.onewin.client.entity

import org.hibernate.annotations.NamedNativeQuery
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@NamedNativeQuery(
    name = "bkiInfo",
    callable = true,
    query = "{ ? = call od.PTKB_CASH.getInfoByClientForOneWindow( ? ) }",
    resultClass = BkiInfo::class
)
class BkiInfo {

    @Id
    var rownum: Long? = null

    var ogrn: String? = ""

    var name: String? = ""

    var loanUuid: String? = ""

    var ampValue: String? = ""

    var ampCalcDate: String? = ""

    override fun hashCode() = 9974
}
