package com.instance.dataxbranch.data.daos

import androidx.room.*
import com.instance.dataxbranch.data.QuestWithObjectives
import com.instance.dataxbranch.data.entities.ObjectiveEntity

import com.instance.dataxbranch.data.entities.QuestEntity


@Dao
abstract class QuestDao {
    /*@Query("SELECT * FROM quests WHERE active != 0")
    //alter this on active select in lazycolumnwithselection
    fun observeActive(): FlowQuestWithObjectives>

    @Query("SELECT * FROM quests WHERE qid = :qid")
    fun observeQuest(qid: String): FlowQuestWithObjectives>
    @Transaction
    @Query("SELECT * FROM quests WHERE title = :title")
    fun getByTitle(title: String): Flow<ListQuestWithObjectives>

    @Transaction
    @Query("SELECT * FROM quests WHERE active != 0")
    //abstract//alter this on active select in lazycolumnwithselection
    fun getActive():QuestWithObjectives

    @Query("SELECT * FROM quests WHERE id = :id AND active != 0")
    fun isActive(id: String): Boolean

    @Query("SELECT qid FROM quests WHERE active != 0")
    suspend fun getIdForActive(): String?


    @Query("DELETE FROM quests WHERE qid = :qid")
    suspend fun removeQuestByQid(qid: String)




    @Query("SELECT * FROM quests ORDER BY title ASC")
    fun getAll(): Array<QuestEntity>
    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    //
    //


    @Insert
    suspend fun insert(quest: QuestEntity)
    @Insert
    suspend fun insert(quest:QuestWithObjectives


    @Insert
    suspend fun insert(objective: ObjectiveEntity)


    @Insert
    suspend fun insert(vararg : Entity)

    @Delete
    fun delete(questEntity: QuestEntity)
    @Delete
    fun delete(objective: ObjectiveEntity)

    fun delete(qwo:QuestWithObjectives{
        qwo.objectives.forEach {delete(it)}
        delete(qwo.quest)
    }





    @Insert
    suspend fun save(vararg objective: ObjectiveEntity)

    @Transaction
    @Query("SELECT * FROM quests WHERE qid = :qid")
    suspend fun getByQuestId(qid: String):QuestWithObjectives

    @Query("SELECT * FROM objectives WHERE qid = :qid")
    fun loadObjectivesByqid(qid: String): List<ObjectiveEntity>

    @Query("SELECT * FROM objectives WHERE id = :id")
    fun loadObjectives(id: Int): List<ObjectiveEntity>

    @Transaction
    @Query("SELECT * FROM quests WHERE id = :id")
    fun geQuestWithObjectivesid: Int):QuestWithObjectives
*/  @Transaction
    @Query("SELECT * FROM quests")
    abstract fun getItAll(): Array<QuestWithObjectives>//probably includes objectives

    @Transaction
    @Insert
    abstract fun save(objective: ObjectiveEntity)

    @Insert
    abstract fun save(quest:QuestEntity):Long
    @Insert
    abstract fun insertQuestEntity(quest: QuestEntity?):Long

    @Insert
    abstract fun insertObjectiveEntityList(objectives: List<ObjectiveEntity>?)

    @Query("SELECT * FROM quests WHERE id =:id")
    abstract fun getQuestEntity(id: Long): QuestEntity

    @Query("SELECT * FROM objectives WHERE id =:id")
    abstract fun getObjectiveEntityList(id: Long): List<ObjectiveEntity>

    /*open fun insertQuestObjectives(quest: QuestEntity) {
        val objectives: List<ObjectiveEntity> = quest.getObjectiveEntityList()
        for (i in objectives.indices) {
            objectives[i].id=(quest.id)
        }
        insertObjectiveEntityList(objectives)
        insertQuestEntity(quest)
    }*/
    @Query("DELETE FROM quests")
    abstract fun deleteAll()
    @Query("DELETE FROM quests")
    abstract fun deleteAllRows()
    open fun getQuestWithObjectives(id: Long):QuestWithObjectives {
        val quest = getQuestEntity(id)
        val objectives: List<ObjectiveEntity> = getObjectiveEntityList(id)
        val qwo =QuestWithObjectives(quest,objectives)
        return qwo
    }



        fun insert(QuestWithObjectives: QuestWithObjectives) {
            val id = insertQuestEntity(QuestWithObjectives.quest)
            QuestWithObjectives.objectives.forEach { i -> i.id= id }
            insertAll(QuestWithObjectives.objectives)
        }

        fun delete(QuestWithObjectives: QuestWithObjectives) {
            delete(QuestWithObjectives.quest, QuestWithObjectives.objectives)
        }

        @Insert
        abstract fun insertAll(objectives: List<ObjectiveEntity?>?)
        //
        //abstract fun insertQuestEntity(recipe: QuestEntity?): Long //return type is the key here.
        @Transaction
        @Delete
        abstract fun delete(quest: QuestEntity?, objectives: List<ObjectiveEntity?>?)
        @Transaction
        @Query("SELECT * FROM quests")
        abstract fun loadAll(): Array<QuestWithObjectives>


    @Update
    abstract fun update(vararg quest:QuestEntity)
    @Update
    abstract fun update(objective: ObjectiveEntity)



/*@Query("SELECT id, title FROM quests")
    fun loadQuestAndObjectives(): ListQuestWithObjectives*/

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

