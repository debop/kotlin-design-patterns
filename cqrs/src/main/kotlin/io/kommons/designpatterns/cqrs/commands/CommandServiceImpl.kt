package io.kommons.designpatterns.cqrs.commands

import io.kommons.designpatterns.cqrs.domain.model.Author
import io.kommons.designpatterns.cqrs.domain.model.Book
import io.kommons.designpatterns.cqrs.domain.repository.AuthorRepository
import io.kommons.designpatterns.cqrs.domain.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CommandServiceImpl(private val authorRepository: AuthorRepository,
                         private val bookRepository: BookRepository): CommandService {

    override fun authorCreated(username: String, name: String, email: String) {
        val author = Author().also {
            it.username = username
            it.name = name
            it.email = email
        }
        authorRepository.saveAndFlush(author)
    }

    override fun bookAddedToAuthor(title: String, price: Double, username: String) {
        val author = authorRepository.findByUsername(username)
        val book = Book().also {
            it.title = title
            it.price = price
            it.author = author
        }
        bookRepository.saveAndFlush(book)
    }

    override fun authorNameUpdated(username: String, name: String) {
        authorRepository.findByUsername(username)?.let { author ->
            author.name = name
            authorRepository.saveAndFlush(author)
        }
    }

    override fun authorUsernameUpdated(oldUsername: String, newUsername: String) {
        authorRepository.findByUsername(oldUsername)?.let { author ->
            author.username = newUsername
            authorRepository.saveAndFlush(author)
        }
    }

    override fun authorEmailUpdated(username: String, email: String) {
        authorRepository.findByUsername(username)?.let { author ->
            author.email = email
            authorRepository.saveAndFlush(author)
        }
    }

    override fun bookTitleUpdated(oldTitle: String, newTitle: String) {
        bookRepository.findByTitle(oldTitle)?.let { book ->
            book.title = newTitle
            bookRepository.saveAndFlush(book)
        }
    }

    override fun bookPriceUpdated(title: String, price: Double) {
        bookRepository.findByTitle(title)?.let { book ->
            book.price = price
            bookRepository.saveAndFlush(book)
        }
    }
}