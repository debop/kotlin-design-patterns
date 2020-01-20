package io.kommons.designpatterns.builder


enum class Armor(val title: String) {

    CLOTHES("clothes"),
    LEATHER("leather"),
    CHAIN_MAIL("chain mail"),
    PLATE_MAIL("plate mail");

    override fun toString(): String = title
}

enum class HairColor {

    WHITE, BLOND, RED, BROWN, BLACK;

    override fun toString(): String = name.toLowerCase()
}

enum class HairType(val title: String) {

    BALD("bald"), SHORT("short"), CURLY("curly"), LONG_STRAIGHT("long straight"), LONG_CURLY("long curly");

    override fun toString(): String = title
}

enum class Profession {

    WARRIOR, THIEF, MAGE, PRIEST;

    override fun toString(): String = name.toLowerCase()
}

enum class Weapon {

    DAGGER, SWORD, AXE, WARHAMMER, BOW;

    override fun toString(): String = name.toLowerCase()
}

