package io.kommons.utils.resizablearray

import io.kommons.utils.resizablearray.ResizableArrayBuffer.Companion.KB

data class ArrayBlock(val size: Int = KB, val count: Int = 1) {
    val capacity: Int get() = size * count
}