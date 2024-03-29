package com.instance.dataxbranch.domain.use_case

//import android.util.Log
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.repository.LocalQuestsRepository


class AddNewQuestEntity (
    private val repo: LocalQuestsRepository
    ) {
        operator fun invoke(title: String,description:String, author: String): String {
            repo.insertQuestEntity(QuestEntity(title =title,description=description, author = author).also{
//                Log.d("USE CASE",it.toString())
                return it.uuid
            })

        }

}

