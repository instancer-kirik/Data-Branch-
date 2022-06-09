package com.instance.dataxbranch.data.repository



import android.app.Application
import com.instance.dataxbranch.data.QuestContainerLocal


import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.local.AppDatabase
import kotlinx.coroutines.*
import javax.inject.Singleton
@Singleton
class LocalQuestsRepository(application: Application,db:AppDatabase) {
    var questDao: QuestDao
    private lateinit var mquests: Array<QuestContainerLocal>

    private fun initRepo(): Job =

            CoroutineScope(Dispatchers.IO).launch {
                mquests =questDao.getItAll()
            }

    fun refresh(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            mquests=questDao.getItAll()
        }
    init {
        questDao = db.questDao()
        initRepo()
    }

    fun insertQuestEntity(quest: QuestEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.insert(quest)
        }

    fun updateQuestEntity(quest: QuestEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.update(quest)

        }

    fun deleteQuestEntity(quest: QuestEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.delete(quest)
        }

    fun getAllQuestsAsync(): Deferred<Unit> =
        CoroutineScope(Dispatchers.IO).async {
            questDao.getAll()
        }

    fun getQuests(): Array<QuestContainerLocal> = mquests

    fun deleteAllRows(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.deleteAllRows()
        }
   fun insertObjectiveEntities(vararg: ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            questDao.save(vararg)
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