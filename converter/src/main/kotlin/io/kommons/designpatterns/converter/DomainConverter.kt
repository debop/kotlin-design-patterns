package io.kommons.designpatterns.converter

/**
 * DomainConverter
 */
open class DomainConverter<E, D>(private val toDto: (E) -> D,
                                 private val toEntity: (D) -> E) {

    fun convertToDto(entity: E): D = toDto.invoke(entity)

    fun convertToEntity(dto: D): E = toEntity.invoke(dto)

    fun convertToDtos(entities: Iterable<E>): List<D> =
        entities.map { toDto.invoke(it) }

    fun convertToEntities(dtos: Iterable<D>): List<E> =
        dtos.map { toEntity.invoke(it) }
}