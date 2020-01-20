package io.kommons.designpatterns.abstractdocument

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

/**
 * AbstractDocumentTest
 *
 * @author debop
 * @since 28/09/2019
 */
class AbstractDocumentTest {

    companion object: KLogging() {
        const val KEY = "key"
        const val VALUE = "value"
    }

    class DocumentImpl(properties: MutableMap<String, Any?>): AbstractDocument(properties)

    private val document = DocumentImpl(hashMapOf())

    @Test
    fun `put and get value`() {
        document[KEY] = VALUE
        document[KEY] shouldEqual VALUE
    }

    @Test
    fun `retrieve children`() {
        val child1 = hashMapOf<String, Any?>()
        val child2 = hashMapOf<String, Any?>()

        val children = listOf(child1, child2)

        document[KEY] = children

        val documents = document.children(KEY, ::DocumentImpl)
        documents.count() shouldEqualTo 2
    }

    @Test
    fun `retrieve empty sequence for non existing children`() {
        val children = document.children(KEY, ::DocumentImpl)
        children.shouldNotBeNull()
        children.count() shouldEqualTo 0
    }

    @Test
    fun `include properties into String`() {
        val props = hashMapOf<String, Any?>(KEY to VALUE)
        val document = DocumentImpl(props)

        document.toString() shouldContain KEY shouldContain VALUE
    }
}