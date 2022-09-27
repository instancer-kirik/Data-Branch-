package com.instance.dataxbranch.data.daos

import androidx.room.*

import com.instance.dataxbranch.data.entities.ItemEntity

@Dao
abstract class ItemDao() {//:EntityDao<ItemEntity>
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    abstract fun insert(vararg item: ItemEntity?):List<Long>

    @Update
    abstract fun update(vararg item: ItemEntity?)

    @Delete
    abstract fun delete(vararg item: ItemEntity?)

    @Transaction
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    abstract fun save(item: ItemEntity):Long
    @Query("DELETE FROM quests")
    abstract fun deleteAllRows()
    @Insert
    abstract fun insertAll(items: Array<ItemEntity>)

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //abstract fun save(items: Iterable<ItemEntity>)
    /*@Query("SELECT * FROM items WHERE uid=:uid") not many to many here, no need
    abstract fun getAbilites(uid:Long): List<ItemEntity>*/
    @Query("SELECT * FROM items")
    abstract fun getItems(): Array<ItemEntity>
    @Query("SELECT * FROM items WHERE iid = :iid")
    abstract fun getItemByiid(iid:Long): ItemEntity



/*open fun getUserWithAbilities(): UserWithAbilities {
    val User = getQuestEntity(id)
    val objectives: List<ObjectiveEntity> = getAbilites(id)
    val a = AllAbilitiesOnHand()
    return a*/
}


