package io.kommons.designpatterns.datamapper

/**
 * StudentDataMapperImpl
 *
 * @author debop
 */
class StudentDataMapperImpl: StudentDataMapper {

    val students: MutableList<Student> = mutableListOf()

    override fun find(studentId: Int): Student? {
        return students.find { it.studentId == studentId }
    }

    override fun update(student: Student) {
        val index = students.indexOf(student)
        if (index != -1) {
            students[index] = student
        } else {
            throw DataMapperException("Student [${student.name}] is not found")
        }
    }

    override fun insert(student: Student) {
        if (!students.contains(student)) {
            students.add(student)
        } else {
            throw DataMapperException("Student already [${student.name}] exists")
        }
    }

    override fun delete(student: Student) {
        if (!students.remove(student)) {
            throw DataMapperException("Student [${student.name}] is not found")
        }
    }
}