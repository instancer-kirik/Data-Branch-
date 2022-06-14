package com.instance.dataxbranch.data.repository



import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.firestore.auth.User
import com.instance.dataxbranch.data.QuestWithObjectives
import com.instance.dataxbranch.data.daos.ObjectiveDao
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.local.AppDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Singleton


@Singleton
class LocalQuestsRepository(application: Application,db:AppDatabase) {
    val questDao: QuestDao
    //private var mquests: Array<QuestWithObjectives> =withContext(Dispatchers.IO){db.questDao().getItAll()} //MutableStateFlow<MutableList<QuestWithObjectives>>(mutableListOf()).apply {
    private val _quests: MutableLiveData<MutableList<QuestWithObjectives>> = MutableLiveData<MutableList<QuestWithObjectives>>()
    /*withContext(Dispatchers.IO){
         db.questDao().getItAll()*/

    private var _objectives = MutableStateFlow<List<ObjectiveEntity>>(emptyList())
    val objectives = _objectives.asStateFlow()

    /*private val _objectives: MutableStateFlow<MutableList<ObjectiveEntity>> = MutableStateFlow<MutableList<ObjectiveEntity>>(mutableListOf()).apply {
        value =withContext(Dispatchers.IO){
            getObjectives()
        } }*/
    var quests: MutableLiveData<MutableList<QuestWithObjectives>> = _quests
    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
    /* private fun initRepo(): Job =

         CoroutineScope(Dispatchers.IO).launch {

             _objectives=getObjectives()
         }*/
    fun addIssuePost(issuePost: IssuePost) {
        mIssuePostLiveData.value?.add(issuePost)
        mIssuePostLiveData.notifyObserver()
    }

        fun refresh(): Job =

            CoroutineScope(Dispatchers.IO).launch {
                //ests=
}

        init {

            questDao = db.questDao()



                //mquests =questDao.getItAll() already did this above remnant from lateinit bug
            //initRepo()
        }
        /*private suspend fun getObjectives(): MutableStateFlow<List<ObjectiveEntity>> {
            return withContext(Dispatchers.IO){questDao.getAllObjectives().asStateFlow}
        }*/



        fun insertQuestEntity(quest: QuestEntity): Job =
            CoroutineScope(Dispatchers.IO).launch {
                questDao.save(quest)
            }
        fun newObjectiveEntity(quest: QuestWithObjectives): Job =
            CoroutineScope(Dispatchers.IO).launch {
                val oe = ObjectiveEntity(id = quest.quest.id, quest = quest.toString())
                questDao.save(oe)
            }

        fun update(quest: QuestEntity): Job =
            CoroutineScope(Dispatchers.IO).launch {
                questDao.update(quest)

            }
    fun update(obj: ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.update(obj)

        }
    fun update(qwo: QuestWithObjectives): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.update(qwo.quest)
            qwo.objectives.forEach{obj->questDao.update(obj)}
        }
        fun deleteQuestEntity(quest: QuestWithObjectives): Job =
            CoroutineScope(Dispatchers.IO).launch {
                questDao.delete(quest)
            }

        fun getAllQuestsAsync(): Deferred<Unit> =
            CoroutineScope(Dispatchers.IO).async {
                questDao.loadAll()
            }

        //fun getQuests() = quests

        fun deleteAllRows(): Job =
            CoroutineScope(Dispatchers.IO).launch {
                questDao.deleteAllRows()
            }
        fun insertObjectiveEntities(vararg: ObjectiveEntity): Job =
            CoroutineScope(Dispatchers.IO).launch {
                questDao.save(vararg)
            }
        /*fun loadObjectivesByqid(qid: String): List<ObjectiveEntity>{//WRONG should use id for locals
            val quest = mquests.filter {   (key) -> key.qid==qid}.first()
            CoroutineScope(Dispatchers.IO).launch {

                quest.objectives = questDao.loadObjectivesByqid(qid)
            }
            return quest.objectives
        }*/

        fun insertCollectionItem(questWithObjectives: QuestWithObjectives): Int {
            CoroutineScope(Dispatchers.IO).launch {
                questDao.insert(questWithObjectives)
                questWithObjectives.objectives.forEach{obj->questDao.save(obj) }
            }
            return 1
        }
        fun insertObjectivesForQuest(quest: QuestEntity, objectives: List<ObjectiveEntity>) {
            CoroutineScope(Dispatchers.IO).launch {

                objectives.forEach{obj->
                    obj.id=quest.id
                    questDao.save(obj) }
            }


        }
        fun questById(id: Long): Flow<QuestWithObjectives?> = _quests.map { it.firstOrNull { it.quest.id == id } }

    fun objForOId(oid: Long): Flow<ObjectiveEntity> {
        return _objectives.map { it.first { it.oid == oid } }
    }

    }












/*

    suspend fun createNewQuest(title: String) {
        val Quest = Quest(title=title)
        questDao.insert(Quest)
    }
/*
    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }
*/
    fun isActive(id: String) =
        com.instance.dataxbranch.data.daos.QuestDao.isActive(id)

    fun getQuests() = com.instance.dataxbranch.data.daos.QuestDao.getAll()
    fun addQuest(quest:Quest){
        val QuestEntity = QuestEntity("","","","","",0,"","","","",0,"", listOf(""),arrayListOf(Quest.QuestObjective()))
        com.instance.dataxbranch.data.daos.QuestDao.insert(QuestEntity)
    }
}
*/