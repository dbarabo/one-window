package ru.barabo.afina

import ru.barabo.crypto.MasterKey
import ru.barabo.db.DbConnection
import ru.barabo.db.DbSetting
import ru.barabo.db.Query
import ru.barabo.db.TemplateQuery

object AfinaConnect : DbConnection(
    DbSetting("oracle.jdbc.driver.OracleDriver",
        "",
        MasterKey.value("AFINA_USER"),
        MasterKey.value("AFINA_PSWD"),
        "select 1 from dual"
    ) ) {

    @JvmStatic
    fun init(isTest: Boolean): Boolean {
        dbSetting.url = if(isTest) "jdbc:oracle:thin:@192.168.0.180:1521:AFINA" else MasterKey.value("AFINA_URL")
        return checkBase()
    }

    fun isTest(): Boolean = (dbSetting.url != MasterKey.value("AFINA_URL") )

}

fun String.ifTest(testPath: String) = if(AfinaConnect.isTest()) testPath else this

object AfinaQuery : Query(AfinaConnect)

object AfinaOrm : TemplateQuery(AfinaQuery)