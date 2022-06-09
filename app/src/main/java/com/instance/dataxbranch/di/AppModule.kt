package com.instance.dataxbranch.di

import com.instance.dataxbranch.data.daos.QuestDao
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.instance.dataxbranch.core.Constants.QUESTS
import com.instance.dataxbranch.data.QuestContainerLocal

import com.instance.dataxbranch.data.local.AppDatabase
import com.instance.dataxbranch.data.repository.LocalQuestsRepository
import com.instance.dataxbranch.data.repository.QuestRepositoryImpl
import com.instance.dataxbranch.data.repository.QuestsRepository
import com.instance.dataxbranch.domain.use_case.*
import com.instance.dataxbranch.utils.constants.FIRESTORE_COLLECTION
import com.instance.dataxbranch.utils.constants.NAME_PROPERTY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideQueryQuestsByName() = FirebaseFirestore.getInstance()
        .collection(FIRESTORE_COLLECTION)
        .orderBy(NAME_PROPERTY, Query.Direction.ASCENDING)

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideApplication(app: Application): Context = app

    @Provides
    fun provideQuestsRef(
        db: FirebaseFirestore
    ) = db.collection(QUESTS)


    @Provides
    fun provideQuestsRepository(
        questsRef: CollectionReference
    ): QuestsRepository = QuestRepositoryImpl(questsRef)
    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, "debug_extra.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun provideLocalQuestsRepository(app:Application,db:AppDatabase):
            LocalQuestsRepository {
        return LocalQuestsRepository(app,db)
    }


    @Provides
    fun provideUseCases(
        repo: QuestsRepository,
        localrepo: LocalQuestsRepository,
        dao: QuestDao
    ) = UseCases(
        getQuests = GetQuests(repo),
        addQuest = AddQuest(repo),
        deleteQuest = DeleteQuest(repo),
        addQuestbyQuest = AddQuestbyQuest(repo),
        getLocalQuests= GetLocalQuests(dao),
        addQuestEntitybyQuest= AddQuestEntitybyQuest(dao),
        addQuestEntity = AddQuestEntity(dao),
        addNewQuestEntity = AddNewQuestEntity(localrepo)
    )


    @Provides
    @Named("questsRef")
    fun provideQuestCollRef(db: FirebaseFirestore): CollectionReference {
        return db.collection("quests")
    }
   /* @Singleton
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return db
    }
*/

    @Provides

    fun provideQuestDao(db: AppDatabase): QuestDao {
        return db.questDao()
    }
    @Provides
    fun provideLocalQuests(localrepo: LocalQuestsRepository):Array<QuestContainerLocal>{
       //val quests: Array<QuestContainerLocal>
        return localrepo.getQuests()
    }







    //fun provideQuestDAO(appDatabase: AppDatabase): QuestDAO { return appDatabase.entityDao() }

}

/*
@Provides
    fun provideLocalQuests(app:Application):Array<QuestEntity>{
        return provideQuestDao(provideDb(app = app)).getAll()
    }
    @Provides
    @Named("questsRef")
    fun provideCollectionReference(db: FirebaseFirestore): CollectionReference? {
        return db.collection("quests")
    }
}
*/