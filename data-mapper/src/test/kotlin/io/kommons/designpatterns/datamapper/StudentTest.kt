package io.kommons.designpatterns.datamapper

import io.kommons.junit.jupiter.random.RandomValue
import io.kommons.junit.jupiter.random.RandomizedTest
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

@RandomizedTest
class StudentTest {

    @Test
    fun `student equality`() {

        val student1 = Student(1, "Adam", 'A')
        val student2 = Student(2, "Donald", 'A')
        val student3 = Student(2, "Smith", 'B')

        student1 shouldNotEqual student2
        student1 shouldNotEqual student3

        student2 shouldEqual student3
    }

    @RepeatedTest(10)
    fun `student equality test`(@RandomValue student1: Student, @RandomValue student2: Student) {
        if (student1.studentId != student2.studentId) {
            student1 shouldNotEqual student2
        } else {
            student1 shouldEqual student2
        }
    }


}