package io.kommons.designpatterns.cqrs.queries

import io.kommons.designpatterns.cqrs.domain.model.Author
import io.kommons.designpatterns.cqrs.domain.model.Book

interface QueryService {

    fun getAuthorByUsername(username: String): Author?

    fun getBook(title: String): Book?

    fun getAuthorBooks(username: String): List<Book>

    fun getAuthorBooksCount(username: String): Long

    fun getAuthorsCount(): Long
}