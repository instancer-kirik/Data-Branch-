package com.instance.dataxbranch.data.local



import android.app.Application
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.AppDatabase
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalQuestsRepository @Inject constructor(application: Application, db: AppDatabase) {
    var questDao: QuestDao
     // lateinit var selectedQuest: QuestWithObjectives
    private var mquests: Array<QuestWithObjectives> = arrayOf()

    private fun initRepo(): Job =
            CoroutineScope(Dispatchers.IO).launch {
                mquests =questDao.getItAll()
            }

    fun refresh(): Job =

        CoroutineScope(Dispatchers.IO).launch {
            mquests=questDao.loadAll()
        }
    init {
        questDao = db.questDao()
        initRepo()
    }

    fun insertQuestEntity(quest: QuestEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.save(quest)
        }
    fun newObjectiveEntity(quest: QuestWithObjectives): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val oe = ObjectiveEntity(id = quest.quest.id,)// quest = quest.toString())
            questDao.save(oe)
        }

    fun updateQuestEntity(quest: QuestEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.update(quest)

        }
    fun update(quest: QuestEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.update(quest)

        }
    fun update(obj: ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.update(obj)

        }
    fun deleteQuestEntity(quest: QuestWithObjectives): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.delete(quest)
        }
    fun update(qwo: QuestWithObjectives): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.update(qwo.quest)
            qwo.objectives.forEach{obj->questDao.update(obj)}
        }
    fun getAllQuestsAsync(): Deferred<Unit> =
        CoroutineScope(Dispatchers.IO).async {
            questDao.loadAll()
        }

    fun getQuests(): Array<QuestWithObjectives> = mquests

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
    fun questById(id: Long): QuestWithObjectives = questDao.getQuestWithObjectives(id)

    /*fun objForOId(oid: Long): Flow<ObjectiveEntity> { Let's avoid flow+liveData for now, too much headache
        return _objectives.map { it.first { it.oid == oid } }
    }*/


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