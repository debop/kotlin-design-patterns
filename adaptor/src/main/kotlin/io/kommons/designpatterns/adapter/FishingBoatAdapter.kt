package io.kommons.designpatterns.adapter

/**
 * [FishingBoat]를 [RowingBoat]로 공개하기 위한 Adapter
 */
class FishingBoatAdapter(private val fishingBoat: FishingBoat = FishingBoat()): RowingBoat {

    override fun row() {
        fishingBoat.sail()
    }
}