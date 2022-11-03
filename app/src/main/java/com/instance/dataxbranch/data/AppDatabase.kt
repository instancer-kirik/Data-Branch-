package com.instance.dataxbranch.data

import android.content.Context
import androidx.room.*
import com.instance.dataxbranch.data.daos.AbilityDao
import com.instance.dataxbranch.data.daos.ItemDao
//import com.instance.dataxbranch.data.daos.EntityDao
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.*
import com.instance.dataxbranch.utils.Converters


@Database(entities = [QuestEntity::class, ObjectiveEntity::class, AbilityEntity::class,
    User::class, CharacterEntity::class, ItemEntity::class],
    version = 53)
@TypeConverters(Converters::class)
 abstract class AppDatabase() : RoomDatabase(){
    //abstract fun addQuestEntity(title: String, author: String): Any
    //abstract fun getQuestDao(): QuestDao
    abstract fun questDao(): QuestDao
    abstract fun abilityDao(): AbilityDao
    abstract fun userDao(): UserDao
    abstract fun itemDao(): ItemDao
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