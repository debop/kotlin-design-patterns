package io.kommons.designpatterns.cqrs.domain.model

import io.kommons.ToStringBuilder
import javax.persistence.Entity

@Entity
class Author: LongEntity() {

    var username: String? = null
    var name: String? = null
    var email: String? = null

    override fun buildStringHelper(): ToStringBuilder {
        return super.buildStringHelper()
            .add("name", name)
            .add("email", email)
    }
}