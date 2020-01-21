package io.kommons.designpatterns.command

enum class Visibility(val title: String) {

    VISIBLE("visible"),
    INVISIBLE("invisible");

    override fun toString(): String = title
}