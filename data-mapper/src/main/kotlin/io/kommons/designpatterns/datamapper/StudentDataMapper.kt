package io.kommons.designpatterns.datamapper

/**
 * StudentDataMapper
 *
 * @author debop
 */
interface StudentDataMapper {

    fun find(studentId: Int): Student?

    @Throws(DataMapperException::class)
    fun insert(student: Student)

    @Throws(DataMapperException::class)
    fun update(student: Student)

    @Throws(DataMapperException::class)
    fun delete(student: Student)
}