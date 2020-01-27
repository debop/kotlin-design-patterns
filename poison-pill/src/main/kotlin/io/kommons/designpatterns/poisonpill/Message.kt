package io.kommons.designpatterns.poisonpill

import io.kommons.designpatterns.poisonpill.Message.Headers

interface Message {

    enum class Headers {
        DATE, SENDER
    }

    fun addHeader(header: Headers, value: String)

    fun getHeader(header: Headers): String?

    fun getHeaders(): Map<Headers, String>

    var body: String

    companion object {

        val POISON_PILL: Message = object: Message {
            override fun addHeader(header: Headers, value: String) {
                throw poison()
            }

            override fun getHeader(header: Headers): String? {
                throw poison()
            }

            override fun getHeaders(): Map<Headers, String> {
                throw poison()
            }

            override var body: String
                get() = throw poison()
                set(value) {
                    throw poison()
                }

            fun poison(): RuntimeException =
                UnsupportedOperationException("Poison")
        }
    }
}

class SimpleMessage: Message {

    private val headers = HashMap<Headers, String>()

    override fun addHeader(header: Headers, value: String) {
        headers[header] = value
    }

    override fun getHeader(header: Headers): String? {
        return headers[header]
    }

    override fun getHeaders(): Map<Headers, String> {
        return headers.toMap()
    }

    override var body: String = ""

}