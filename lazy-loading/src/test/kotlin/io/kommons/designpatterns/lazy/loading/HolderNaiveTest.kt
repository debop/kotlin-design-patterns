package io.kommons.designpatterns.lazy.loading

/**
 * LazyLoadingTest
 */
class HolderNaiveTest: AbstractHolderTest() {

    private val holder = HolderNaive()

    override fun getInternalHeavyValue(): Heavy? {
        val field = HolderNaive::class.java.getDeclaredField("heavy")
        field.isAccessible = true

        return field.get(holder) as? Heavy
    }

    override fun getHeavy(): Heavy? {
        return holder.getHeavy()
    }

}