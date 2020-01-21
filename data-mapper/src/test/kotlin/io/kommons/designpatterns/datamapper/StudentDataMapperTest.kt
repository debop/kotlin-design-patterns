package io.kommons.designpatterns.datamapper

import io.kommons.junit.jupiter.random.RandomValue
import io.kommons.junit.jupiter.random.RandomizedTest
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.assertThrows

@RandomizedTest
class StudentDataMapperTest {

    val studentDataMapper = StudentDataMapperImpl()

    @BeforeEach
    fun beforeEach() {
        studentDataMapper.students.clear()
    }

    @RepeatedTest(10)
    fun `insert test`(@RandomValue(type = Student::class) students: List<Student>) {

        students.forEach {
            studentDataMapper.insert(it)
        }
        studentDataMapper.students shouldContainSame students

        studentDataMapper.find(students.last().studentId).shouldNotBeNull()

        assertThrows<DataMapperException> {
            studentDataMapper.insert(students.last())
        }
    }

    @RepeatedTest(10)
    fun `update test`(@RandomValue(type = Student::class) students: List<Student>) {

        students.forEach {
            studentDataMapper.insert(it)
        }
        studentDataMapper.students shouldContainSame students

        students.forEach {
            studentDataMapper.update(it)
        }

        studentDataMapper.find(students.last().studentId).shouldNotBeNull()

        studentDataMapper.students.clear()

        studentDataMapper.find(students.last().studentId).shouldBeNull()

        assertThrows<DataMapperException> {
            studentDataMapper.update(students.last())
        }
    }

    @RepeatedTest(10)
    fun `delete test`(@RandomValue(type = Student::class) students: List<Student>) {

        students.forEach {
            studentDataMapper.insert(it)
        }
        studentDataMapper.students shouldContainSame students

        students.forEach {
            studentDataMapper.delete(it)
        }

        studentDataMapper.find(students.last().studentId).shouldBeNull()

        assertThrows<DataMapperException> {
            studentDataMapper.delete(students.last())
        }
    }
}