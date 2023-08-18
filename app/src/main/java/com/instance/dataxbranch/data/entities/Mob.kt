package com.instance.dataxbranch.data.entities

import androidx.room.Entity
import com.instance.dataxbranch.utils.Attack
import dagger.multibindings.IntoMap

@Entity(tableName = "mobs")
data class Mob(
    val mobId: String,
    val name: String,
    val mobImage: String,
    val mobDescription: String,
    val mobType: MobType,
    //stats
    val level: Int,
    val maxHealth: Int,
    val attack: Int,
    val defense: Int,
    val agility: Int,
    val intelligence: Int,
    val luck: Int,
    val accuracy: Int,
    val evasion: Int,
    val currentStatusEffects: List<String>,
    val currentHealth: Int,
    val currentEnergy: Int,

    //rewards
    val experienceReward: Int,
    val goldReward: Int,
    val itemDropId: String,
    val itemDropChance: Int,
    val itemDropQuantity: Int,


    val attacks: List<Attack>
) {
    // Additional properties and methods related to mobs can be added here
}

enum class MobType {
    NPC,
    Monster,
    Boss,
    Summon,
    Familiar,
    Undead,
    Animal,
    Elemental,
    Humanoid,
    MagicalBeast,
    Construct,
    Demon,
    Spirit,
    Insect,
    Dragon,
    Ghost,
    MythicalCreature
}