package com.quantum.player.model

/**
 * Represents all supported player GameModes.
 * Defines default gameplay behavior and networking ID.
 *
 * @author ProTrix
 * @since 16/02/2026
 */
enum class GameMode(
	val id: Int,
	val displayName: String,
	val canFly: Boolean,
	val canBreakBlocks: Boolean,
	val takesDamage: Boolean,
	val hasCollision: Boolean,
	val hasGravity: Boolean,
	val canInteract: Boolean
) {

	SURVIVAL(
		id = 0,
		displayName = "Survival",
		canFly = false,
		canBreakBlocks = true,
		takesDamage = true,
		hasCollision = true,
		hasGravity = true,
		canInteract = true
	),

	CREATIVE(
		id = 1,
		displayName = "Creative",
		canFly = true,
		canBreakBlocks = true,
		takesDamage = false,
		hasCollision = true,
		hasGravity = true,
		canInteract = true
	),

	ADVENTURE(
		id = 2,
		displayName = "Adventure",
		canFly = false,
		canBreakBlocks = false,
		takesDamage = false,
		hasCollision = true,
		hasGravity = true,
		canInteract = false //TODO: Recheck Adventure Flags because idrk -Trix
	),

	SPECTATOR(
		id = 3,
		displayName = "Spectator",
		canFly = true,
		canBreakBlocks = false,
		takesDamage = false,
		hasCollision = false,
		hasGravity = false,
		canInteract = false
	);

	companion object {
		private val BY_ID = entries.associateBy(GameMode::id)

		fun fromId(id: Int): GameMode =
			BY_ID[id] ?: SURVIVAL
	}

	override fun toString(): String = displayName
}
