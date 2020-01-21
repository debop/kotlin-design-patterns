package io.kommons.designpatterns.execute.around

class App

fun main() {

    SimpleFileWriter("testfile.txt") { writer ->
        writer.write("Hello")
        writer.append(" ")
        writer.append("there!")
    }

}