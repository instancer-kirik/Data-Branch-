package com.instance.dataxbranch.data.cloud



import com.instance.dataxbranch.data.entities.CampaignEntity
import com.instance.dataxbranch.data.entities.CharacterEntity
import com.instance.dataxbranch.data.entities.Party

import com.instance.dataxbranch.data.entities.PartyMember
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.CloudQuest

import com.surrealdb.connection.SurrealWebSocketConnection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow


interface CloudGeneralRepository{
   // var questsRef: CollectionReference
    fun getSignedInUser(): String

    //QUESTS
    fun getQuestById(qid: String):Flow<Response<Quest>>
    fun getQuestsFromCloud(): Flow<Response<List<Quest>>>
    fun addQuestToCloud(title: String, description: String, author:String): Flow<Response<CloudQuest>> //ORIGINALLY HAD SUSPEND FUN
    fun addQuestToCloud(quest: CloudQuest): Flow<Response<Void?>>
    fun addQuestToCloud(quest: Quest): Flow<Response<Void?>>
    fun deleteQuestFromCloud(qid: String): Flow<Response<Void?>>
    fun updateQuest(quest: Quest): Flow<Response<MutableList<CloudQuest>>>
    fun deleteQuestFromCloud(quest: CloudQuest): Flow<Response<Void?>>

    fun getUserById(userId: String): Flow<Response<User>>
    fun getUsersFromCloud(): Flow<Response<List<User>>>

    //USERS

    fun addUserToCloud(user: User): Flow<Response<Void?>>
    fun updateUser(user: User): Flow<Response<Void?>>
    fun deleteUserFromCloud(uid: String): Flow<Response<Void?>>
    fun deleteUserFromCloud(user: User): Flow<Response<Void?>>

    fun connect(): Flow<Response<SurrealWebSocketConnection>>
} //  fun mintNFTQuest(cloudQuest: CloudQuest) :Boolean
@Module
@InstallIn(ViewModelComponent::class)
abstract class CloudModule {

    @Binds
    abstract fun bindCloudGeneralRepository(
        cloudGeneralRepositoryImpl: CloudGeneralRepositoryImpl//        CloudServiceImpl
    ): CloudGeneralRepository
}
//others
interface UserRepository {
    fun getUserById(userId: String): User?
    fun createUser(user: User)
    fun updateUser(user: User)
    fun deleteUser(userId: String)
    // Add other user-related methods as needed
}
interface CloudCharacterRepository {
    // Characters
    fun getCharacterById(characterId: String): Flow<Response<CharacterEntity>>
    fun getCharactersByUserId(userId: String): Flow<Response<List<CharacterEntity>>>
    fun addCharacterToCloud(character: CharacterEntity): Flow<Response<Void?>>
    fun deleteCharacterFromCloud(characterId: String): Flow<Response<Void?>>
    fun updateCharacter(character: CharacterEntity): Flow<Response<Void?>>

    // Quests
    fun getQuestById(questId: String): Flow<Response<QuestEntity>>
    fun getQuestsByCharacterId(characterId: String): Flow<Response<List<QuestEntity>>>
    fun addQuestToCloud(quest: QuestEntity): Flow<Response<Void?>>
    fun deleteQuestFromCloud(questId: String): Flow<Response<Void?>>
    fun updateQuest(quest: QuestEntity): Flow<Response<Void?>>
}
interface PartyRepository {
    fun newPartyId(): String
    fun getPartyId(): String
    fun getPartyById(partyId: String): Party?
    fun createParty(party: Party)
    fun updateParty(party: Party)
    fun deleteParty(partyId: String)

    //members
    fun getPartyMembers(): List<PartyMember>
    fun addMemberToParty(partyId: String, member: PartyMember)
    fun removeMemberFromParty(partyId: String, memberId: String)

    //quests
    fun getAcceptedQuests(partyId: String = getPartyId()): List<Quest>
    fun getAvailableQuests(): List<Quest>
    fun getPendingQuests(): List<Quest>
    fun getCompletedQuests(): List<Quest>
    fun acceptQuest(questId: String):Boolean
    fun addQuestToPendingAcceptance(quest: Quest)
    fun removeQuestFromPendingAcceptance(quest: Quest)
    fun completeQuest(questId: String):Boolean

    // Add other party-related methods as needed

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class PartyModule {

        @Binds
        abstract fun bindPartyRepository(
            partyRepositoryImpl: PartyRepositoryImpl//        CloudServiceImpl
        ): PartyRepository
    }
}
interface CampaignRepository {
    fun getCampaignById(campaignId: String): CampaignEntity?
    fun createCampaign(campaign: CampaignEntity)
    fun updateCampaign(campaign: CampaignEntity)
    fun deleteCampaign(campaignId: String)
    fun addQuestToCampaign(campaignId: String, questId: String)
    fun removeQuestFromCampaign(campaignId: String, questId: String)
    // Add other campaign-related methods as needed
}

