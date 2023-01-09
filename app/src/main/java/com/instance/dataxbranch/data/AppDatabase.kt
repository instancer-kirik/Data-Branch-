package com.instance.dataxbranch.data

import android.content.Context
import androidx.room.*
import com.instance.dataxbranch.data.daos.*
//import com.instance.dataxbranch.data.daos.EntityDao
import com.instance.dataxbranch.data.entities.*
import com.instance.dataxbranch.utils.Converters

//also has relevant enums below
@Database(entities = [QuestEntity::class, ObjectiveEntity::class, AbilityEntity::class,
    User::class, CharacterEntity::class, ItemEntity::class,NoteEntity::class],
    version = 67, exportSchema = false)
@TypeConverters(Converters::class)
 abstract class AppDatabase() : RoomDatabase(){
    //abstract fun addQuestEntity(title: String, author: String): Any
    //abstract fun getQuestDao(): QuestDao
    abstract fun questDao(): QuestDao
    abstract fun abilityDao(): AbilityDao
    abstract fun userDao(): UserDao
    abstract fun itemDao(): ItemDao
    abstract fun noteDao(): NoteDao
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

/*@Database(entities = [TodoItem::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDatabaseDao
    companion object {
         private var INSTANCE: TodoDatabase? = null
         fun getInstance(context: Context): TodoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo_list_database"
                    ).fallbackToDestructiveMigration()
                     .build()
                 INSTANCE = instance
               }
             return instance
           }
       }
   }
}*/
    }



}
enum class QuestType{
    DAILY, WEEKLY, MONTHLY, YEARLY, ONCE, REPEATABLE
}
enum class QuestStatus{
    ACTIVE, COMPLETED, FAILED, CANCELLED
}
enum class EventType{//doesn't have OBJECTIVE, ABILITY, ITEM, EVENT, CHARACTER, USER... for use with calendar

    //could have objective
    QUEST,  HABIT, NOTE, DEFAULT, NONE;
    companion object{

        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
        fun fromStringOrDefault(string: String): EventType {
            return values().firstOrNull { it.toString() == string } ?: DEFAULT
        }

    }
}
/*enum class CalendarDataType{//made by github copilot
    QUEST, OBJECTIVE, ABILITY, ITEM, NOTE, EVENT, TASK, REMINDER, APPOINTMENT, MEETING, DEADLINE, BIRTHDAY, ANNIVERSARY, HOLIDAY, VACATION, OTHER, DEFAULT
}*/
enum class DayStatus{
    FANTASTIC, GOOD, OKAY, BAD, TIRED, NONE,DEFAULT;
    companion object{
        fun getDayStatusFromInt(i: Int): DayStatus{
            return when(i){
                0 -> DEFAULT
                1 -> GOOD
                2 -> OKAY
                3 -> BAD
                4 -> TIRED
                5 -> FANTASTIC
                else -> NONE
            }
        }
        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
        fun fromStringOrDefault(string: String): DayStatus {
            return values().firstOrNull { it.toString() == string } ?: DEFAULT
        }

    }

}

//fantastic, excellent, mediocre, good, bad, awful, abysmal terrible, default