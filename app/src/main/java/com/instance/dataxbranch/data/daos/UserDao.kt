package com.instance.dataxbranch.data.daos

import android.util.Log
import androidx.room.*
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.local.UserWithAbilities
import kotlinx.coroutines.flow.Flow


@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg user: User?)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun prime(user: User)
    @Update
    abstract fun update(vararg user: User?)

    @Update
    fun update(user: User?){
        Log.d(TAG,"updating with $user")}
    @Delete
    abstract fun delete(vararg user: User?)

    @Query("SELECT * FROM my_resources_attributes_stats")
    abstract fun observeMe(): Flow<User?>

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

    @Transaction
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(user: User)


    @Transaction
    @Query("SELECT * FROM abilities")
    abstract fun getAbilities(): List<AbilityEntity>


    open fun getMeAbilities(): UserWithAbilities? {
        val me = getMe()
        val abilities: List<AbilityEntity> = getAbilities()

        return me?.let { UserWithAbilities(it,abilities) }
    }
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
