package io.kommons.utils

private val CHO_SUNG: IntArray =
    intArrayOf(0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139,
               0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149,
               0x314a, 0x314b, 0x314c, 0x314d, 0x314e)


private val JUNG_SUNG: IntArray =
    intArrayOf(0x314f, 0x3150, 0x3151, 0x3152,
               0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159,
               0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163)

private val JONG_SUNG: IntArray =
    intArrayOf(0x0000, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135,
               0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e,
               0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146,
               0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e)

private val KOREAN_RANGE = (0xAC00..0xD7AF)

/**
 * 문자열에 한글 자소가 포함되어 있는지 확인한다
 *
 * @receiver 검사할 문자열
 * @return Boolean 한글 자소가 포함되었으면 true
 */
fun String.containKorean(): Boolean {
    var exists = firstOrNull {
        val char = it.toInt()
        CHO_SUNG.contains(char) || JUNG_SUNG.contains(char) || JONG_SUNG.contains(char)
    } != null

    if (!exists) {
        exists = firstOrNull { it.toInt() in KOREAN_RANGE } != null
    }
    return exists
}


/**
 * 문자열 내에서 할글 자소를 분해하여 초/중/종성 으로 제공합니다.
 * 예: 한국 -> ㅎㅏㄴㄱㅜㄱ
 */
fun String.getJasoLetter(): String {
    if (this.isBlank())
        return ""

    val letters = StringBuilder(this.length * 3)

    repeat(length) { i ->
        val char = this[i].toInt()
        if (char in KOREAN_RANGE) {
            val initIndex: Int = char - 0xAC00

            val jongIndex: Int = initIndex % 28
            val jungIndex: Int = ((initIndex - jongIndex) / 28) % 21
            val choIndex: Int = ((initIndex / 28) - jungIndex) / 21

            letters.run {
                append("%c".format(CHO_SUNG[choIndex]))
                append("%c".format(JUNG_SUNG[jungIndex]))
            }
            if (jongIndex != 0x0000) {
                letters.append("%c".format(JONG_SUNG[jongIndex]))
            }
        }
        //        else {
        //            letters.append("%c".format(char.toChar()))
        //        }
    }
    return letters.toString()
}

/**
 * 문자열의 초성만 추출합니다.
 * 예: 대한민국 -> ㄷㅎㅁㄱ
 */
fun String.getChosung(): CharArray {
    if (this.isBlank())
        return charArrayOf()

    val chosungs = mutableListOf<Char>()

    repeat(length) { i ->
        val char = this[i].toInt()
        if (char in KOREAN_RANGE) {
            val initIndex: Int = char - 0xAC00

            val jongIndex: Int = initIndex % 28
            val jungIndex: Int = ((initIndex - jongIndex) / 28) % 21
            val choIndex: Int = ((initIndex / 28) - jungIndex) / 21

            chosungs.add(CHO_SUNG[choIndex].toChar())
        }
    }
    return chosungs.toCharArray()
}
