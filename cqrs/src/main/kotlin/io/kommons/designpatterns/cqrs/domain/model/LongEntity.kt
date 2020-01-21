package io.kommons.designpatterns.cqrs.domain.model

import io.kommons.AbstractValueObject
import io.kommons.ToStringBuilder
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class LongEntity: AbstractValueObject() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    override fun equalProperties(other: Any): Boolean {
        return other is LongEntity && id == other.id
    }

    override fun buildStringHelper(): ToStringBuilder {
        return super.buildStringHelper()
            .add("id", id)
    }
}