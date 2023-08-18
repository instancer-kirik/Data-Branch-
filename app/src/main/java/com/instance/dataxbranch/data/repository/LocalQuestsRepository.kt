package com.instance.dataxbranch.data.repository



import android.app.Application
import android.util.Log
import com.instance.dataxbranch.core.Constants.TAG
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
            val oe = ObjectiveEntity(qid = quest.quest.uuid,)// quest = quest.toString())
            questDao.save(oe)
        }
    fun bind(quest: QuestWithObjectives,oe:ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            oe.apply{qid = quest.quest.uuid}
            quest.objectives += oe
            //val oe = ObjectiveEntity()// quest = quest.toString())
            questDao.save(oe)
        }
    fun updateQuestEntity(quest: QuestEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.update(quest)

        }
    fun update(quest: QuestEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.save(quest)//changed 6/26/23

        }
    fun update(obj: ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.save(obj)

        }
    fun deleteQuest(quest: QuestWithObjectives) {
        mquests= mquests.filter{it.quest.uuid !=quest.quest.uuid}.toTypedArray()
        CoroutineScope(Dispatchers.IO).launch {
            questDao.delete(quest)
        }
    }
    fun update(qwo: QuestWithObjectives): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.save(qwo.quest)
            qwo.objectives.forEach{obj->questDao.save(obj)}
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
    fun insertQWOtomquests(qwo: QuestWithObjectives) {
        Log.d(TAG,"before: mquests is " + mquests )
            mquests += qwo
        Log.d(TAG,"after: mquests is " + mquests )

        }
    fun insertObjectivesForQuest(quest: QuestEntity, objectives: List<ObjectiveEntity>) {
        CoroutineScope(Dispatchers.IO).launch {

            objectives.forEach{obj->
                obj.qid=quest.uuid
                questDao.save(obj) }
        }


    }
    fun loadQuestById(id: String): QuestWithObjectives = questDao.getQuestWithObjectives(id)
    fun getQuestById(id: String): QuestWithObjectives = mquests.firstOrNull{it.quest.uuid==id}?:QuestWithObjectives(QuestEntity(title = "MISSING ON $id"), listOf())
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