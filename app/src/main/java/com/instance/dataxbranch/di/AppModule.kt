package com.instance.dataxbranch.di

import com.instance.dataxbranch.data.daos.QuestDao
import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.room.Room
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.instance.dataxbranch.DataBranchApp
import com.instance.dataxbranch.core.Constants.QUESTS
import com.instance.dataxbranch.data.daos.AbilityDao
import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.quests.QuestWithObjectives

import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.firestore.*
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.data.local.GeneralRepository
import com.instance.dataxbranch.data.local.LocalQuestsRepository

import com.instance.dataxbranch.domain.use_case.*
import com.instance.dataxbranch.utils.constants.FIRESTORE_COLLECTION
import com.instance.dataxbranch.utils.constants.NAME_PROPERTY
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.scope.DestinationScope
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


   /* @Provides
    fun provideDestinationsNavigator(app: DataBranchApp

    ) : DestinationsNavigator = DestinationScope.destinationsNavigator
*/
   /* @Provides
    fun provideUsersRef(
        db: FirebaseFirestore
    ) = db.collection(USERS)

    @Provides
    fun provideResponsesRef(
        db: FirebaseFirestore
    ) = db.collection(RESPONSES)*/


    @Provides
    @Named("questsRef")
    fun provideQuestCollRef(db: FirebaseFirestore): CollectionReference {
        return db.collection("quests")
    }
    @Provides
    @Named("usersRef")
    fun provideUserCollRef(db: FirebaseFirestore): CollectionReference {
        return db.collection("users")
    }
    @Provides
    @Named("responsesRef")
    fun provideResponsesCollRef(db: FirebaseFirestore): CollectionReference {
        return db.collection("responses")
    }
    @Provides
    @Named("chatRoomRef")
    fun provideChatRoomCollRef(db: FirebaseFirestore): CollectionReference {
        return db.collection("chatRooms")
    }
    @Provides
    fun provideSocialRepository(
        chatRoomRef: CollectionReference
    ): SocialRepository = SocialRepositoryImpl(chatRoomRef)
    @Provides
    fun provideQuestsRepository(
        questsRef: CollectionReference
    ): QuestsRepository = QuestRepositoryImpl(questsRef)
    @Provides
    fun provideUserRepository(
        usersRef: CollectionReference
    ): UsersRepository = UserRepositoryImpl(usersRef)
    @Provides
    fun provideResponsesRepository(
        responsesRef: CollectionReference
    ): ResponseRepository = ResponseRepositoryImpl(responsesRef)
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
    fun provideLocalQuestsRepository(app:Application,db: AppDatabase):
            LocalQuestsRepository {
        return LocalQuestsRepository(app,db)
    }
    @Singleton
    @Provides
    fun provideGeneralRepository(app:Application,db: AppDatabase,questsRepository: LocalQuestsRepository):
            GeneralRepository {
        return GeneralRepository(app,db,questsRepository)
    }
    /*@Singleton THIS IS FOR ROOM
    @Provides
    fun provideResponseRepository(app:Application,db: AppDatabase):
            ResponseRepository {
        return ResponseRepository(app,db)
    }*/

    @Provides
    fun provideUseCases(
        repo: QuestsRepository,
        responseRepo: ResponseRepository,
        localrepo: LocalQuestsRepository,
        socialRepo: SocialRepository,
        dao: QuestDao
    ) = UseCases(
        getQuests = GetQuests(repo),
        addQuest = AddQuest(repo),
        deleteQuest = DeleteQuest(repo),
        addQuestbyQuest = AddQuestbyQuest(repo),
        getLocalQuests= GetLocalQuests(dao),
        addQuestEntitybyQuest= AddQuestEntitybyQuest(dao),
        addQuestEntity = AddQuestEntity(dao),
        addNewQuestEntity = AddNewQuestEntity(localrepo),
        addNewObjectiveEntityToQuestEntity=  AddNewObjectiveEntityToQuestEntity(localrepo),
        addResponse = AddResponse(responseRepo),
        getResponses = GetResponses(responseRepo),
    addChatRoom= AddChatRoom(socialRepo),
    getChatRooms = GetChatRooms(socialRepo)
    )



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
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideAbilityDao(db: AppDatabase): AbilityDao {
        return db.abilityDao()
    }
    @Provides
    fun provideLocalQuests(localrepo: LocalQuestsRepository):Array<QuestWithObjectives>{
       //val quests: Array<QuestWithObjectives>
        return localrepo.getQuests()
    }
    @Provides
    fun provideMe(localrepo: GeneralRepository): UserWithAbilities {
         val me= localrepo.getMe()
        val abilities = localrepo.getAbilities()
        if(me == null){
            return UserWithAbilities(User(), abilities = abilities)
        }else{return me}


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