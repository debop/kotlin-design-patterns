package io.kommons.designpatterns.datamapper

import java.io.Serializable

/**
 * Student
 *
 * @author debop
 */
data class Student(var studentId: Int,
                   var name: String,
                   var grade: Char): Serializable {

    override fun equals(other: Any?): Boolean = when (other) {
        is Student -> studentId == other.studentId
        else       -> false
    }

    override fun hashCode(): Int = studentId.hashCode()

    override fun toString(): String =
        "Student [studentId=$studentId, name=$name, grade=$grade]"
}