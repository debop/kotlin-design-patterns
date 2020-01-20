package io.kommons

import java.nio.ByteBuffer

/**
 * Int 값을 ByteArray로 변환합니다.
 */
fun Int.toByteArray(): ByteArray {
    val buffer = ByteBuffer.allocateDirect(4).putInt(this)
    buffer.flip()
    return ByteArray(4).apply { buffer.get(this) }
}

/**
 * Long 값을 ByteArray로 변환합니다.
 */
fun Long.toByteArray(): ByteArray {
    val buffer = ByteBuffer.allocateDirect(8).putLong(this)
    buffer.flip()
    return ByteArray(8).apply { buffer.get(this) }
}

/**
 * ByteArray의 값을 Int로 변환합니다.
 */
fun ByteArray.toInt(): Int = ByteBuffer.wrap(this, 0, 4).int

/**
 * ByteArray의 값을 Long 으로 변환합니다.
 */
fun ByteArray.toLong(): Long = ByteBuffer.wrap(this, 0, 8).long
