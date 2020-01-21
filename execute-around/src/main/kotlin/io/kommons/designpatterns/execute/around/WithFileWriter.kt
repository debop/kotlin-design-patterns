package io.kommons.designpatterns.execute.around

import java.io.FileWriter

/**
 * Kotlin의 receiver를 이용한 함수를 이용하여 execute-around 패턴을 구현합니다.
 *
 * @author debop
 */
fun withFileWriter(filename: String, action: FileWriter.() -> Unit) {
    FileWriter(filename).use {
        action(it)
    }
}