package com.instance.dataxbranch.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.google.firebase.firestore.auth.User


@Dao
interface UserDao {
    @Insert
    fun insert(vararg user: User?)

    @Update
    fun update(vararg user: User?)

    @Delete
    fun delete(vararg user: User?)
}