package com.instance.dataxbranch.ui.viewModels


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.cloud.CloudGeneralRepository
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.repository.GeneralRepository
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.domain.unpackResponse
import com.instance.dataxbranch.domain.use_case.UseCases
import com.instance.dataxbranch.quests.CloudQuest
import com.instance.dataxbranch.quests.Quest
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestsViewModel @Inject constructor(
    val useCases: UseCases,
    private val cloudRepository: CloudGeneralRepository,
): ViewModel() {
    private val _questsState = mutableStateOf<Response<List<Quest>>>(Response.Loading)
    val questsState: State<Response<List<Quest>>> = _questsState
    val selectedQuest = mutableStateOf(Quest())
    private val _isQuestAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isQuestAddedState: State<Response<Void?>> = _isQuestAddedState

    private val _isQuestDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isQuestDeletedState: State<Response<Void?>> = _isQuestDeletedState

    var openDialogState = mutableStateOf(false)

    init {
        getQuests()
    }


    private fun getQuests() {
        viewModelScope.launch {
            cloudRepository.getQuestsFromCloud().collect { response ->
                _questsState.value = response
            }
        }
    }
    private fun mapCloudResponseToQuestResponse(cloudResponse: Response<CloudQuest>): Response<Quest> {
        return when (cloudResponse) {
            is Response.Loading -> Response.Loading
            is Response.Success -> Response.Success(cloudResponse.data.toQuest())
            is Response.Error -> Response.Error(cloudResponse.message)
        }
    }
    suspend fun addQuest(title: String, description: String, author: String): Quest {
        Log.d("QuestsViewModel", "in addQuest: $title $author")
        return try {
            Log.d("QuestsViewModel", "334")
            cloudRepository.addQuestToCloud(title, description, author).collect { response ->
                Log.d("QuestsViewModel", "555")
                when (response) {
                    is Response.Loading -> {
                        // Handle loading state if needed
                        Log.d("QuestsViewModel", "Loading state")
                    }
                    is Response.Success -> {
                        val quest = response.data
                        Log.d("QuestsViewModel", "addQuest: $quest")
                        selectedQuest.value = quest.toQuest()
                    }
                    is Response.Error -> {
                        val error = response.message
                        Log.e("QuestsViewModel", "ERROR addQuest: $error")
                        throw Exception(error)
                    }
                }
            }
            selectedQuest.value // Return the selectedQuest value if needed
        } catch (e: Exception) {
            throw e
        }
    }
        /*return try {
            val cloudResponse = cloudRepository.addQuestToCloud(title, description, author).first()
            val questResponse = mapCloudResponseToQuestResponse(cloudResponse)

            unpackResponse(questResponse,
                { quest ->
                    Log.d("QuestsViewModel", "addQuest: $quest")
                    selectedQuest.value = quest
                },
                { error ->
                    Log.e("QuestsViewModel", "ERROR addQuest: $error")
                    throw Exception(error)
                })
            selectedQuest.value//optimal return case

        } catch (e: Exception) {
            // Handle the exception if necessary
            throw e
        }
    }*/
    fun addQuest(quest: Quest) {
        viewModelScope.launch {
         try {
            val cloudResponse = cloudRepository.addQuestToCloud(quest).first()
            //val questResponse = mapCloudResponseToQuestResponse(cloudResponse)
            selectedQuest.value = quest
            unpackResponse(cloudResponse,
                { Rquest ->
                    Log.d("QuestsViewModel", "addQuest: $quest")
                    //selectedQuest.value = quest
                },
                { error ->
                    throw Exception(error)
                })
            selectedQuest.value//optimal return case

        } catch (e: Exception) {
            // Handle the exception if necessary
            throw e
        }
    }}

    fun deleteQuest(quest: Quest) {
        viewModelScope.launch {
            val response = cloudRepository.deleteQuestFromCloud(quest.qid).first()
            unpackResponse(response,
                onSuccess = {
                            Log.d("QuestsViewModel", "deleteQuest: $quest")
                    // Handle the success case
                    // Perform any necessary operations after deleting the quest
                },
                onError = { errorMessage ->
                    // Handle the error case
                    // Display or handle the error message
                }
            )
        }
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