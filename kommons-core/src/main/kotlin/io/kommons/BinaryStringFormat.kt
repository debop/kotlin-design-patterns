package io.kommons

/**
 * BinaryStringFormat
 *
 * @author debop
 */
enum class BinaryStringFormat {

    /** Base64 encoding */
    Base64,

    /** HexDecimal encoding */
    HexDecimal;


    companion object {

        fun valueOf(ordinal: Int): BinaryStringFormat = when (ordinal) {
            0    -> Base64
            1    -> HexDecimal
            else -> throw IllegalArgumentException("Not supported ordianl value. ordinal=$ordinal")
        }
    }
}