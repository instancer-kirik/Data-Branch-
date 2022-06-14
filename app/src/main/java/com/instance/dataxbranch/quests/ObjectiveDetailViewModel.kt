package com.instance.dataxbranch.quests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.QuestWithObjectives
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.repository.LocalQuestsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ObjectiveDetailViewModel (

    private val oid: Long,
    private val repository: LocalQuestsRepository,

): ViewModel() {

    val obj: StateFlow<ObjectiveEntity?> = repository.objForOId(oid)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val qwo: StateFlow<QuestWithObjectives?> = repository.objForOId(oid)
        .flatMapConcat {
            repository.questById(it.id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun onObjectiveEntityCheckedChange(obj:ObjectiveEntity,checked: Boolean) {
        //val value = obj.value ?: return
        viewModelScope.launch {
            repository.update(obj.copy(completed = checked))
        }
    }

}