/*
package com.instance.dataxbranch.data.daos

import androidx.room.*
import com.instance.dataxbranch.data.entities.Repo

@Dao
interface RepoDao {
    @Insert
    fun insert(repo: Repo?)

    @Update
    fun update(vararg repos: Repo?)

    @Delete
    fun delete(vararg repos: Repo?)

    @get:Query("SELECT * FROM repo")
    val allRepos: List<Any?>?

    @Query("SELECT * FROM repo WHERE userId=:userId")
    fun findRepositoriesForUser(userId: Int): List<Repo?>?
}*/
