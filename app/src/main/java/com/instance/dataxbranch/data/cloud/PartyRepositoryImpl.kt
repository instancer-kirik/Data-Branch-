package com.instance.dataxbranch.data.cloud

import com.instance.dataxbranch.data.entities.Party
import com.instance.dataxbranch.data.entities.PartyMember
import com.instance.dataxbranch.quests.Quest
import java.util.UUID
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(

) : PartyRepository {
    val partyList = mutableListOf<Party>()
    val partyMemberList = mutableListOf<PartyMember>()
    val questList = mutableListOf<Quest>()

    override fun newPartyId(): String {
        return UUID.randomUUID().toString()
    }

    override fun getPartyId(): String {
        TODO("Not yet implemented")
    }

    override fun getPartyById(partyId: String): Party? {
        TODO("Not yet implemented")
    }

    override fun createParty(party: Party) {
        TODO("Not yet implemented")
    }

    override fun updateParty(party: Party) {
        partyList.add(party)
    }

    override fun deleteParty(partyId: String) {
        TODO("Not yet implemented")
    }

    override fun getPartyMembers(): List<PartyMember> {
        TODO("Not yet implemented")
    }

    override fun addMemberToParty(partyId: String, member: PartyMember) {
        TODO("Not yet implemented")
    }

    override fun removeMemberFromParty(partyId: String, memberId: String) {
        TODO("Not yet implemented")
    }

    override fun getAcceptedQuests(partyId: String): List<Quest> {
        TODO("Not yet implemented")
    }

    override fun getAvailableQuests(): List<Quest> {
        TODO("Not yet implemented")
    }

    override fun getPendingQuests(): List<Quest> {
        TODO("Not yet implemented")
    }

    override fun getCompletedQuests(): List<Quest> {
        TODO("Not yet implemented")
    }

    override fun acceptQuest(questId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun addQuestToPendingAcceptance(quest: Quest) {
        TODO("Not yet implemented")
    }

    override fun removeQuestFromPendingAcceptance(quest: Quest) {
        TODO("Not yet implemented")
    }

    override fun completeQuest(questId: String): Boolean {
        TODO("Not yet implemented")
    }
}