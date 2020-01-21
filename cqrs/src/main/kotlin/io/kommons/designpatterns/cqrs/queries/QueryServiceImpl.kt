package io.kommons.designpatterns.cqrs.queries

import io.kommons.designpatterns.cqrs.domain.model.Author
import io.kommons.designpatterns.cqrs.domain.model.Book
import io.kommons.designpatterns.cqrs.domain.repository.AuthorRepository
import io.kommons.designpatterns.cqrs.domain.repository.BookRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class QueryServiceImpl(private val bookRepository: BookRepository,
                       private val authorRepository: AuthorRepository): QueryService {

    // QueryDSL을 사용할 수도 있다
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun getAuthorByUsername(username: String): Author? {
        return authorRepository.findByUsername(username)
    }

    override fun getBook(title: String): Book? {
        return bookRepository.findByTitle(title)
    }

    override fun getAuthorBooks(username: String): List<Book> {
        return bookRepository.getAuthorBooks(username)
    }

    override fun getAuthorBooksCount(username: String): Long {
        return bookRepository.getAuthorBooksCount(username)
    }

    override fun getAuthorsCount(): Long {
        return authorRepository.count()
    }
}