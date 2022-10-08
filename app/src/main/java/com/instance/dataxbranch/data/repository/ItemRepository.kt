package com.instance.dataxbranch.data.repository



import android.app.Application
//import android.util.Log

import com.instance.dataxbranch.data.daos.ItemDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.ItemEntity
import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.entities.User

import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates


@Singleton
class ItemRepository @Inject constructor(application: Application, db: AppDatabase) {
    var itemDao: ItemDao
    var selectedItem: ItemEntity by Delegates.observable(ItemEntity()) { property, oldValue, newValue ->

        //Log.d("ITEMREPO"," CHANGED  $property and oldval ${oldValue.stringify()} and newval ${newValue.stringify()}")
        //if (newValue.iid ==0L){ selectedItem =oldValue}// this prevents resetting to id=0 bug
    }
    var selectedID:Int = 0
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
    fun sync() {
        refresh()
        /*CoroutineScope(Dispatchers.IO).launch {//this might cause issues with data not being loaded fast enough
            mitems = itemDao.getItems()
        }*/
    }
    fun insertItemEntity(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val returnId = itemDao.save(item)
           // Log.d("ItemREPO","id on insert is $returnId")
        }
    /*fun newObjectiveEntity(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val oe = ObjectiveEntity(id = item.item.id,)// item = item.toString())
            itemDao.save(oe)
        }*/

    fun updateitemEntity(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.upsert(item)

        }
    /*fun update(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.update(item)

        }*/
    /*fun update(obj: ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.update(obj)

        }*/
    fun delete(item: ItemEntity) {
        mitems = mitems.filter{it.iid !=item.iid}.toTypedArray()
            CoroutineScope(Dispatchers.IO).launch {
                itemDao.delete(item)
            }
    }
  /*  fun update(item: ItemEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.update(item)
            //qwo.objectives.forEach{obj->itemDao.update(obj)}
        }*/
    fun getAllItemsAsync(): Deferred<Unit> =
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

private fun ItemEntity.stringify(): String {
return "ITEM ${this.name} id: ${this.iid}"
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