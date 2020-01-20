package io.kommons


/** Size가 0인 BooleanArray */
val emptyBooleanArray: BooleanArray = BooleanArray(0)

/** Size가 0인 CharArray */
val emptyCharArray: CharArray = CharArray(0)

/** Size가 0인 ByteArray */
val emptyByteArray: ByteArray = ByteArray(0)

/** Size가 0인 ShortArray */
val emptyShortArray: ShortArray = ShortArray(0)

/** Size가 0인 IntArray */
val emptyIntArray: IntArray = IntArray(0)

/** Size가 0인 LongArray */
val emptyLongArray: LongArray = LongArray(0)

/** Size가 0인 FloatArray */
val emptyFloatArray: FloatArray = FloatArray(0)

/** Size가 0인 DoubleArray */
val emptyDoubleArray: DoubleArray = DoubleArray(0)

fun BooleanArray?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun CharArray?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun ByteArray?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun ShortArray?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun IntArray?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun LongArray?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun FloatArray?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun DoubleArray?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()


fun BooleanArray?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false
fun CharArray?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false
fun ByteArray?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false
fun ShortArray?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false
fun IntArray?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false
fun LongArray?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false
fun FloatArray?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false
fun DoubleArray?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false
fun <T> Array<T>?.isNonEmpty(): Boolean = this?.isNotEmpty() ?: false

/**
 * Array의 요소에 지정한 `supplier`를 통해 값을 설정합니다.
 *
 * @param supplier 요소의 값을 제공하는 함수
 * @return
 */
inline fun BooleanArray.setAll(supplier: (index: Int) -> Boolean): BooleanArray = apply {
    indices.forEach { this[it] = supplier(it) }
}

/**
 * Array의 요소에 지정한 `supplier`를 통해 값을 설정합니다.
 *
 * @param supplier 요소의 값을 제공하는 함수
 * @return
 */
inline fun CharArray.setAll(supplier: (index: Int) -> Char): CharArray = apply {
    indices.forEach { this[it] = supplier(it) }
}

/**
 * Array의 요소에 지정한 `supplier`를 통해 값을 설정합니다.
 *
 * @param supplier 요소의 값을 제공하는 함수
 * @return
 */
inline fun ByteArray.setAll(supplier: (index: Int) -> Byte): ByteArray = apply {
    indices.forEach { this[it] = supplier(it) }
}

/**
 * Array의 요소에 지정한 `supplier`를 통해 값을 설정합니다.
 *
 * @param supplier 요소의 값을 제공하는 함수
 * @return
 */
inline fun ShortArray.setAll(supplier: (index: Int) -> Short): ShortArray = apply {
    indices.forEach { this[it] = supplier(it) }
}

/**
 * Array의 요소에 지정한 `supplier`를 통해 값을 설정합니다.
 *
 * @param supplier 요소의 값을 제공하는 함수
 * @return
 */
inline fun IntArray.setAll(supplier: (index: Int) -> Int): IntArray = apply {
    indices.forEach { this[it] = supplier(it) }
}

/**
 * Array의 요소에 지정한 `supplier`를 통해 값을 설정합니다.
 *
 * @param supplier 요소의 값을 제공하는 함수
 * @return
 */
inline fun LongArray.setAll(supplier: (index: Int) -> Long): LongArray = apply {
    indices.forEach { this[it] = supplier(it) }
}

/**
 * Array의 요소에 지정한 `supplier`를 통해 값을 설정합니다.
 *
 * @param supplier 요소의 값을 제공하는 함수
 * @return
 */
inline fun FloatArray.setAll(supplier: (index: Int) -> Float): FloatArray = apply {
    indices.forEach { this[it] = supplier(it) }
}

/**
 * Array의 요소에 지정한 `supplier`를 통해 값을 설정합니다.
 *
 * @param supplier 요소의 값을 제공하는 함수
 * @return
 */
inline fun DoubleArray.setAll(supplier: (index: Int) -> Double): DoubleArray = apply {
    indices.forEach { this[it] = supplier(it) }
}

/**
 * 지정된 크기의 [BooleanArray]를 생성합니다.
 *
 * @param size array size
 * @param initializer 초기화 함수
 * @return [BooleanArray] instance
 */
@JvmOverloads
inline fun generateBooleanArray(size: Int, initializer: (Int) -> Boolean = { false }): BooleanArray =
    BooleanArray(size) { initializer(it) }


/**
 * 지정된 크기의 [CharArray]를 생성합니다.
 *
 * @param size array size
 * @param initializer 초기화 함수
 * @return [CharArray] instance
 */
@JvmOverloads
inline fun generateCharArray(size: Int, initializer: (Int) -> Char = { '\u0000' }): CharArray =
    CharArray(size) { initializer(it) }

/**
 * 지정된 크기의 [ByteArray]를 생성합니다.
 *
 * @param size array size
 * @param initializer 초기화 함수
 * @return [ByteArray] instance
 */
@JvmOverloads
inline fun generateByteArray(size: Int, initializer: (Int) -> Byte = { 0 }): ByteArray =
    ByteArray(size) { initializer(it) }

/**
 * 지정된 크기의 [ShortArray]를 생성합니다.
 *
 * @param size array size
 * @param initializer 초기화 함수
 * @return [ShortArray] instance
 */
@JvmOverloads
inline fun generateShortArray(size: Int, initializer: (Int) -> Short = { 0 }): ShortArray =
    ShortArray(size) { initializer(it) }

/**
 * 지정된 크기의 [IntArray]를 생성합니다.
 *
 * @param size array size
 * @param initializer 초기화 함수
 * @return [IntArray] instance
 */
@JvmOverloads
inline fun generateIntArray(size: Int, initializer: (Int) -> Int = { 0 }): IntArray =
    IntArray(size) { initializer(it) }

/**
 * 지정된 크기의 [LongArray]를 생성합니다.
 *
 * @param size array size
 * @param initializer 초기화 함수
 * @return [LongArray] instance
 */
@JvmOverloads
inline fun generateLongArray(size: Int, initializer: (Int) -> Long = { 0L }): LongArray =
    LongArray(size) { initializer(it) }

/**
 * 지정된 크기의 [FloatArray]를 생성합니다.
 *
 * @param size array size
 * @param initializer 초기화 함수
 * @return [FloatArray] instance
 */
@JvmOverloads
inline fun generateFloatArray(size: Int, initializer: (Int) -> Float = { 0.0F }): FloatArray =
    FloatArray(size) { initializer(it) }

/**
 * 지정된 크기의 [DoubleArray]를 생성합니다.
 *
 * @param size array size
 * @param initializer 초기화 함수
 * @return [DoubleArray] instance
 */
@JvmOverloads
inline fun generateDoubleArray(size: Int, initializer: (Int) -> Double = { 0.0 }): DoubleArray =
    DoubleArray(size) { initializer(it) }


/**
 * Array의 마지막 요소를 삭제한 새로운 Array를 반환합니다.
 *
 * @param T
 * @return
 */
fun <T> Array<T>.removeLastValue(): Array<T> = this.copyOfRange(0, size - 1)

/**
 * Array의 마지막 요소에 지정한 값을 설정합니다.
 *
 * @param value 설정할 값
 */
fun <T> Array<T>.setLast(value: T) {
    check(size > 0) { "Array is empty." }
    this[lastIndex] = value
}
