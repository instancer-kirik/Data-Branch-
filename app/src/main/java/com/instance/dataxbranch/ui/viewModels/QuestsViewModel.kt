package com.instance.dataxbranch.ui.viewModels


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.domain.use_case.UseCases
import com.instance.dataxbranch.quests.Quest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestsViewModel @Inject constructor(
    val useCases: UseCases
): ViewModel() {
    private val _questsState = mutableStateOf<Response<List<Quest>>>(Response.Loading)
    val questsState: State<Response<List<Quest>>> = _questsState

    private val _isQuestAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isQuestAddedState: State<Response<Void?>> = _isQuestAddedState

    private val _isQuestDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isQuestDeletedState: State<Response<Void?>> = _isQuestDeletedState

    var openDialogState = mutableStateOf(false)

    init {
        getQuests()
    }


    private fun getQuests() {
       /* viewModelScope.launch {
            useCases.getQuests().collect { response ->
                _questsState.value = response
            }
        }*/
    }

    fun addQuest(title: String,description:String, author: String) {
    /*    viewModelScope.launch {
            useCases.addQuest.invoke(title, description, author).collect { response ->
                _isQuestAddedState.value = response
            }
        }*/
    }

    fun addQuest(quest: Quest) {
      /*  viewModelScope.launch {
            useCases.addQuestbyQuest(quest).collect { response ->
                _isQuestAddedState.value = response
            }
        }*/
    }

    fun deleteQuest(quest: Quest) {
        /*viewModelScope.launch {
            useCases.deleteQuest(quest.qid).collect { response ->
                _isQuestDeletedState.value = response
            }
        }*/
    }

    fun addQuestToRoom(quest: Quest) {//probably isnt a flow function
        viewModelScope.launch {
            useCases.addQuestEntitybyQuest(quest)
        }
    }
    fun addQuestEntityToRoom(quest: QuestEntity) {//probably isnt a flow function
        viewModelScope.launch {
            useCases.addQuestEntity(quest)
        }
    }

}