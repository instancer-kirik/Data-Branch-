package com.instance.dataxbranch.di

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.room.Room
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.local.AppDatabase
import com.ramcosta.composedestinations.annotation.Destination
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
@Module
@InstallIn(Singleton::class)
object RoomModule {
    @Provides
    fun providesQuestDao(db: AppDatabase): QuestDao {
        return db.questDao()
    }

    /*fun RoomModule(mApplication: Application?) {
        db=
            Room.databaseBuilder(mApplication!!, AppDatabase::class.java, "demo-db").build()
    }*/
    //private val db: AppDatabase

    /*@Singleton
    @Provides
    fun productRepository(productDao: ProductDao?): QuestsRepository {
        return ProductDataSource(productDao)
    }
*/
      */