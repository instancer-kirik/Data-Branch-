package com.instance.dataxbranch.quests
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.QuestContainerLocal
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.local.AppDatabase
import com.instance.dataxbranch.data.repository.LocalQuestsRepository
//import com.instance.dataxbranch.di.AppModule_ProvideDbFactory.provideDb
import com.instance.dataxbranch.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomQuestViewModel @Inject constructor(
    val appDatabase: AppDatabase,
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle,
    val localQuestsRepository: LocalQuestsRepository,
    val dao: QuestDao,
    var quests :Array<QuestContainerLocal>
    ): ViewModel() {
    init {
        quests=getQuestsFromRepo()
        viewModelScope.launch {

            // Coroutine that will be canceled when the ViewModel is cleared.
        }
    }

    //private val _isQuestAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    //val isQuestAddedState: State<Response<Void?>> = _isQuestAddedState
    var openDialogState = mutableStateOf(false)
    //private val _questsState = mutableStateOf(listOf(QuestEntity::class))
    //val qes: Flow<List<QuestEntity?>> = questDao.getAll()
    //fun getQuests() :Array<QuestEntity>{
    //return quests

    fun getQuestsFromRepo():Array<QuestContainerLocal>{
        return localQuestsRepository.getQuests()
    }
    fun addNewQuestEntity(title: String, description:String,author: String) {
        CoroutineScope(Dispatchers.IO).launch { useCases.addNewQuestEntity(title,description, author) }

        //dao.insert(QuestEntity(title = title, author = author))
    }

    fun addQuestEntity(quest: Quest) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(quest.toRoom())
            quest.objectives.forEach { Log.d("RQVM","OBJ seen")}//objective->objective.convert()  }

        }
    }

    fun refresh() {
        localQuestsRepository.refresh()
        quests=getQuestsFromRepo()
    }

}


/*
val quests: StateFlow<List<QuestEntity>> =
    .combine(stepsRepository.steps) { tasks, steps ->
        tasks.map { task ->
            TaskUiItem(task, steps.filter { it.taskId == task.id })
        }
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

fun onCheckboxChecked(task: Task, checked: Boolean) {
    viewModelScope.launch {
        tasksRepository.updateTask(task.copy(completed = checked))
    }
}
*/
/*
    fun getQuests(): StateFlow<List<QuestEntity>>{
            viewModelScope.launch {
                useCases.getLocalQuests().collect{it->
                    _questsState.value = it

                }
            }

    }


        //private val navArgs: TaskScreenNavArgs = savedStateHandle.navArgs()



       // val steps: StateFlow<List<Step>> = stepsRepository.stepsByTask(navArgs.taskId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
/*
        fun onStepCheckedChanged(step: Step, checked: Boolean) {
            viewModelScope.launch {
                stepsRepository.updateStep(step.copy(completed = checked))
            }
        }

        fun onTaskCheckedChange(checked: Boolean) {
            val task = task.value ?: return
            viewModelScope.launch {
                tasksRepository.updateTask(task.copy(completed = checked))
            }
        }*/