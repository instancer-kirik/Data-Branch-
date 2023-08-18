package com.instance.dataxbranch.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "entourages")
data class Entourage(//maybe only need partyId and leaderId
    //locally stored party should not have a partyId, only the server should,
    @PrimaryKey val entId: String,
    val characterId: String,
    val name: String,
    val description: String,
    val members: List<String>
) {
    // Additional properties and methods related to the party can be added here
}
class EntourageMember(
    val mob:Mob,
)

data class Party(
    val partyId: String,
    val leader: PartyMember,
    val name: String,
    val description: String,
    val members: List<PartyMember>
){
    fun getPartyMembers(): List<PartyMember> {
        return members
    }
}

class PartyMember(
    val user: User,
    val character: CharacterEntity
) {
// Additional properties and methods related to the party member can be added here
}

@Entity(tableName = "campaigns")
data class CampaignEntity(
    @PrimaryKey val campaignId: String,
    val leaderId: String,
    val name: String,
    val description: String,
    val quests: List<String>
)