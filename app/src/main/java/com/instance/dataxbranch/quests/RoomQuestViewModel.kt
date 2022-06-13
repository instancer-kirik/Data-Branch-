package com.instance.dataxbranch.quests
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import androidx.room.Transaction
import com.instance.dataxbranch.data.QuestWithObjectives
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.local.AppDatabase
import com.instance.dataxbranch.data.repository.LocalQuestsRepository
//import com.instance.dataxbranch.di.AppModule_ProvideDbFactory.provideDb
import com.instance.dataxbranch.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoomQuestViewModel @Inject constructor(
    val appDatabase: AppDatabase,
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle,
    val localQuestsRepository: LocalQuestsRepository,
    val dao: QuestDao,
    var quests :Array<QuestWithObjectives>,


    ): ViewModel() {
    //val _qwe:
    val _quests: MutableStateFlow<List<QuestWithObjectives>> = MutableStateFlow(listOf<QuestWithObjectives>())
    val QuestFlow: StateFlow<List<QuestWithObjectives>> get() = _quests
    private val downloadQueue: MutableMap<Int, Flow<Int>> = mutableMapOf()
    val handyString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        quests=getQuestsFromRepo()
        viewModelScope.launch {

            // Coroutine that will be canceled when the ViewModel is cleared.
        }
    }

    val rowsInserted: MutableLiveData<Int> = MutableLiveData()
    override fun onCleared() {
        //rowsInserted.removeObserver(observer)
        super.onCleared()
    }
    private fun getQuests() {
        viewModelScope.launch(Dispatchers.IO) {
             val qwe = arrayListOf<QuestWithObjectives>()

            _quests.emit(qwe)
        }
    }
    fun addNewObjectiveEntity(quest:QuestWithObjectives){
        CoroutineScope(Dispatchers.IO).launch { useCases.addNewObjectiveEntityToQuestEntity(quest)}
    }
    //Update your coroutine
    fun insert(collectionItem: QuestWithObjectives) = viewModelScope.launch {
        val result = localQuestsRepository.insertCollectionItem(collectionItem)
        rowsInserted.postValue(result)
    }
    //private val _isQuestAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    //val isQuestAddedState: State<Response<Void?>> = _isQuestAddedState
    var openDialogState = mutableStateOf(false)
    //private val _questsState = mutableStateOf(listOf(QuestEntity::class))
    //val qes: Flow<List<QuestEntity?>> = questDao.getAll()
    //fun getQuests() :Array<QuestEntity>{
    //return quests

    fun getQuestsFromRepo():Array<QuestWithObjectives>{
        return localQuestsRepository.getQuests()

    }
    fun addNewQuestEntity(title: String, description:String,author: String) {
        CoroutineScope(Dispatchers.IO).launch { useCases.addNewQuestEntity(title,description, author) }

        //dao.insert(QuestEntity(title = title, author = author))
    }

    fun addQuestEntity(quest: Quest) {
        CoroutineScope(Dispatchers.IO).launch {
            quest.toRoom(dao)
            quest.objectives.forEach { Log.d("RQVM"," OBJ seen $it")}//objective->objective.convert()  }

        }
    }

    fun refresh() {
        localQuestsRepository.refresh()
        quests=getQuestsFromRepo()
    }
    fun loadObjectivesa(quest: QuestWithObjectives):List<ObjectiveEntity> {

        CoroutineScope(Dispatchers.IO).launch {
            quest.objectives = dao.getObjectiveEntityList(id = quest.quest.id)
        }
        return quest.objectives
    }
     /*fun loadQuestWithObjectives(id: Int): QuestWithObjectives =withContext(Dispatchers.IO){
              dao.getQuestWithObjectives(id)//this just get the first one. idk what array wrapper but whatever

        }*/
   /* fun loadObjectivesb(quest: QuestWithObjectives):QuestWithObjectives {

        CoroutineScope(Dispatchers.IO).launch {
            quest.objectives = dao.loadObjectivesByqid(qid = quest.quest.qid!!)
        }
        return quest

    }*/

    fun getQuestWithObjectives(id:Long): QuestWithObjectives {

        return dao.getQuestWithObjectives(id) }

    }






    /*fun getQuestWithObjectives(id: Int): List<QuestWithObjectives> {
      CoroutineScope(Dispatchers.IO).launch{dao.getQuestWithObjectives(id)}
        return result
    }
    */





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