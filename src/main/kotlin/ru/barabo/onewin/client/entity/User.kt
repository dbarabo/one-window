package ru.barabo.onewin.client.entity

import javax.persistence.Id

open class User {
    @Id
    var username: String? = null

    var token: String? = null

    var fullName: String? = null

    var errorMessage: String? = null
}
