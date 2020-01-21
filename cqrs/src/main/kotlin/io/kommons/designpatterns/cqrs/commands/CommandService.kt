package io.kommons.designpatterns.cqrs.commands

/**
 * CommandService
 *
 * @author debop
 */
interface CommandService {

    fun authorCreated(username: String, name: String, email: String)

    fun bookAddedToAuthor(title: String, price: Double, username: String)

    fun authorNameUpdated(username: String, name: String)

    fun authorUsernameUpdated(oldUsername: String, newUsername: String)

    fun authorEmailUpdated(username: String, email: String)

    fun bookTitleUpdated(oldTitle: String, newTitle: String)

    fun bookPriceUpdated(title: String, price: Double)

}