package com.instance.dataxbranch.quests


import androidx.annotation.UiThread
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.HttpException
import com.instance.dataxbranch.Quest
import com.instance.dataxbranch.data.repository.QuestsRepository
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class QuestsViewModel @Inject constructor(
    private val useCases: UseCases
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
        viewModelScope.launch {
            useCases.getQuests().collect { response ->
                _questsState.value = response
            }
        }
    }

    fun addQuest(title: String, author: String) {
        viewModelScope.launch {
            useCases.addQuest(title, author).collect { response ->
                _isQuestAddedState.value = response
            }
        }
    }

    fun deleteQuest(quest: Quest) {
        viewModelScope.launch {
            useCases.deleteQuest(quest.qid).collect { response ->
                _isQuestDeletedState.value = response
            }
        }
    }
}