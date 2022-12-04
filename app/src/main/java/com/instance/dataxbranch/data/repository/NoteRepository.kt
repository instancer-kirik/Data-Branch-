package com.instance.dataxbranch.data.repository





import android.app.Application
//import android.util.Log

import com.instance.dataxbranch.data.daos.NoteDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.NoteEntity
import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.entities.User


import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates


@Singleton
class NoteRepository @Inject constructor(application: Application, db: AppDatabase) {
    var noteDao: NoteDao
    var selectedNote: NoteEntity by Delegates.observable(NoteEntity()) { property, oldValue, newValue ->

        //Log.d("ITEMREPO"," CHANGED  $property and oldval ${oldValue.stringify()} and newval ${newValue.stringify()}")
        //if (newValue.iid ==0L){ selectedNote =oldValue}// this prevents resetting to id=0 bug
    }
    var selectedID:Int = 0
    private var mnotes: Array<NoteEntity> = arrayOf()//ArrayList<NoteEntity> = arrayOf()

    private fun initRepo(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            mnotes =noteDao.getNotes()
        }

    fun refresh(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            mnotes=noteDao.getNotes()
        }
    init {
        noteDao = db.noteDao()
        initRepo()
    }
    fun sync() {
        refresh()
        /*CoroutineScope(Dispatchers.IO).launch {//this might cause issues with data not being loaded fast enough
            mnotes = noteDao.getNotes()
        }*/
    }
    fun insertNoteEntity(note: NoteEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val returnId = noteDao.save(note)
            // Log.d("NoteREPO","id on insert is $returnId")
        }
    /*fun newObjectiveEntity(note: NoteEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val oe = ObjectiveEntity(id = note.note.id,)// note = note.toString())
            noteDao.save(oe)
        }*/

    fun updatenoteEntity(note: NoteEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.save(note)

        }
    /*fun update(note: NoteEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.update(note)

        }*/
    /*fun update(obj: ObjectiveEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.update(obj)

        }*/
    fun delete(note: NoteEntity) {
        mnotes = mnotes.filter{it.uuid !=note.uuid}.toTypedArray()
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.delete(note)
        }
    }
    /*  fun update(note: NoteEntity): Job =
          CoroutineScope(Dispatchers.IO).launch {
              noteDao.update(note)
              //qwo.objectives.forEach{obj->noteDao.update(obj)}
          }*/
    fun getAllNotesAsync(): Deferred<Unit> =
        CoroutineScope(Dispatchers.IO).async {
            noteDao.getNotes()
        }

    fun getnotes(): Array<NoteEntity> = mnotes

    fun deleteAllRows(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            TODO()
            //noteDao.deleteAllRows()
        }
    /*fun insertObjectiveEntities(vararg: ObjectiveEntity): Job =
         CoroutineScope(Dispatchers.IO).launch {
             noteDao.save(vararg)
         }*/
    /*fun loadObjectivesByqid(qid: String): List<ObjectiveEntity>{//WRONG should use id for locals
        val note = mnotes.filter {   (key) -> key.qid==qid}.first()
        CoroutineScope(Dispatchers.IO).launch {

            note.objectives = noteDao.loadObjectivesByqid(qid)
        }
        return note.objectives
    }*/

    fun insertCollectionNote(noteEntity: NoteEntity): Int {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insert(noteEntity)
            //noteEntity.objectives.forEach{obj->noteDao.save(obj) }
        }
        return 1
    }

    /*fun insertObjectivesFornote(note: NoteEntity, objectives: List<ObjectiveEntity>) {
        CoroutineScope(Dispatchers.IO).launch {

            objectives.forEach{obj->
                obj.id=note.id
                noteDao.save(obj) }
        }


    }*/
    fun loadNoteById(id: String): NoteEntity = noteDao.getNotesByUuid(id)
    fun getNoteById(id: String):NoteEntity = mnotes.first{it.uuid==id}
    /*fun objForOId(oid: Long): Flow<ObjectiveEntity> { Let's avoid flow+liveData for now, too much headache
        return _objectives.map { it.first { it.oid == oid } }
    }*/


}




/*

    suspend fun createNewnote(title: String) {
        val note = note(title=title)
        noteDao.insert(note)
    }
/*
    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }
*/
    fun isActive(id: String) =
        com.instance.dataxbranch.data.daos.noteDao.isActive(id)

    fun getnotes() = com.instance.dataxbranch.data.daos.noteDao.getAll()
    fun addnote(note:note){
        val noteEntity = noteEntity("","","","","",0,"","","","",0,"", listOf(""),arrayListOf(note.noteObjective()))
        com.instance.dataxbranch.data.daos.noteDao.insert(noteEntity)
    }
}
*/