package org.example.ru.barabo.onewin.entity

import org.example.ru.barabo.onewin.xml.request.Fio
import org.example.ru.barabo.onewin.xml.request.Passport
import ru.barabo.db.annotation.ColumnName
import ru.barabo.db.annotation.ColumnType
import ru.barabo.db.annotation.SelectQuery
import java.sql.Timestamp
import java.time.LocalDate

@SelectQuery("{ ? = call OD.PTKB_CASH.getClientOneWindowById( ? ) }")
data class ClientRequest (

    @ColumnName("ID")
    @ColumnType(java.sql.Types.BIGINT)
    var id: Long? = null,

    @ColumnName("LAST_NAME")
    @ColumnType(java.sql.Types.VARCHAR)
    var lastName: String = "",

    @ColumnName("FIRST_NAME")
    @ColumnType(java.sql.Types.VARCHAR)
    var firstName: String = "",

    @ColumnName("PAPA_NAME")
    @ColumnType(java.sql.Types.VARCHAR)
    var papaName: String = "",

    @ColumnName("LINE_DOC")
    @ColumnType(java.sql.Types.VARCHAR)
    var lineDoc: String = "",

    @ColumnName("NUMBER_DOC")
    @ColumnType(java.sql.Types.VARCHAR)
    var numberDoc: String = "",

    @ColumnName("DATE_OUT_DOC")
    @ColumnType(java.sql.Types.TIMESTAMP)
    var dateOutDoc: Timestamp? = null,

    @ColumnName("BIRTH_DAY")
    @ColumnType(java.sql.Types.TIMESTAMP)
    var birthDay: Timestamp? = null,

    @ColumnName("AGREE_DATE_OUT")
    @ColumnType(java.sql.Types.TIMESTAMP)
    var agreeDateOut: Timestamp? = null,

    @ColumnName("VALIDITY")
    @ColumnType(java.sql.Types.BIGINT)
    var validity: Long = 1
) {
    fun fio(): Fio = Fio(lastName, firstName, papaName)

    fun passport(): Passport = Passport(lineDoc, numberDoc, dateOutDoc!!.toLocalDateTime().toLocalDate())

    fun birthDayLocalDate(): LocalDate = birthDay!!.toLocalDateTime().toLocalDate()

    fun agreeLocalDate(): LocalDate = agreeDateOut!!.toLocalDateTime().toLocalDate()
}