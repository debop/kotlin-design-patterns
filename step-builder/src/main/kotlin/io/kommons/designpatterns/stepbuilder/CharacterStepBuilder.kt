package io.kommons.designpatterns.stepbuilder


object CharacterStepBuilder {

    fun newBuilder(): NameStep = CharacterSteps()

    interface NameStep {
        fun name(name: String): ClassStep
    }

    interface ClassStep {
        fun fighterClass(fighterClass: String): WeaponStep
        fun wizardClass(wizardClass: String): SpellStep
    }

    interface WeaponStep {
        fun withWeapon(weapon: String): AbilityStep
        fun noWeapon(): BuildStep
    }

    interface SpellStep {
        fun withSpell(spell: String): AbilityStep
        fun noSpell(): BuildStep
    }

    interface AbilityStep {
        fun withAbility(ability: String): AbilityStep
        fun noMoreAbilities(): BuildStep
        fun noAbilities(): BuildStep
    }

    interface BuildStep {
        fun build(): Character
    }

    private class CharacterSteps: NameStep, ClassStep, WeaponStep, SpellStep, AbilityStep, BuildStep {

        var name: String? = null
        var fighterClass: String? = null
        var wizardClass: String? = null
        var weapon: String? = null
        var spell: String? = null
        var abilities: MutableList<String> = mutableListOf()

        override fun name(name: String): ClassStep = apply {
            this.name = name
        }

        override fun fighterClass(fighterClass: String): WeaponStep = apply {
            this.fighterClass = fighterClass
        }

        override fun wizardClass(wizardClass: String): SpellStep = apply {
            this.wizardClass = wizardClass
        }

        override fun withWeapon(weapon: String): AbilityStep = apply {
            this.weapon = weapon
        }

        override fun noWeapon(): BuildStep = apply {}

        override fun withSpell(spell: String): AbilityStep = apply {
            this.spell = spell
        }

        override fun noSpell(): BuildStep = apply { }

        override fun withAbility(ability: String): AbilityStep = apply {
            this.abilities.add(ability)
        }

        override fun noMoreAbilities(): BuildStep = apply { }

        override fun noAbilities(): BuildStep = apply { }

        override fun build(): Character {
            return Character(name!!).also {
                if (fighterClass != null) {
                    it.fighterClass = fighterClass
                } else {
                    it.wizardClass = wizardClass
                }
                if (weapon != null) {
                    it.weapon = weapon
                } else {
                    it.spell = spell
                }

                if (abilities.isNotEmpty()) {
                    it.abilities = abilities
                }
            }
        }

    }

}