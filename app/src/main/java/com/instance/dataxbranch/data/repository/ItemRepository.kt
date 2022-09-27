package com.instance.dataxbranch.data.repository



import android.app.Application
import android.util.Log

import com.instance.dataxbranch.data.daos.ItemDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.ItemEntity
import com.instance.dataxbranch.data.AppDatabase

import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ItemRepository @Inject constructor(application: Application, db: AppDatabase) {
    var itemDao: ItemDao
    // lateinit var selecteditem: ItemEntity
    private var mitems: Array<ItemEntity> = arrayOf()//ArrayList<ItemEntity> = arrayOf()

    private fun initRepo(): Job =
            CoroutineScope(Dispatchers.IO).launch {
                mitems =itemDao.getItems()
            }

    fun refresh(): Job =

        CoroutineScope(Dispatchers.IO).launch {
            mitems=itemDao.getItems()
        }
    init {
        itemDao = db.itemDao()
        initRepo()
    }

    fun insertItemEntity(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val returnId = itemDao.save(item)
            Log.d("ItemREPO","id on insert is $returnId")
        }
    /*fun newObjectiveEntity(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val oe = ObjectiveEntity(id = item.item.id,)// item = item.toString())
            itemDao.save(oe)
        }*/

    fun updateitemEntity(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.update(item)

        }
    fun update(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.update(item)

        }
    /*fun update(obj: ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.update(obj)

        }*/
    fun deleteitemEntity(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.delete(item)
        }
  /*  fun update(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.update(item)
            //qwo.objectives.forEach{obj->itemDao.update(obj)}
        }*/
    fun getAllitemsAsync(): Deferred<Unit> =
        CoroutineScope(Dispatchers.IO).async {
            itemDao.getItems()
        }

    fun getitems(): Array<ItemEntity> = mitems

    fun deleteAllRows(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.deleteAllRows()
        }
   /*fun insertObjectiveEntities(vararg: ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.save(vararg)
        }*/
    /*fun loadObjectivesByqid(qid: String): List<ObjectiveEntity>{//WRONG should use id for locals
        val item = mitems.filter {   (key) -> key.qid==qid}.first()
        CoroutineScope(Dispatchers.IO).launch {

            item.objectives = itemDao.loadObjectivesByqid(qid)
        }
        return item.objectives
    }*/

    fun insertCollectionItem(itemEntity: ItemEntity): Int {
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.insert(itemEntity)
            //itemEntity.objectives.forEach{obj->itemDao.save(obj) }
        }
        return 1
    }

    /*fun insertObjectivesForitem(item: ItemEntity, objectives: List<ObjectiveEntity>) {
        CoroutineScope(Dispatchers.IO).launch {

            objectives.forEach{obj->
                obj.id=item.id
                itemDao.save(obj) }
        }


    }*/
    fun itemByIid(iid: Long): ItemEntity = itemDao.getItemByiid(iid)

    /*fun objForOId(oid: Long): Flow<ObjectiveEntity> { Let's avoid flow+liveData for now, too much headache
        return _objectives.map { it.first { it.oid == oid } }
    }*/


}









/*

    suspend fun createNewitem(title: String) {
        val item = item(title=title)
        itemDao.insert(item)
    }
/*
    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }
*/
    fun isActive(id: String) =
        com.instance.dataxbranch.data.daos.itemDao.isActive(id)

    fun getitems() = com.instance.dataxbranch.data.daos.itemDao.getAll()
    fun additem(item:item){
        val itemEntity = itemEntity("","","","","",0,"","","","",0,"", listOf(""),arrayListOf(item.itemObjective()))
        com.instance.dataxbranch.data.daos.itemDao.insert(itemEntity)
    }
}
*/