package io.kommons.designpatterns.cqrs.domain.repository

import io.kommons.designpatterns.cqrs.domain.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, Long> {

    @Query("select b from Book b where b.title=:title")
    fun findByTitle(title: String): Book?

    @Query("select b from Book b join Author a on b.author = a where a.username=:username")
    fun getAuthorBooks(@Param("username") username: String): List<Book>

    @Query("select count(b) from Book b join Author a on b.author = a where a.username=:username")
    fun getAuthorBooksCount(@Param("username") username: String): Long


}