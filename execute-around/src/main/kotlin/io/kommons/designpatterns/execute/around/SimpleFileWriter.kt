package io.kommons.designpatterns.execute.around

import java.io.FileWriter

/**
 * SimpleFileWriter handles opening and closing file for the user. The user only has to specify what
 * to do with the file resource through {@link FileWriterAction} parameter.
 *
 * @author debop
 */
class SimpleFileWriter(filename: String, action: (FileWriter) -> Unit) {

    init {
        FileWriter(filename).use { writer ->
            action(writer)
        }
    }
}