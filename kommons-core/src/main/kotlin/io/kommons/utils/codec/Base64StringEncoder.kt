package io.kommons.utils.codec

import io.kommons.EMPTY_STRING
import org.apache.commons.codec.binary.Base64

/**
 * 문자열을 Base64 형태로 인코딩/디코딩 합니다
 *
 * @author debop
 */
class Base64StringEncoder: StringEncoder {

    // Url safe base64 encoder
    private val base64 = Base64(true)

    /**
     * [bytes]를 Base64 문자열로 인코딩합니다.
     *
     * @param bytes
     * @return
     */
    override fun encode(bytes: ByteArray?): String {
        return bytes?.run { base64.encodeAsString(this) } ?: EMPTY_STRING
    }

    /**
     * [encoded]를 디코딩하여 [ByteArray]로 만든다
     *
     * @param encoded
     * @return
     */
    override fun decode(encoded: String?): ByteArray {
        if (encoded.isNullOrBlank()) {
            return ByteArray(0)
        }
        return base64.decode(encoded.toByteArray(Charsets.UTF_8))
    }
}