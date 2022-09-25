package com.instance.dataxbranch.ui.viewModels


import android.content.Context
import android.util.Log


import com.instance.dataxbranch.data.daos.AbilityDao
import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.repository.GeneralRepository


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
import com.instance.dataxbranch.data.entities.*
import com.instance.dataxbranch.data.firestore.FirestoreUser
import com.instance.dataxbranch.data.local.CharacterWithStuff
import com.instance.dataxbranch.data.local.UserWithAbilities
//import com.instance.dataxbranch.di.AppModule_ProvideDbFactory.provideDb
import com.instance.dataxbranch.domain.use_case.UseCases
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.showToast


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

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
    lateinit var selectedItem: ItemEntity
    lateinit var selectedAE: AbilityEntity
    lateinit var selectedQuest: QuestWithObjectives
    //var selectedCharacterWithStuff: CharacterWithStuff=generalRepository.selectedCharacterWithStuff
   // var selectedCharacterIndex:Int = 0
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
    var singleConditionsDialog = mutableStateOf(true)
    var characterDialogState = mutableStateOf(false)
    var allabilities = mutableStateOf(false)
    var inventoryModeState = mutableStateOf(false)
    //var mfsid:String = "-2"
    val handyString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private var meWithAbilities=generalRepository.getMe()
    var attunement = mutableStateOf(meWithAbilities.user.attunement)
    var attuned = mutableStateOf(getAttuned())
    var userContainer: User?=null
    init {
refresh()
        selectedAE = if(meWithAbilities.abilities.isNotEmpty()) {
            meWithAbilities.abilities[0]
        }else{
            AbilityEntity()
        }

        selectedQuest = if(getSelectedCharacter().quests.isNotEmpty()) {
            getSelectedCharacter().quests[0]
        }else{
            QuestWithObjectives(QuestEntity(title="init"),listOf(ObjectiveEntity(obj="init")))
        }
        selectedItem = if(getItems().isNotEmpty()){
            getItems()[0]
        } else{
            ItemEntity()
        }
    }
    fun refresh(andQuests:Boolean = false) : String {
        generalRepository.sync()
        viewModelScope.launch {
           /* abilities = generalRepository.aDao.getAbilites()
            // Coroutine that will be canceled when the ViewModel is cleared.*/
            meWithAbilities=generalRepository.getMe()
            if(andQuests){
                generalRepository.questsRepository.refresh()
                generalRepository.updateCharacterQuests()
            }
        }



        fixattunement()
        return meWithAbilities.user.uname
    }
    fun getItems():Array<ItemEntity>{
        return generalRepository.itemRepository.getitems()
    }
    fun getAllCharacters():List<CharacterWithStuff>{
        return generalRepository.mcharacters
    }
    fun setSelectedCharacter(index: Int){

        generalRepository.selectedCharacterIndex = index
        generalRepository.selectedCharacterWithStuff=generalRepository.mcharacters[generalRepository.selectedCharacterIndex]

        Log.d(TAG,"SELECTED CHARACTER IS INDEX ${generalRepository.selectedCharacterIndex} and is ${getSelectedCharacter()}")
    }
    fun setSelectedCharacter(character: CharacterWithStuff){
        generalRepository.selectedCharacterWithStuff=character

        Log.d(TAG,"WARNING, DOES NOT UPDATE INDEX SELECTED CHARACTER is ${getSelectedCharacter()}")
    }
    fun addCharacterEntity(name:String){
        generalRepository.makeACharacter(name)
    }
    /*fun getAttuned():Int{
        return if(meWithAbilities.user.attuned==0){
            if(attuned.value==0){
                0
            }else{
                 -1
            }
        }else meWithAbilities.user.attuned
    }*/
    fun getSelectedCharacter():CharacterWithStuff{
        return generalRepository.selectedCharacterWithStuff
    }
    fun getSelectedCharacterIndex():Int{
        return generalRepository.selectedCharacterIndex
    }
    fun getAttuned():Int{
        return if(meWithAbilities.user.attuned==0){
            meWithAbilities.abilities.filter{it.inloadout}.size
        }else meWithAbilities.user.attuned
    }
    fun addNewAbilityEntity(ae: AbilityEntity){
        generalRepository.insertAbility(ae)
    }
    fun addNewAbilityEntity(title: String){ //might be better to just do on new title
        val ae = AbilityEntity(title = title, author = generalRepository.getMe().user.uname)
        generalRepository.insertAbility(ae)
    }
    fun addNewAbilityEntityOnCharacter(title: String){ //might be better to just do on new title
        val ae = AbilityEntity(title = title, author = generalRepository.getMe().user.uname)
        putAbilityOnCharacter(ae)
        generalRepository.insertAbility(ae)

}
    fun putAbilityOnCharacter(ae: AbilityEntity=selectedAE){
        generalRepository.putAbilityOnCharacter(ae)
    }
    fun putItemOnCharacter(item: ItemEntity=selectedItem){
        generalRepository.putItemOnCharacter(item)
    }
    fun addNewItemEntityOnCharacter(name: String) {
        val item = ItemEntity(name=name, author = generalRepository.getMe().user.uname)
        putItemOnCharacter(item)
        generalRepository.insertItem(item =item)
    }
   /* fun addNewAbilityEntity() {
        CoroutineScope(Dispatchers.IO).launch { adao.insert(AbilityEntity()) }

        //dao.insert(QuestEntity(title = title, author = author))
    }*/
    fun update(ae: AbilityEntity){
        generalRepository.syncAE(ae)}

    fun update(me:User){

        CoroutineScope(Dispatchers.IO).launch {
            udao.save(me)
           // udao.update(me)
        }}

    fun onObjCheckedChanged(obj: ObjectiveEntity, b: Boolean){ CoroutineScope(Dispatchers.IO).launch {generalRepository.questsRepository.update(obj)} }
    fun addNewObjectiveEntity(quest: QuestWithObjectives){ CoroutineScope(Dispatchers.IO).launch { useCases.addNewObjectiveEntityToQuestEntity(quest)} }
    fun getQuestsFromRepo():Array<QuestWithObjectives>{ return generalRepository.questsRepository.getQuests() }
    fun addNewQuestEntity(title: String, description:String,author: String){
        var char = getSelectedCharacter()
        viewModelScope.launch {
            val result = generalRepository.newQuestOnCharacter(
                QuestEntity(
                    title = title,
                    description = description,
                    author = author
                )
            )
            Log.d(TAG, "11111character quests: ${char.quests.size}")
            //generalRepository.updateCharacterQuests()
            refresh(true)
            Log.d(TAG, "character quests: ${getSelectedCharacter().quests.size}")
            /*    CoroutineScope(Dispatchers.IO).launch {
            result =useCases.addNewQuestEntity(title,description, author) }*/
        }
    }

    fun getMeWithAbilities(): UserWithAbilities {
        refresh()
        return meWithAbilities
    }
    fun getFriends(): List<User>{//maybe do a simpler struct for friends
    //"make sure no secrets. make sure it works and updates on sync and such")
        return listOf(meWithAbilities.user,User(uname="friend1", name = "friend 1"))
        //return meWithAbilities.user.friends// stored as ids, must store more local or pull from cloud

    }
    fun update(oe: ObjectiveEntity){
        CoroutineScope(Dispatchers.IO).launch {
            qdao.update(oe)
        }}
    fun update(quest: QuestWithObjectives){
        CoroutineScope(Dispatchers.IO).launch {
            qdao.update(quest.quest)
        }}
    fun getAllFirestoreUsers(context:Context, db: FirebaseFirestore=FirebaseFirestore.getInstance()):List<FirestoreUser>{
        var output :List<FirestoreUser> =listOf()
        var cachedUsers = generalRepository.getCachedUsers()
        if (cachedUsers.isNotEmpty()){
            showToast(context, "Cache fetched success in uViewModel")
            return cachedUsers
        }else{
        db.collection("users").get().addOnSuccessListener {

            if (it != null) {
                output = it.toObjects(FirestoreUser::class.java)
                generalRepository.setUsers(output)
            } else {
                showToast(context, "GOT NULL")
            }
        }.addOnFailureListener { e ->
                showToast(context, e.toString())
        }


            showToast(context, "returning with length ${output.size}")
        return output
    }}
/*db.collection("cities")
        .whereEqualTo("capital", true)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }*/
    /*private fun getAllUsers() {
        viewModelScope.launch {
            useCases.getAllUsers().collect { response ->
                _questsState.value = response
            }
        }
    }*/
    fun syncSelectedAE(){
        update(selectedAE)
        updateAttuned()
    }
    fun sync(a:Int=attuned.value){

        //meWithAbilities.abilities.forEach { update(it) }
        update(meWithAbilities.user.apply{attuned=a})
        setSelect()
        generalRepository.sync()
        refresh()
    }
    fun syncAttunement(){
        meWithAbilities.user.attuned=attuned.value
    }

    fun updateAttuned(){
        meWithAbilities.user.attuned= meWithAbilities.abilities.filter{it.inloadout}.size
    }
    fun fixattunement(){
        updateAttuned()
        meWithAbilities.fixattunement()
    }
    fun getSelect() {
        selectedAE=generalRepository.selectedAE
    }
    private fun setSelect(it:AbilityEntity?=null) {
        if (it==null) {
            generalRepository.selectedAE = selectedAE
        }
        else{
            generalRepository.selectedAE=it

        }
        getSelect()
    }
    fun whoAmI(): String? {
        val auth = FirebaseAuth.getInstance()

       return ( auth.currentUser?.uid )

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
            userContainer=meWithAbilities.copy().user
            readUserData(context,db,fsid)

            val newer= userContainer!!.whichNewer(qDate=meWithAbilities.user.dateUpdated)
            if (newer==1){
                showToast(context, userContainer!!.dateUpdated +"\n >meWithAbilities.user.dateUpdated \n"+meWithAbilities.user.dateUpdated)

            }else if (newer ==0){
               // meWithAbilities = oldme
               // Log.d(TAG,"This may eat 2 reads per overwrite, reduce to 1")
                showToast(context, "cloud is in local, old local in container")

                downloadCloudDialog.value =true
            }else if (newer ==-1){
                showToast(context,"TIME COMPARE EXCEPTION GOT TO LOGMEIN")}
        }else{//clear data
            readUserData(context,db,fsid)}

    }
    fun overwriteLogIn(context:Context,db:FirebaseFirestore,fsid: String) {//ALready has account happens after a valid login with fsid
            //readUserData(context,db,fsid) this is too many reads. already have data
        showToast(context, "disregards container, keeps cloud user in local")
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
        meWithAbilities.user.fsid = fsid
        generalRepository.setMe(meWithAbilities)
        return meWithAbilities
    }
  /*
    db.ref('users/' + user.uid).set(user).catch(error => {
        console.log(error.message)
    });*/
    fun writeUserData(context: Context, db:FirebaseFirestore,fsid: String = meWithAbilities.user.fsid){//to firestore

        db.collection("users")
            .document(fsid)
            .set(meWithAbilities.toFireStoreUser(fsid))//, SetOptions.merge()
            .addOnSuccessListener { showToast(context,"wrote to firestore! c;") }
            .addOnFailureListener { e -> showToast(context, "Error writing document $e") }

    }
    fun onCheckboxChecked(quest: QuestWithObjectives, checked: Boolean,):String? {
        //Log.d(TAG, "$quest is is $checked   "+quest.quest.completed.toString())
        //dateLastDone = getNow()
        viewModelScope.launch {

            generalRepository.questsRepository.update(quest.quest.copy(completed = checked, ))
        }
        if(checked){
            return onQuestCompleted(quest)
        }
        return null
    }
    fun onHabitClick(quest:QuestWithObjectives):String {
        val char = getSelectedCharacter()
        quest.quest.onDone()
        generalRepository.questsRepository.update(quest)//notice different save style
       return if (quest.quest.isHabit){
            if (quest.quest.habitStreak>3) {
               if (Random.nextInt(0, 6) == 5) {//0,1,2,3,4,5. 5 grants reward
                   char.character.xp += quest.quest.rewardxp
                   save(char)
                   "Random Persistence Reward :3"//gets random reward
               }else{
                   "Good consistency"//default, no reward
               }

           }else{
               "Heating up!"
           }
        }else {
        "not habit?"

    }
    }

    fun onQuestCompleted(quest: QuestWithObjectives):String {
        val char = getSelectedCharacter()
        //Regular quest, default case
            meWithAbilities.user.xp += quest.quest.rewardxp
            char.character.xp += quest.quest.rewardxp

            //Plans: to handle other rewards, like tokens and items
            // status updates
            //notification to QuestGiver
            save(char)
            return "Quest Complete!"

        }


    fun save(c: CharacterWithStuff) {
        generalRepository.save(c)
    }


}

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