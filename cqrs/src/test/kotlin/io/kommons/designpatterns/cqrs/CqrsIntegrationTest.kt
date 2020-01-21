package io.kommons.designpatterns.cqrs

import io.kommons.designpatterns.cqrs.commands.CommandService
import io.kommons.designpatterns.cqrs.domain.repository.AuthorRepository
import io.kommons.designpatterns.cqrs.domain.repository.BookRepository
import io.kommons.designpatterns.cqrs.queries.QueryService
import io.kommons.junit.jupiter.random.RandomizedTest
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * CqrsIntegrationTest
 *
 * @author debop
 */
@RandomizedTest
@SpringBootTest(classes = [CqrsJpaConfiguration::class])
class CqrsIntegrationTest {

    companion object: KLogging()

    @Autowired private lateinit var command: CommandService
    @Autowired private lateinit var query: QueryService
    @Autowired private lateinit var authorRepo: AuthorRepository
    @Autowired private lateinit var bookRepo: BookRepository

    @BeforeAll
    fun beforeAll() {
        // create first author1
        command.authorCreated("username1", "name1", "email1")

        // create author2 and update all it's data
        command.authorCreated("username2", "name2", "email2")
        command.authorEmailUpdated("username2", "new_email2")
        command.authorNameUpdated("username2", "new_name2")
        command.authorUsernameUpdated("username2", "new_username2")

        // add book1 to author1
        command.bookAddedToAuthor("title1", 10.0, "username1")

        // add book2 to author1 and update all it's data
        command.bookAddedToAuthor("title2", 20.0, "username1")
        command.bookPriceUpdated("title2", 30.0)
        command.bookTitleUpdated("title2", "new_title2")
    }

    @Test
    fun `context loading`() {
        command.shouldNotBeNull()
        query.shouldNotBeNull()
        authorRepo.shouldNotBeNull()
        bookRepo.shouldNotBeNull()
    }

    @Test
    fun `get authors by username`() {
        val author = query.getAuthorByUsername("username1")
        author.shouldNotBeNull()

        author.username shouldEqual "username1"
        author.name shouldEqual "name1"
        author.email shouldEqual "email1"
    }

    @Test
    fun `get updated author by username`() {
        val author = query.getAuthorByUsername("new_username2")
        author.shouldNotBeNull()

        author.username shouldEqual "new_username2"
        author.name shouldEqual "new_name2"
        author.email shouldEqual "new_email2"
    }

    @Test
    fun `get book`() {
        val book = query.getBook("title1")
        book.shouldNotBeNull()

        book.title shouldEqual "title1"
        book.price shouldEqual 10.0
    }

    @Test
    fun `get author books`() {
        val books = query.getAuthorBooks("username1")
        books.size shouldEqualTo 2
    }

    @Test
    fun `get author books count`() {
        val bookCount = query.getAuthorBooksCount("username1")
        bookCount shouldEqualTo 2L
    }

    @Test
    fun `get authors count`() {
        val authorCount = query.getAuthorsCount()
        authorCount shouldEqualTo 2L
    }
}