package io.kommons.designpatterns.servant

class Queen: Royalty {

    private var isDrunk = true
    private var isHungry = false
    private var isHappy = false
    private var isFlirty = true
    private var complimentReceived = false

    override fun getFed() {
        isHungry = false
    }

    override fun getDrink() {
        isDrunk = true
    }

    override fun changeMood() {
        if (complimentReceived && isFlirty && isDrunk && !isHungry) {
            isHappy = true
        }
    }

    override fun receiveCompliments() {
        complimentReceived = true
    }

    override fun getMood(): Boolean {
        return isHappy
    }

    fun setFlirtiness(f: Boolean) {
        isFlirty = f
    }
}