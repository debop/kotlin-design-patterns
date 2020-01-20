package io.kommons

import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBe
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.Test

class ValueObjectTest {

    class Person(val name: String, val age: Int, val address: String? = null): AbstractValueObject() {

        override fun equalProperties(other: Any): Boolean = when (other) {
            is Person -> name == other.name && age == other.age
            else      -> false
        }

        override fun buildStringHelper(): ToStringBuilder {
            return super.buildStringHelper()
                .add("name", name)
                .add("age", age)
                .add("address", address)
        }
    }

    val people = listOf(
        Person("debop", 50, "hanam"),
        Person("debop", 44, "hanam"),
        Person("sunghyouk", 50),
        Person("debop", 50)
    )

    @Test
    fun `check equals value object`() {
        people[0] shouldNotEqual people[1]
        people[0] shouldNotEqual people[2]
        people[1] shouldNotEqual people[2]
        people[1] shouldNotEqual people[3]

        people[0] shouldEqual people[3]  // equal value
        people[0] shouldNotBe people[3]  // not equal reference address
    }

    @Test
    fun `value object toString`() {
        people[0].toString() shouldEqual "Person(name=debop,age=50,address=hanam)"
        people[1].toString() shouldEqual "Person(name=debop,age=44,address=hanam)"
        people[2].toString() shouldEqual "Person(name=sunghyouk,age=50,address=<null>)"
        people[3].toString() shouldEqual "Person(name=debop,age=50,address=<null>)"
    }
}