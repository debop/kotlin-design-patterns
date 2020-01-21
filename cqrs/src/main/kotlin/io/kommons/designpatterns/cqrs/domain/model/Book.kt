package io.kommons.designpatterns.cqrs.domain.model

import io.kommons.ToStringBuilder
import javax.persistence.Entity
import javax.persistence.ManyToOne

/**
 * Book
 *
 * @author debop
 */
@Entity
class Book: LongEntity() {

    var title: String? = null
    var price: Double = 0.0

    @ManyToOne
    var author: Author? = null


    override fun buildStringHelper(): ToStringBuilder {
        return super.buildStringHelper()
            .add("title", title)
            .add("price", price)
            .add("author", author)
    }
}