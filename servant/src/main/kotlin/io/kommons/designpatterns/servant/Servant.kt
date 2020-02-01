package io.kommons.designpatterns.servant

class Servant(val name: String) {

    fun feed(r: Royalty) {
        r.getFed()
    }

    fun giveWine(r: Royalty) {
        r.getDrink()
    }

    fun giveCompliments(r: Royalty) {
        r.receiveCompliments()
    }

    fun checkIfYouWillBeHanged(tableGuests: List<Royalty>): Boolean {
        return tableGuests.all { it.getMood() }
    }
}