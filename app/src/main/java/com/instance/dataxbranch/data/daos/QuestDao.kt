package com.instance.dataxbranch.data.daos

import androidx.room.*

import com.instance.dataxbranch.data.QuestContainerLocal
import com.instance.dataxbranch.data.entities.ObjectiveEntity

import com.instance.dataxbranch.data.entities.QuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Query("SELECT * FROM quests WHERE active != 0")
    //alter this on active select in lazycolumnwithselection
    fun observeActive(): Flow<QuestEntity?>

    @Query("SELECT * FROM quests WHERE qid = :qid")
    fun observeQuest(qid: String): Flow<QuestEntity?>

    @Query("SELECT * FROM quests WHERE title = :title")
    fun getByTitle(title: String): Flow<List<QuestEntity>>


    @Query("SELECT * FROM quests WHERE active != 0")
    //abstract//alter this on active select in lazycolumnwithselection
    fun getActive(): QuestEntity?

    @Query("SELECT * FROM quests WHERE id = :id AND active != 0")
    fun isActive(id: String): Boolean

    @Query("SELECT qid FROM quests WHERE active != 0")
    suspend fun getIdForActive(): String?


    @Query("DELETE FROM quests WHERE qid = :qid")
    suspend fun removeQuestByQid(qid: String)


    @Query("DELETE FROM quests")
    suspend fun deleteAll()

    @Query("SELECT * FROM quests ORDER BY title ASC")
    fun getAll(): Array<QuestEntity>
    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    //
    //


    @Insert
    suspend fun insert(quest: QuestEntity)

    @Update
    fun update(vararg quest:QuestEntity)

    @Delete
    fun delete(questEntity: QuestEntity)

    @Query("DELETE FROM quests")
    fun deleteAllRows()

    @Insert
    suspend fun save(quest:QuestEntity)
    @Insert
    suspend fun save(objective: ObjectiveEntity)
    @Insert
    suspend fun save(vararg objective: ObjectiveEntity)

    @Transaction
    @Query("SELECT * FROM quests")
    suspend fun getItAll(): Array<QuestContainerLocal>

    @Transaction
    @Query("SELECT * FROM quests WHERE id = :id")
    suspend fun getByQuestId(id: String): QuestContainerLocal
}

/*
  @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(QuestEntity(title:String, author: String))
@Query("SELECT * FROM quests ORDER BY title ASC")
    fun getAllQuests(): StateFlow<List<QuestEntity>>

val test: Int

    fun foo() : String

    fun hello() {
        println("Hello there, pal!")
    }
companion object{

    fun getAll(): List<QuestEntity> {
    return this.getAll()
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(questEntity: QuestEntity) {}
    fun isActive(id: String): Boolean {
TODO("maybe needs a query idk")
    }


    val id: String = "-1"
}
}*/

