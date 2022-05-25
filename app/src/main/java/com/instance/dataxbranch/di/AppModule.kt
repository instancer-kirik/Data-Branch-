package com.instance.dataxbranch.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.instance.dataxbranch.core.Constants.QUESTS
import com.instance.dataxbranch.data.repository.QuestRepositoryImpl
import com.instance.dataxbranch.data.repository.QuestsRepository
import com.instance.dataxbranch.domain.use_case.AddQuest
import com.instance.dataxbranch.domain.use_case.DeleteQuest
import com.instance.dataxbranch.domain.use_case.GetQuests
import com.instance.dataxbranch.domain.use_case.UseCases
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
    fun provideQuestsRef(
        db: FirebaseFirestore
    ) = db.collection(QUESTS)


    @Provides
    fun provideQuestsRepository(
        questsRef: CollectionReference
    ): QuestsRepository = QuestRepositoryImpl(questsRef)

    @Provides
    fun provideUseCases(
        repo: QuestsRepository
    ) = UseCases(
        getQuests = GetQuests(repo),
        addQuest = AddQuest(repo),
        deleteQuest = DeleteQuest(repo)
    )


    @Provides
    @Named("questsRef")
    fun provideQuestCollRef(db: FirebaseFirestore): CollectionReference {
        return db.collection("quests")
    }

}
/*
    @Provides
    @Named("questsRef")
    fun provideCollectionReference(db: FirebaseFirestore): CollectionReference? {
        return db.collection("quests")
    }
}
*/