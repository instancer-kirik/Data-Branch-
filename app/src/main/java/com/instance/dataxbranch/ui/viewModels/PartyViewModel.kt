package com.instance.dataxbranch.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.cloud.PartyRepository
import com.instance.dataxbranch.data.entities.CharacterEntity
import com.instance.dataxbranch.data.entities.Mob
import com.instance.dataxbranch.data.entities.Party

import com.instance.dataxbranch.data.entities.PartyMember
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.repository.GeneralRepository
import com.instance.dataxbranch.domain.use_case.UseCases
import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.utils.CombatManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyViewModel @Inject constructor(
    private val generalRepository: GeneralRepository,
    private val partyRepository: PartyRepository,
    private val useCases: UseCases
) : ViewModel() {



    private val _openDialogState = MutableStateFlow(false)
    val openDialogState: StateFlow<Boolean> = _openDialogState

    private val _partyMembers = MutableStateFlow<List<PartyMember>>(emptyList())
    val partyMembers: StateFlow<List<PartyMember>> = _partyMembers

    private val _pendingQuests = MutableStateFlow<List<Quest>>(emptyList())
    val pendingQuests: StateFlow<List<Quest>> = _pendingQuests

    private val _acceptedQuests = MutableStateFlow<List<Quest>>(emptyList())
    val acceptedQuests: StateFlow<List<Quest>> = _acceptedQuests

    private val _completedQuests = MutableStateFlow<List<Quest>>(emptyList())
    val completedQuests: StateFlow<List<Quest>> = _completedQuests

    init {
        loadPartyMembers()
        loadPendingQuests()
        loadAcceptedQuests()
        loadCompletedQuests()
    }

    private fun loadPartyMembers() {
        viewModelScope.launch {
            val members = partyRepository.getPartyMembers()
            _partyMembers.value = members
        }
    }

    private fun loadPendingQuests() {
        viewModelScope.launch {
            val quests = partyRepository.getPendingQuests()
            _pendingQuests.value = quests
        }
    }

    private fun loadAcceptedQuests() {
        viewModelScope.launch {
            val quests = partyRepository.getAcceptedQuests()
            _acceptedQuests.value = quests
        }
    }

    private fun loadCompletedQuests() {
        viewModelScope.launch {
            val quests = partyRepository.getCompletedQuests()
            _completedQuests.value = quests
        }
    }

    fun openDialog() {
        _openDialogState.value = true
    }

    fun closeDialog() {
        _openDialogState.value = false
    }
    /*@Entity(tableName = "parties")
data class PartyEntity(
    @PrimaryKey val partyId: String,
    val leaderId: String,
    val name: String,
    val description: String,
    val members: List<String>
)
*/
    fun createParty(partyName: String, leader: PartyMember, others: List<PartyMember>) {
        viewModelScope.launch {
            val newParty = Party(
                partyId = partyRepository.newPartyId(),
                leader = leader,
                name = partyName,
                description = "",
                members = others
            )

            partyRepository.createParty(newParty)
        }
    }
    fun createPartyMember(user:User,character: CharacterEntity) {
        val member = PartyMember(user,character)
        viewModelScope.launch {
            partyRepository.addMemberToParty(partyRepository.getPartyId(),member)
            _partyMembers.value = partyMembers.value + member
        }
    }

    fun addQuestToPendingAcceptance(quest: Quest) {
        viewModelScope.launch {
            partyRepository.addQuestToPendingAcceptance(quest)
            _pendingQuests.value = pendingQuests.value + quest
        }
    }

    fun removeQuestFromPendingAcceptance(quest: Quest) {
        viewModelScope.launch {
            partyRepository.removeQuestFromPendingAcceptance(quest)
            _pendingQuests.value = pendingQuests.value - quest
        }
    }

    fun acceptQuest(quest: Quest) {
        viewModelScope.launch {
            partyRepository.acceptQuest(quest.qid)
            _pendingQuests.value = pendingQuests.value - quest
            _acceptedQuests.value = acceptedQuests.value + quest
        }
    }
    fun completeQuest(quest: Quest) {
        viewModelScope.launch {
            partyRepository.completeQuest(quest.qid)
            _acceptedQuests.value = acceptedQuests.value - quest
            _completedQuests.value = completedQuests.value + quest
        }
    }
    fun engageInCombat(enemy: Mob) {
        viewModelScope.launch {
            CombatManager(partyMembers.value, listOf(enemy)).startCombat()

        }
    }

    /*fun performOtherPartyAction(action: PartyAction) {
        // Handle other party actions here
    }*/}
