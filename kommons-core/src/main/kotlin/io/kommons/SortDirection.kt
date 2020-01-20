package io.kommons

/**
 * SortDirection
 *
 * @author debop
 */
enum class SortDirection(val direction: Int) {

    ASC(1), DESC(-1);

    companion object {
        @JvmStatic
        fun of(dir: Int): SortDirection = if (dir > 0) ASC else DESC
    }
}