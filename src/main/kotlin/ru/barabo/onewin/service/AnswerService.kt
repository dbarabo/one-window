package ru.barabo.onewin.service

import oracle.jdbc.OracleTypes
import org.slf4j.LoggerFactory
import ru.barabo.afina.AfinaQuery
import ru.barabo.db.SessionSetting
import ru.barabo.onewin.xml.answer.KBkiSource
import ru.barabo.onewin.xml.answer.Loan
import ru.barabo.onewin.xml.answer.PayInfoXml
import ru.barabo.onewin.xml.request.xmlDateToLocal
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

object AnswerService {

    private val logger = LoggerFactory.getLogger(AnswerService::class.java)

    fun saveAnswer(idClient: Number, payInfo: PayInfoXml) {

        val session = AfinaQuery.uniqueSession()

        try {
            val request = saveRequest(idClient, payInfo, session)

            if(!payInfo.bkiList.isNullOrEmpty()) {
                saveResponseList(request, payInfo.bkiList!!, session)
            }
        } catch (e: Exception) {
            logger.error("saveAnswer", e)

            AfinaQuery.rollbackFree(session)

            throw Exception(e)
        }
        AfinaQuery.commitFree(session)
    }

    private fun saveResponseList(request: Long, bkiList: List<KBkiSource>, session: SessionSetting) {

        for(kbki in bkiList) {

            if(kbki.commitments?.bkiList.isNullOrEmpty()) {
                saveResponse(request, kbki, loan=null, session)
            } else {

                kbki.commitments!!.bkiList!!.forEach {

                    for(loan in it.loanList!!) {
                        saveResponse(request, kbki, loan, session)
                    }
                }
            }
        }
    }

    private fun saveResponse(request: Long, kBkiSource: KBkiSource, loan: Loan?, session: SessionSetting) {

        val params: Array<Any?> = arrayOf(request,
            kBkiSource.ogrn,
            kBkiSource.onDateStateDateTime.toTimestamp(),
            kBkiSource.uuidResponse,
            loan?.uuid ?:"",
            loan?.payMonthly?.payValue?: BigDecimal::class.javaObjectType,
            loan?.payMonthly?.dateCalculate?.xmlDateToLocal()?.toTimestamp() ?: java.sql.Date::class.javaObjectType,
            loan?.payMonthly?.currency?:""
        )

        AfinaQuery.execute(EXEC_SAVE_RESPONSE, params, session)
    }

    private fun saveRequest(idClient: Number, payInfo: PayInfoXml, session: SessionSetting): Long {

        val params: Array<Any?> = arrayOf(idClient,
            payInfo.uuidRequest,
            payInfo.uuidResponse,
            payInfo.ogrn,
            payInfo.typeResponse.toInt().toLong(),
            payInfo.subject?.fio?.fullName?:"",
            payInfo.subject?.birthDayLocal?.toTimestamp(),
            payInfo.subject?.passport?.lineAndNumber?:"",
            payInfo.subject?.passport?.dateOutLocal?.toTimestamp(),
            "",
            0L
            )

        return (AfinaQuery.execute(EXEC_SAVE_REQUEST, params, session, intArrayOf(OracleTypes.NUMBER))
            ?.get(0) as Number).toLong()
    }
}

fun LocalDate.toTimestamp(): Timestamp = Timestamp.valueOf(this.atStartOfDay() )

fun LocalDateTime.toTimestamp(): Timestamp = Timestamp.valueOf(this )

private const val EXEC_SAVE_REQUEST = "{ call od.PTKB_RUTDF.saveRequestOneWin(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }"

private const val EXEC_SAVE_RESPONSE = "{ call od.PTKB_RUTDF.saveResponseOneWin(?, ?, ?, ?, ?, ?, ?, ?) }"