package io.kommons.designpatterns.extensionobjects

import io.kommons.logging.KLogging
import io.kommons.logging.info

class Soldier(val unit: SoldierUnit): SoldierExtension {

    companion object: KLogging()

    override fun soldierReady() {
        log.info { "[Soldier] ${unit.name} is ready" }
    }
}

class Sergeant(val unit: SergeantUnit): SergeantExtension {

    companion object: KLogging()

    override fun sergeantReady() {
        log.info { "[Sergeant] ${unit.name} is ready" }
    }
}

class Commander(val unit: CommanderUnit): CommanderExtension {

    companion object: KLogging()

    override fun commanderReady() {
        log.info { "[Commander] ${unit.name} is ready" }
    }
}