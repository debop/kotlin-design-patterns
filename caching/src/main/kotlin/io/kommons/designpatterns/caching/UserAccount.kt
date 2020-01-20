package io.kommons.designpatterns.caching

import java.io.Serializable

data class UserAccount(val userId: String,
                       var userName: String,
                       var additionalInfo: String): Serializable
