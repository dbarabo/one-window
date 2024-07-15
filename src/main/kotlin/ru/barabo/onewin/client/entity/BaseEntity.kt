package ru.barabo.onewin.client.entity

import org.springframework.data.util.ProxyUtils
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity {
    @Id
    //@Column(name = "CLASSIFIED")
    open var doc: Long? = null

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as BaseEntity

        return this.doc != null && this.doc == other.doc
    }

    override fun hashCode() = 9973

    override fun toString(): String {
        return "${this.javaClass.simpleName}(id=$doc)"
    }
}