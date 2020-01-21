package io.kommons.designpatterns.execute.around

import java.io.FileWriter

/**
 * Interface for specifying what to do with the file resource.
 *
 * @author debop
 */
@FunctionalInterface
interface FileWriterAction {

    fun writeFile(writer: FileWriter)

}