package io.kommons.designpatterns.caching


import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import io.kommons.testcontainers.generic.MongoDBServer
import org.bson.Document

object DbManager {

    private var useMongoDB: Boolean = false
    private var isConnected: Boolean = false
    private lateinit var mongoServer: MongoDBServer
    private lateinit var mongoClient: MongoClient
    private lateinit var db: MongoDatabase

    private lateinit var virtualDB: MutableMap<String, UserAccount>

    fun createVirtualDb() {
        useMongoDB = false
        virtualDB = hashMapOf()
    }

    fun connect() {
        useMongoDB = true
        mongoServer = MongoDBServer().apply { start() }
        mongoClient = MongoClient(mongoServer.host, mongoServer.port)
        db = mongoClient.getDatabase("test")
        isConnected = true
    }

    fun readFromDb(userId: String): UserAccount? {
        if (!useMongoDB) {
            return virtualDB[userId]
        }

        val document = getUserAccounts().find(Document(USER_ID, userId)).first()
        return document?.toUserAccount()
    }

    fun writeToDb(userAccount: UserAccount) {
        if (!useMongoDB) {
            virtualDB[userAccount.userId] = userAccount
        } else {
            getUserAccounts().insertOne(userAccount.toDocument())
        }
    }

    fun updateDb(userAccount: UserAccount) {
        if (!useMongoDB) {
            virtualDB[userAccount.userId] = userAccount
        } else {
            getUserAccounts().updateOne(Document(USER_ID, userAccount.userId),
                                        Document("\$set", userAccount.toDocument()))
        }
    }

    fun upsertDb(userAccount: UserAccount) {
        if (!useMongoDB) {
            virtualDB[userAccount.userId] = userAccount
        } else {
            getUserAccounts().updateOne(Document(USER_ID, userAccount.userId),
                                        Document("\$set", userAccount.toDocument()),
                                        UpdateOptions().upsert(true))
        }
    }

    private fun getUserAccounts(): MongoCollection<Document> {
        if (!isConnected) {
            runCatching { connect() }
        }
        return db.getCollection(USER_ACCOUNT)
    }

    fun UserAccount.toDocument(): Document =
        Document(USER_ID, userId)
            .append(USER_NAME, userName)
            .append(ADD_INFO, additionalInfo)

    fun Document.toUserAccount(): UserAccount =
        UserAccount(getString(USER_ID), getString(USER_NAME), getString(ADD_INFO))
}