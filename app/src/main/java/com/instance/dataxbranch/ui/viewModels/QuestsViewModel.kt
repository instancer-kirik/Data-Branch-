package com.instance.dataxbranch.ui.viewModels


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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



    //new one
    private val _addedQuestState = MutableLiveData<Response<Quest>>()
    val addedQuestState: LiveData<Response<Quest>> = _addedQuestState
    private val _isQuestDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isQuestDeletedState: State<Response<Void?>> = _isQuestDeletedState

    var openDialogState = mutableStateOf(false)

    init {
        getQuests()
    }

    fun onQuestSelected(quest: Quest) {
        selectedQuest.value = quest
    }
    fun getFirstQuestFromList(): Quest {
        val response = questsState.value
        return if (response is Response.Success && response.data.isNotEmpty()) {
            response.data[0] // Return the first quest in the list
        } else {
            Quest() // Return a default quest if the list is empty or the response is not success
        }
    }
    private fun getQuests() {
        viewModelScope.launch {
            cloudRepository.getQuestsFromCloud().collect { response ->
                _questsState.value = response
            }
        }
    }


        fun addQuest(title: String, description: String, author: String) {
            Log.d("QuestsViewModel", "in addQuest: $title $author")
            viewModelScope.launch {
                try {
                    cloudRepository.addQuestToCloud(title, description, author)
                        .collect { response ->
                            when (response) {
                                is Response.Loading -> {
                                    // Handle loading state if needed
                                    Log.d("QuestsViewModel", "Loading state")
                                }

                                is Response.Success -> {
                                    val quest = response.data
                                    Log.d("QuestsViewModel", "success addQuest: $quest")
                                    _addedQuestState.value = Response.Success(quest.toQuest())
                                    selectedQuest.value = quest.toQuest()
                                }

                                is Response.Error -> {
                                    val error = response.message
                                    Log.e("QuestsViewModel", "ERROR addQuest: $error")
                                    _addedQuestState.value = Response.Error(error)
                                }
                            }
                        }
                } catch (e: Exception) {
                    // Handle the exception
                    _addedQuestState.value = Response.Error(e.toString())
                }

            }
    }
/*
        Log.d("QuestsViewModel", "in addQuest: $title $author")
        return  try {

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
                        Log.d("QuestsViewModel", "success addQuest: $quest")
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
    }*/
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
        fun addQuest(inQuest: Quest) {
            Log.d("QuestsViewModel", "in addQuest with quest: ${inQuest.title} $inQuest ")
    viewModelScope.launch {try {
                Log.d("QuestsViewModel", "334")
                cloudRepository.addQuestToCloud(inQuest).collect { response ->
                    Log.d("QuestsViewModel", "555")
                    when (response) {
                        is Response.Loading -> {
                            // Handle loading state if needed
                            Log.d("QuestsViewModel", "Loading state")
                        }
                        is Response.Success -> {
                            val quest = response.data
                            Log.d("QuestsViewModel", "success addQuest: ${inQuest.title} $inQuest")
                            _addedQuestState.value = Response.Success(inQuest)
                            selectedQuest.value = inQuest

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
    }}

    fun deleteQuest(inQuest: Quest) {
        Log.d("QuestsViewModel", "in deleteQuest with quest: ${inQuest.title} $inQuest ")
        viewModelScope.launch {try {
            //Log.d("QuestsViewModel", "334")
            cloudRepository.deleteQuestFromCloud(inQuest.qid).collect { response ->
                Log.d("QuestsViewModel", "555")
                when (response) {
                    is Response.Loading -> {
                        // Handle loading state if needed
                        Log.d("QuestsViewModel", "Loading state")
                    }
                    is Response.Success -> {response
                        val quest = response.data
                        Log.d("QuestsViewModel", "success deleteQuest: ${inQuest.title} $inQuest")
                        selectedQuest.value = getFirstQuestFromList()
                        _isQuestDeletedState.value = response
                    }
                    is Response.Error -> {
                        val error = response.message
                        Log.e("QuestsViewModel", "ERROR deleteQuest: $error")
                        throw Exception(error)
                    }
                }
            }
            selectedQuest.value // Return the selectedQuest value if needed
        } catch (e: Exception) {
            throw e
        }
    }}

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