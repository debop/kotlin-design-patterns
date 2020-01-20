package io.kommons.designpatterns.adapter

/**
 * Captain
 *
 * @author debop
 * @since 19. 9. 18.
 */
class Captain(val rowingBoat: RowingBoat) {

    fun row() {
        rowingBoat.row()
    }
}