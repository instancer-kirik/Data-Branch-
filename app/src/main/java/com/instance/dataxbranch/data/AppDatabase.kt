package com.instance.dataxbranch.data

import android.content.Context
import androidx.room.*
import com.instance.dataxbranch.data.daos.AbilityDao
//import com.instance.dataxbranch.data.daos.EntityDao
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.utils.Converters


@Database(entities = arrayOf(QuestEntity::class, ObjectiveEntity::class, AbilityEntity::class, User::class), version = 24)
@TypeConverters(Converters::class)
 abstract class AppDatabase() : RoomDatabase(){
    //abstract fun addQuestEntity(title: String, author: String): Any
    //abstract fun getQuestDao(): QuestDao
    abstract fun questDao(): QuestDao
    abstract fun abilityDao(): AbilityDao
    abstract fun userDao(): UserDao
    //\abstract fun entityDao(): EntityDao

    //fun addQuestEntity(quest:Quest): Any
    //abstract fun addNewQuestEntity(title: String, author: String): Any
    companion object {


            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "db_room_database")
                        //.createFromAsset("database/dbroom.db")
                        .build()
                    INSTANCE = instance

                    instance
                }
            }
            /*fun getQuests(questDao: QuestDao): Flow<List<QuestEntity>> {
            return questDao.getAll()
            }
            suspend fun addQuestEntity(quest: Quest, questDao: QuestDao){
                questDao.insert(quest.toRoom())
            }
            suspend fun addNewQuestEntity(title: String,author:String, questDao: QuestDao){
            questDao.insert(QuestEntity(title =title, author = author))
        }*/


    }



}