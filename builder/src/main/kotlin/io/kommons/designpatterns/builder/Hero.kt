package io.kommons.designpatterns.builder

class Hero private constructor(builder: Builder) {

    val profession: Profession = builder.profession
    val name: String = builder.name
    val hairType: HairType? = builder.hairType
    val hairColor: HairColor? = builder.hairColor
    val armor: Armor? = builder.armor
    val weapon: Weapon? = builder.weapon

    override fun toString(): String = buildString {
        append("This is a ")
            .append(profession)
            .append(" named ")
            .append(name)

        if (hairColor != null || hairType != null) {
            append(" with ")
            hairColor?.run { append(this).append(' ') }
            hairType?.run { append(this).append(' ') }
        }

        armor?.run { append(" wearing ").append(this) }
        weapon?.run { append(" and wielding a ").append(this) }
        append('.')
    }


    /**
     * Builder
     *
     * @property profession
     * @property name
     */
    class Builder(val profession: Profession, val name: String) {

        internal var hairType: HairType? = null
        internal var hairColor: HairColor? = null
        internal var armor: Armor? = null
        internal var weapon: Weapon? = null

        fun withHairType(hairType: HairType) = apply {
            this.hairType = hairType
        }

        fun withHairColor(hairColor: HairColor) = apply {
            this.hairColor = hairColor
        }

        fun withArmor(armor: Armor) = apply {
            this.armor = armor
        }

        fun withWeapon(weapon: Weapon) = apply {
            this.weapon = weapon
        }

        fun build(): Hero {
            return Hero(this)
        }
    }
}