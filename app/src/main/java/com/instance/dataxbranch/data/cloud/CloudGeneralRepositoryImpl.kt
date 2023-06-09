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
    private val namespace_name = "namespace-name"
    private val quests_table = "quests"
    private val quests_database = "quests"
    private val items_table = "items"
    private val items_database = "items"
    private val users_table = "users"
    private val users_database = "users"
    private val spells_table = "spells"
    private val spells_database = "spells"
    private val mobs_table = "mobs"
    private val mobs_database = "mobs"
    private val wiki_table = "wiki"
    private val wiki_database = "wiki"
    //private val npcs_database = "npcs"
    // private val npcs_table = "npcs"

    private val host = "10.0.2.2"//"localhost"
    private val port = 8000
    override fun connect(): Flow<Response<SurrealWebSocketConnection>> {
        Log.d("CloudGeneralRepositoryIMPL", "quest_connect: ")
        val conn = SurrealWebSocketConnection(host, port, false)
        conn.connect(5)
        val driver = SyncSurrealDriver(conn)
        driver.signIn("root", "root")
        driver.use(namespace_name, quests_database)

        driver.delete(quests_table)
        val (thing /*me_id, uid, uname, name, imageUrl, tagline, bio, rating, rating_denominator, traits, dateAdded, energy, strength, vitality, stamina, wisdom, charisma, intellect, magic,
            dexterity, agility, speed, height, allignment, life, mana, money, level, hearts, attunement, attuned, playerAvatarjson, defaultScreen,
            constitution, isreal, initflag, history, cloud, hasKilled, hasDied, numKills, numDeaths, friends, dob, dateUpdated, completedQuests,
            completedCloudQuests, abilities, cloudAbilities, activeCloudQuests, activeQuests, dockedQuests, dockedCloudQuests, status, terms_status,
            xp, characters, selectedCharacterID, authoredQuests, dayStatuses*/) = driver.create(
            quests_table,
            CloudQuest.builder().title("bQuest1").build()
        )
        val built_quest: CloudQuest = driver.create(quests_table, CloudQuest.builder().title("built_quest").build())
        val updates = driver.update<Map<String, String>>(

            built_quest.qid, java.util.Map.of<String, String>("name", "Jaime")
        )
        val allUsers = driver.select(
            quests_table,
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
        TODO()
    }


    override fun getQuestById(qid: String): Flow<Response<Quest>> {
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, quests_database)
                val quest = driver.select(
                    qid,
                    CloudQuest::class.java
                )
                Log.d("CloudGeneralRepository", "getQuestById:len ${quest.size} ${quest} ")
                emit(Response.Success(quest.first().toQuest()))
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
                driver.use(namespace_name, quests_database)
                val allQuests = driver.select(
                    quests_table,
                    CloudQuest::class.java
                )
                val quests = allQuests.map { cloudQuest -> cloudQuest.toQuest() }
                emit(Response.Success(quests))
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
        //Log.d("CloudGeneralRepository", "addQuestToCloud: $title, $description, $author")
        return flow{
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, quests_database)
                val built_quest = driver.create(
                    quests_table,
                    CloudQuest.builder().title(title).description(description).author(author).build()
                )
                emit(Response.Success(built_quest))
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
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
                driver.use(namespace_name, quests_database)
                val built_quest: CloudQuest = driver.create(quests_table, quest)
                emit(Response.Success(null))
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
                driver.use(namespace_name, quests_database)
                val built_quest: CloudQuest = driver.create(quests_table, CloudQuest.builder().buildFromQuest(quest))
                emit(Response.Success(null))
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
                driver.use(namespace_name, quests_database)
                driver.delete(qid)
                emit(Response.Success(null))
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
                driver.use(namespace_name, quests_database)
                driver.delete(quest.qid)
                emit(Response.Success(null))
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }

    override fun updateQuest(quest: Quest): Flow<Response<MutableList<CloudQuest>>>{
        return flow {
            emit(Response.Loading)
            try {
                val conn = SurrealWebSocketConnection(host, port, false)
                conn.connect(5)
                val driver = SyncSurrealDriver(conn)
                driver.signIn("root", "root")
                driver.use(namespace_name, quests_database)
                val result = driver.update(quests_table, CloudQuest.builder().buildFromQuest(quest)) //(Mutable)List<CloudQuest!>! ???????
                emit(Response.Success(result))
                //driver.update( CloudQuest.builder().buildFromQuest(quest))
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
                driver.use(namespace_name, quests_database
               )
                Log.d("CloudGeneralRepository_quest", "driver: $driver")
                val built_quest = driver.create(
                    quests_table,
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

