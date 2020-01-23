package io.kommons.designpatterns.stepbuilder

class Character(var name: String) {

    var fighterClass: String? = null
    var wizardClass: String? = null
    var weapon: String? = null
    var spell: String? = null
    var abilities: List<String>? = null

    override fun toString(): String = buildString {
        append("This is a ")
        append(fighterClass ?: wizardClass ?: "")
        append(" named ")
        append(name)
        append(" armed with a ")
        append(weapon ?: spell ?: "with nothing")
        append(if (abilities != null) " and wielding $abilities abilities" else "")
        append(".")
    }
}