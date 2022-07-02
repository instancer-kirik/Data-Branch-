package com.instance.dataxbranch.ui.viewModels


import android.content.Context
import android.util.Log

import com.instance.dataxbranch.data.daos.AbilityDao
import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.local.GeneralRepository


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.firestore.FirestoreUser
import com.instance.dataxbranch.data.local.UserWithAbilities
//import com.instance.dataxbranch.di.AppModule_ProvideDbFactory.provideDb
import com.instance.dataxbranch.domain.use_case.UseCases
import com.instance.dataxbranch.showToast


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val appDatabase: AppDatabase,
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle,
    val generalRepository: GeneralRepository,

    val udao: UserDao,
    val qdao: QuestDao,
    val adao: AbilityDao,
    //var

    ): ViewModel() {
    lateinit var selectedAE: AbilityEntity
    var currentSite: String= "https://sites.google.com/view/instance-select/home"

    //val _qwe:
    //val _quests: MutableStateFlow<List<QuestWithObjectives>> = MutableStateFlow(listOf<QuestWithObjectives>())
   // val QuestFlow: StateFlow<List<QuestWithObjectives>> get() = _quests
   //var
    //val _meWithAbilities: MutableStateFlow<UserWithAbilities> = MutableStateFlow(UserWithAbilities())
    private val downloadQueue: MutableMap<Int, Flow<Int>> = mutableMapOf()
    var openDialogState = mutableStateOf(false)
    var openDialogState2 = mutableStateOf(false)
    var openDialogState3 = mutableStateOf(false)
    var termsDialogState = mutableStateOf(false)
    var refreshWebview = mutableStateOf(false)
    var downloadCloudDialog = mutableStateOf(false)
    var mfsid:String = "-2"
    val handyString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private var meWithAbilities=generalRepository.getMe()
    init {
refresh()
        selectedAE=AbilityEntity()
    }
    fun refresh() : String {
        generalRepository.refresh()
        viewModelScope.launch {
           /* abilities = generalRepository.aDao.getAbilites()
            // Coroutine that will be canceled when the ViewModel is cleared.*/
            meWithAbilities=generalRepository.getMe()
        }
        return meWithAbilities.user.uname
    }


    fun addNewAbilityEntity(ae: AbilityEntity){
        CoroutineScope(Dispatchers.IO).launch { adao.insert(ae)}
    }
    fun addNewAbilityEntity(title: String){ //might be better to just do on new title
        CoroutineScope(Dispatchers.IO).launch { adao.insert(AbilityEntity(title=title,author=meWithAbilities.user.uname))}
    }
    fun addNewAbilitiyEntity() {
        CoroutineScope(Dispatchers.IO).launch { adao.insert(AbilityEntity()) }

        //dao.insert(QuestEntity(title = title, author = author))
    }
    fun update(ae: AbilityEntity){
        CoroutineScope(Dispatchers.IO).launch {
            adao.update(ae)
        }}

    fun update(me:User){

        CoroutineScope(Dispatchers.IO).launch {
            udao.save(me)
           // udao.update(me)
        }}

    fun getMeWithAbilities(): UserWithAbilities {
        refresh()
        return meWithAbilities
    }
    fun sync(){
        //update(me)
        setSelect()
        //generalRepository.sync()
        refresh()
    }
    fun fixattunement(){
        meWithAbilities.fixattunment()
    }
    fun getSelect() {
        selectedAE=generalRepository.selectedAE
    }
    private fun setSelect() {
        generalRepository.selectedAE= selectedAE
    }

    fun logMeIn(context:Context,db:FirebaseFirestore) {//happens after a valid login uses auth

        val auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        // if(currentUser != null){ reload(); }
        if (currentUser != null) {
            readUserData(context,db=db,fsid=currentUser.uid)

            }

        }
    fun logMeIn(context:Context,db:FirebaseFirestore,fsid: String) {//ALready has account happens after a valid login with fsid
        if(meWithAbilities.user.isreal){//merge with cloud one if cloud one is newer
            val oldme=meWithAbilities.copy()
            readUserData(context,db,fsid)
            val newer=oldme.user.whichNewer(qDate=meWithAbilities.user.dateUpdated)
            if (newer==1){
                showToast(context,oldme.user.dateUpdated +"\n >meWithAbilities.user.dateUpdated \n"+meWithAbilities.user.dateUpdated)

            }else if (newer ==0){
                meWithAbilities = oldme
                Log.d(TAG,"This may eat 2 reads per overwrite, reduce to 1")
                showToast(context, "almost overwrote but cloud is older")
                downloadCloudDialog.value =true
            }else if (newer ==-1){
                Log.d(TAG,"TIME COMPARE GOT TO LOGMEIN")}
        }else{//clear data
            readUserData(context,db,fsid)}

    }
    fun overwriteLogIn(context:Context,db:FirebaseFirestore,fsid: String) {//ALready has account happens after a valid login with fsid
            readUserData(context,db,fsid)
    }
    fun createAndLogMeIn(context:Context,db:FirebaseFirestore,fsid: String) {//happens after a valid login with fsid
        writeUserData(context,db,fsid)
 /*if(meWithAbilities.user.isreal){//merge with cloud one if cloud one is newer


        }else{//clear data
            readUserData(context,db,fsid)}
*/
    }

    fun readUserData(context:Context,db: FirebaseFirestore,fsid:String):UserWithAbilities{
        val me = db.collection("users").document(fsid).get().addOnSuccessListener {
            //userRepository

            if (it != null) {

                it.toObject(FirestoreUser::class.java)?.let { it1 ->
                    meWithAbilities.user.combine(it1.toLocalUser())
                }//does not yet support cloud abilities as I do not have cloud abilities
                meWithAbilities.user.cloud = true
            }//If this works, awesome. otherwise, I use FirestoreUser.toLocalUser

            //meWithAbilities.user.initflag = true
            meWithAbilities.user.isreal = true }
            .addOnFailureListener({e->
                generalRepository.getMe()
                showToast(context,e.toString())
            })

        generalRepository.setMe(meWithAbilities)
        return meWithAbilities
    }
  /*
    db.ref('users/' + user.uid).set(user).catch(error => {
        console.log(error.message)
    });*/
    fun writeUserData(context: Context, db:FirebaseFirestore,fsid: String = meWithAbilities.user.fsid){//to firestore

        db.collection("users")
            .add(
            meWithAbilities.toFireStoreUser(fsid)
            )
            .addOnSuccessListener { showToast(context,"wrote to firestore! c;") }
            .addOnFailureListener { e -> showToast(context, "Error writing document $e") }

    }}

    /*val rowsInserted: MutableLiveData<Int> = MutableLiveData()
    override fun onCleared() {
        rowsInserted.removeObserver(observer)
        super.onCleared()
    }*/
   /* private fun getQuests() {
        viewModelScope.launch(Dispatchers.IO) {
            val qwe = arrayListOf<QuestWithObjectives>()


            _quests.emit(qwe)
        }
    }*/

    //Update your coroutine
    /*fun insert(collectionItem: QuestWithObjectives) = viewModelScope.launch {
        val result = localQuestsRepository.insertCollectionItem(collectionItem)
        rowsInserted.postValue(result)
    }*/
    //private val _isQuestAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    //val isQuestAddedState: State<Response<Void?>> = _isQuestAddedState
    //var openDialogState = mutableStateOf(false)
    //private val _questsState = mutableStateOf(listOf(QuestEntity::class))
    //val qes: Flow<List<QuestEntity?>> = questDao.getAll()
    //fun getQuests() :Array<QuestEntity>{
    //return quests


   /* fun addAbilityEntity(quest: Quest) {
        CoroutineScope(Dispatchers.IO).launch {
            quest.toRoom(dao)
            quest.objectives.forEach { Log.d("RQVM"," OBJ seen $it")}//objective->objective.convert()  }

        }
    }*/


    /*fun loadObjectivesa(quest: QuestWithObjectives):List<ObjectiveEntity> {

        CoroutineScope(Dispatchers.IO).launch {
            quest.objectives = dao.getObjectiveEntityList(id = quest.quest.id)
        }
        return quest.objectives
    }*/

    /*fun onObjCheckedChanged(obj:ObjectiveEntity, checked: Boolean) {//saves and spits log
        //Log.d(TAG, "$obj is is $checked   "+obj.completed.toString())
        //obj.completed=checked
        CoroutineScope(Dispatchers.IO).launch {generalRepository.update(obj)}
        *//*viewModelScope.launch {
            localQuestsRepository.update(obj.copy(completed = checked))
        }*//*
    }*/

    /*fun onCheckboxChecked(quest: QuestWithObjectives, checked: Boolean) {
        //Log.d(TAG, "$quest is is $checked   "+quest.quest.completed.toString())
        viewModelScope.launch {
           GeneralRepository.update(quest.quest.copy(completed = checked))
        }
    }*/
    /*fun loadQuestWithObjectives(id: Int): QuestWithObjectives =withContext(Dispatchers.IO){
             dao.getQuestWithObjectives(id)//this just get the first one. idk what array wrapper but whatever

       }*/
    /* fun loadObjectivesb(quest: QuestWithObjectives):QuestWithObjectives {

         CoroutineScope(Dispatchers.IO).launch {
             quest.objectives = dao.loadObjectivesByqid(qid = quest.quest.qid!!)
         }
         return quest

     }*/
/*
    fun getQuestWithObjectives(id:Long): QuestWithObjectives {

        return dao.getQuestWithObjectives(id) }*/








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