package com.quantum.player

import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Player management utils
 * @author xRookieFight
 * @since 03/02/2026
 */
class PlayerManager {

    val players = ConcurrentHashMap<UUID, Player>()

    fun addPlayer(player: Player) {
        players[player.uuid] = player
    }

    fun removePlayer(player: Player) {
        players.remove(player.uuid)
    }

    fun getPlayerByName(name: String): Player?
    = players.values.find { it.name == name }

	fun getPlayer(uuid: UUID): Player?
	= players[uuid]
}
