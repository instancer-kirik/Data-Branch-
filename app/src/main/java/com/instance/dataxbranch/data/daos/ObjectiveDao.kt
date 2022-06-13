package com.instance.dataxbranch.data.daos

import androidx.room.Dao
import androidx.room.Insert
import com.instance.dataxbranch.data.entities.ObjectiveEntity

@Dao
abstract class ObjectiveDao {

        @Insert
        abstract fun insertAll(objectives: List<ObjectiveEntity>)
    }