package io.kommons.designpatterns.servant

class King: Royalty {

    private var isDrunk = false
    private var isHungry = true
    private var isHappy = false
    private var complimentReceived = false

    override fun getFed() {
        isHungry = false
    }

    override fun getDrink() {
        isDrunk = true
    }

    override fun changeMood() {
        if (!isHungry && isDrunk) {
            isHappy = true
        }
        if (complimentReceived) {
            isHappy = false
        }
    }

    override fun receiveCompliments() {
        complimentReceived = true
    }

    override fun getMood(): Boolean {
        return isHappy
    }
}