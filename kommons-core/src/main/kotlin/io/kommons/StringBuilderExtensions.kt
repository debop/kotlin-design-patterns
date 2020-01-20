package io.kommons


fun StringBuilder.appendln(msg: String): StringBuilder =
    this.append(msg).appendln()

fun StringBuilder.appendln(msg: () -> String): StringBuilder =
    this.append(msg.invoke()).appendln()