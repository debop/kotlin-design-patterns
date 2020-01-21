package io.kommons.designpatterns.dto

import java.io.Serializable

/**
 * [CustomerDto] is a data transfer object POJO. Instead of sending individual information to client
 * We can send related information together in POJO.
 * Dto will not have any business logic in it.
 */
data class CustomerDto(val id: String,
                       val firstName: String,
                       val lastName: String): Serializable