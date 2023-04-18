package com.instance.dataxbranch.data.daos

//import android.util.Log
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.entities.*

import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.quests.QuestWithObjectives
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg user: User)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun prime(user: User)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun prime(characterEntity: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun prime(vararg characterEntity: CharacterEntity)
    @Update
    abstract fun update(vararg user: User)

    @Update
    abstract fun update(user: User)
        //Log.d(TAG,"updating with $user")}
    @Delete
    abstract fun delete(vararg user: User)

    @Query("SELECT * FROM my_resources_attributes_stats")
    abstract fun observeMe(): Flow<User>

    @Transaction
    @Query("SELECT * FROM my_resources_attributes_stats")
    abstract fun getMe(): User

    @Query("DELETE FROM my_resources_attributes_stats")
    abstract fun nukeTable()

    @Transaction
    @Query("SELECT * FROM my_resources_attributes_stats")
     fun setMe(user: User){
        nukeTable()
        insert(user)
     }



    @Query("SELECT * FROM characters ORDER BY uuid LIMIT 1")
    abstract fun isPrimed(): CharacterEntity



    @Upsert
    fun save(user: User){
        Log.d(TAG,"selected character id is ${user.selectedCharacterID}")}


    @Upsert
    fun save(character: CharacterEntity){Log.d(TAG,"BISH, IN SAVE")}

    @Transaction
    @Query("SELECT * FROM abilities")
    abstract fun getAbilities(): List<AbilityEntity>


    @Transaction
    @Query("SELECT * FROM characters")
    abstract fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE uuid =:uuid")
    abstract fun getCharacterEntity(uuid: UUID): CharacterEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertCharacter(vararg character: CharacterEntity)

    @Delete
    abstract fun delete(vararg character: CharacterEntity)

    @Query("DELETE FROM characters")
    abstract fun wipeCharacters()

    @Update
    fun update(character: CharacterEntity){
        //Log.d(TAG,"updating with $char")
    }

    @Query("SELECT * FROM abilities WHERE aid =:id")
    abstract fun getAbilityEntityList(id: Long): List<AbilityEntity>

    /*
    Cannot figure out how to read this field from a cursor. - equipment in com.instance.dataxbranch.data.local.CharacterWithStuff
    @Transaction
    @Query("SELECT * FROM characters")
    abstract fun loadAll(): List<CharacterWithStuff>*/

    open fun getMeAbilities(): UserWithAbilities? {
        val me = getMe()
        val abilities: List<AbilityEntity> = getAbilities()

        return me?.let { UserWithAbilities(it,abilities) }
    }

   /* @MapInfo(keyColumn = "date", valueColumn = "option")
    @Query("SELECT c.name AS name, sum(p.value) AS sum FROM payments p, paymentCategories c WHERE p.categoryId = c.id GROUP BY c.name")
    abstract fun getCategoryStats(): Map<String, Float>*/






}
/*
@Dao
interface UserAbilityDao {
    @Query(
        "SELECT my_resources_attributes_stats.name AS userName, abilities.name AS abilityName " +
                "FROM my_resources_attributes_stats, abilities " +
                "WHERE user.id = abilit.user_id"
    )
    fun loadUserAndAbilities(): LiveData<List<UserAbility>>

    // You can also define this class in a separate file.
    data class UserAbility(val userName: String?, val abilityName: String?)
}*/
