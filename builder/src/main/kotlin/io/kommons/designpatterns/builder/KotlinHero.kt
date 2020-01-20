package io.kommons.designpatterns.builder

data class KotlinHero @JvmOverloads constructor(val profession: Profession,
                                                val name: String,
                                                val hairType: HairType? = null,
                                                val hairColor: HairColor? = null,
                                                val armor: Armor? = null,
                                                val weapon: Weapon? = null) {

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
}