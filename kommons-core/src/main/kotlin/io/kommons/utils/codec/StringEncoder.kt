package io.kommons.utils.codec

/**
 * 바이트 배열을 문자열로 인코딩/디코딩 하는 Interface
 *
 * @author debop
 */
interface StringEncoder {

    /**
     * 바이트 배열을 인코딩하여 문자열로 만든다.
     * @param bytes 인코딩할 바이트 배열
     * @return 인코딩된 문자열
     */
    fun encode(bytes: ByteArray?): String

    /**
     * 인코딩된 문자열을 분해하여 바이트 배열로 만든다.
     * @param encoded 인코딩된 문자열
     * @return 디코딩된 바이트 배열
     */
    fun decode(encoded: String?): ByteArray
}