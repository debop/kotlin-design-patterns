package io.kommons.designpatterns.command

enum class Size(val title: String) {

    SMALL("small"),
    NORMAL("normal");

    override fun toString(): String = title
}