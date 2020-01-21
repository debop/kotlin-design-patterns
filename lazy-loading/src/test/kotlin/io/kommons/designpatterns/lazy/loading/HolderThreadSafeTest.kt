package io.kommons.designpatterns.lazy.loading

class HolderThreadSafeTest: AbstractHolderTest() {

    private val holder = HolderThreadSafe()

    override fun getInternalHeavyValue(): Heavy? {
        val field = HolderThreadSafe::class.java.getDeclaredField("heavy")
        field.isAccessible = true

        return field.get(holder) as? Heavy
    }

    override fun getHeavy(): Heavy? {
        return holder.getHeavy()
    }
}