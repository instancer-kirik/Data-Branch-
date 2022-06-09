package com.instance.dataxbranch.data.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Transaction
import androidx.room.Update
import com.instance.dataxbranch.data.entities.QuestEntity


interface EntityDao<in E : QuestEntity> {
    @Insert
    abstract suspend fun insert(entity: E): String

    @Insert
    abstract suspend fun insertAll(vararg entity: E)

    @Insert
    abstract suspend fun insertAll(entities: List<E>)

    @Update
    abstract suspend fun update(entity: E)

    @Delete
    abstract suspend fun deleteEntity(entity: E): Int

    @Transaction
    open suspend fun withTransaction(tx: suspend () -> Unit) = tx()

    suspend fun insertOrUpdate(entity: E): String {
        return if (entity.qid == "-1") {
            insert(entity)
        } else {
            update(entity)
            entity.qid!!
        }
    }

    @Transaction
    open suspend fun insertOrUpdate(entities: List<E>) {
        entities.forEach {
            insertOrUpdate(it)
        }
    }

}
