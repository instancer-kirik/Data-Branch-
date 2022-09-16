package com.instance.dataxbranch.data.daos

import androidx.room.*

import com.instance.dataxbranch.data.entities.AbilityEntity

@Dao
abstract class AbilityDao() {//:EntityDao<AbilityEntity>
    @Insert
    abstract fun insert(vararg ability: AbilityEntity?)

    @Update
    abstract fun update(vararg ability: AbilityEntity?)

    @Delete
    abstract fun delete(vararg ability: AbilityEntity?)

    @Transaction
    @Insert
    abstract fun save(ability: AbilityEntity)

    @Insert
    abstract fun insertAll(abilities: List<AbilityEntity>)

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //abstract fun save(items: Iterable<AbilityEntity>)
    /*@Query("SELECT * FROM abilities WHERE uid=:uid") not many to many here, no need
    abstract fun getAbilites(uid:Long): List<AbilityEntity>*/
    @Query("SELECT * FROM abilities")
    abstract fun getAbilites(): List<AbilityEntity>
    @Query("SELECT * FROM abilities WHERE aid = :aid")
    abstract fun getAbilitesByaid(aid:Long): AbilityEntity



/*open fun getUserWithAbilities(): UserWithAbilities {
    val User = getQuestEntity(id)
    val objectives: List<ObjectiveEntity> = getAbilites(id)
    val a = AllAbilitiesOnHand()
    return a*/
}


