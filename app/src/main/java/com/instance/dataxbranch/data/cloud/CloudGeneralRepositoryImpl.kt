package com.instance.dataxbranch.data.cloud

import android.util.Log
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.CloudQuest
import com.instance.dataxbranch.quests.Quest
import com.surrealdb.connection.SurrealWebSocketConnection
import com.surrealdb.driver.SyncSurrealDriver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CloudGeneralRepositoryImpl @Inject constructor(

) : CloudGeneralRepository {
    private val tableName = "quests"
    private val namespace_name = "namespace-name"
    private val database_name = "database-name"
    private val host = "10.0.2.2"//"localhost"
    private val port = 8000
    override fun connect(): Flow<Response<SurrealWebSocketConnection>> {
        Log.d("CloudGeneralRepositoryIMPL", "quest_connect: ")
        val conn = SurrealWebSocketConnection(host, port, false)
        conn.connect(5)
        val driver = SyncSurrealDriver(conn)
        driver.signIn("root", "root")
        driver.use(namespace_name, database_name)
        val tableName = tableName
        driver.delete(tableName)
        val (thing /*me_id, uid, uname, name, imageUrl, tagline, bio, rating, rating_denominator, traits, dateAdded, energy, strength, vitality, stamina, wisdom, charisma, intellect, magic,
            dexterity, agility, speed, height, allignment, life, mana, money, level, hearts, attunement, attuned, playerAvatarjson, defaultScreen,
            constitution, isreal, initflag, history, cloud, hasKilled, hasDied, numKills, numDeaths, friends, dob, dateUpdated, completedQuests,
            completedCloudQuests, abilities, cloudAbilities, activeCloudQuests, activeQuests, dockedQuests, dockedCloudQuests, status, terms_status,
            xp, characters, selectedCharacterID, authoredQuests, dayStatuses*/) = driver.create(
            tableName,
            CloudQuest.builder().title("bQuest1").build()
        )
        val built_quest: CloudQuest = driver.create(tableName, CloudQuest.builder().title("built_quest").build())
        val updates = driver.update<Map<String, String>>(

            built_quest.qid, java.util.Map.of<String, String>("name", "Jaime")
        )
        val allUsers = driver.select(
            tableName,
            User::class.java
        )
        System.out.printf("All users = %s", allUsers)
        conn.disconnect()
        return flow {
            emit(Response.Success(conn))
        }
    }

    override fun getSignedInUser(): String {
        return "root"
    }


    override fun getQuestById(qid: String): Flow<Response<Quest>> {
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, database_name)
                val quest = driver.select(
                    qid,
                    CloudQuest::class.java
                )
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }

    override fun getQuestsFromCloud(): Flow<Response<List<Quest>>> {
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, database_name)
                val allQuests = driver.select(
                    tableName,
                    CloudQuest::class.java
                )
                val quests = allQuests.map { cloudQuest -> cloudQuest.toQuest() }
                emit(Response.Success(quests))

                //emit(Response.Success(allQuests.))
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }

    override fun addQuestToCloud(
        title: String,
        description: String,
        author: String
    ): Flow<Response<CloudQuest>> {
        Log.d("CloudGeneralRepository", "addQuestToCloud: $title, $description, $author")
        return flow{
            Log.d("CloudGeneralRepository quest", "0")
            emit(Response.Loading)
            Log.d("CloudGeneralRepository quest", "1")
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                Log.d("CloudGeneralRepository quest", "2")
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, database_name)

                Log.d("CloudGeneralRepository_quest", "driver: $driver")
                val built_quest = driver.create(
                    tableName,
                    CloudQuest.builder().title(title).description(description).author(author).build()
                )
                Log.d("CloudGeneralRepository", "built_quest: $built_quest")
                emit(Response.Success(built_quest))
            } catch (e: Exception) {
                Log.d("CloudGeneralRepository quest", "3 ${e.toString()}")
                emit(Response.Error(e.toString()))
            }
            Log.d("CloudGeneralRepository quest", "4")
        }
    }


    override fun addQuestToCloud(quest: CloudQuest): Flow<Response<Void?>> {
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, database_name)
                val built_quest: CloudQuest = driver.create(tableName, quest)
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }

    }

    override fun addQuestToCloud(quest: Quest): Flow<Response<Void?>> {
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, database_name)
                val built_quest: CloudQuest = driver.create(tableName, CloudQuest.builder().buildFromQuest(quest))
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }

    override fun deleteQuestFromCloud(qid: String): Flow<Response<Void?>> {
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, database_name)
                driver.delete(qid)

            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }
    override fun deleteQuestFromCloud(quest: CloudQuest): Flow<Response<Void?>> {
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, database_name)
                driver.delete(quest.qid)
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }
}


/*
    // var questsRef: CollectionReference
    fun getQuestById(qid: String): Flow<Response<Quest>> {TODO()}
    fun getQuestsFromCloud(): Flow<Response<List<Quest>>> {TODO()}

    //ORIGINALLY HAD SUSPEND FUN
    fun addQuestToCloud(title: String, description: String, author:String): Flow<Response<Void?>> {TODO()}
    fun addQuestToCloud(quest: Quest): Flow<Response<Void?>> {TODO()}
    fun deleteQuestFromCloud(qid: String): Flow<Response<Void?>> {TODO()}

    //  fun mintNFTQuest(cloudQuest: CloudQuest) :Boolean



}

 override fun addQuestToCloud(
        title: String,
        description: String,
        author: String
    ): Flow<Response<CloudQuest>> {
        Log.d("CloudGeneralRepository", "addQuestToCloud: $title, $description, $author")
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, database_name)
                Log.d("CloudGeneralRepository_quest", "driver: $driver")
                val built_quest = driver.create(
                    tableName,
                    CloudQuest.builder().title(title).description(description).author(author).build()
                )
                Log.d("CloudGeneralRepository", "built_quest: $built_quest")
                emit(Response.Success(built_quest))
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }
*/

