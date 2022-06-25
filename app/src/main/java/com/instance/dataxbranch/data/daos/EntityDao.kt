/*
package com.instance.dataxbranch.data.daos

import androidx.room.*
import com.instance.dataxbranch.data.entities.QuestEntity

@Dao
interface EntityDao {
    @Insert
    abstract suspend fun insert(entity: Entity): String

    @Insert
    abstract suspend fun insertAll(vararg entity: Entity)

    @Insert
    abstract suspend fun insertAll(entities: List<Entity>)

    @Update
    abstract suspend fun update(entity: Entity)

    @Delete
    abstract suspend fun deleteEntity(entity: Entity): Int

    @Transaction
    open suspend fun withTransaction(tx: suspend () -> Unit) = tx()

    suspend fun insertOrUpdate(entity: Entity) {
        try{
            update(entity)

        }catch(e: Exception){insert(entity)}
        */
/*return if (entity.) {
            insert(entity)
        } else {
            update(entity)
            entity.id!!
        }*//*

    }

    @Transaction
    open suspend fun insertOrUpdate(entities: List<Entity>) {
        entities.forEach {
            insertOrUpdate(it)
        }
    }

}
*/
