package io.kommons.utils.codec

import io.kommons.EMPTY_STRING
import org.apache.commons.codec.binary.Hex

/**
 * 문자열을 16진법 (Hex Decimal) 문자로 인코딩/디코딩 합니다
 *
 * @author debop
 */
class HexStringEncoder: StringEncoder {

    private val hex = Hex(Charsets.UTF_8)

    /**
     * [bytes]를 16진법 (Hex Decimal) 문자열로 인코딩합니다.
     *
     * @param bytes 인코딩할 데이터
     * @return 16진법으로 인코딩된 문자열
     */
    override fun encode(bytes: ByteArray?): String {
        return bytes?.run { hex.encode(this).toString(Charsets.UTF_8) } ?: EMPTY_STRING
    }

    /**
     * 16진법 (Hex Decimmal) 문자열로 인코딩된 [encoded]를 [ByteArray]로 디코딩합니다.
     *
     * @param encoded Hex Decimal로 인코딩된 문자열
     * @return 디코딩된 [ByteArray]
     */
    override fun decode(encoded: String?): ByteArray {
        if (encoded.isNullOrBlank()) {
            return ByteArray(0)
        }

        return hex.decode(encoded.toByteArray(Charsets.UTF_8))
    }
}