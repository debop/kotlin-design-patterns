package io.kommons.designpatterns.cqrs.domain.repository

import io.kommons.designpatterns.cqrs.domain.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository: JpaRepository<Author, Long> {

    @Query("select a from Author a where a.username=:username")
    fun findByUsername(@Param("username") username: String): Author?

}