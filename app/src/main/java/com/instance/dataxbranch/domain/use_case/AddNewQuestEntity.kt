package com.instance.dataxbranch.domain.use_case

import android.util.Log
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.local.LocalQuestsRepository


class AddNewQuestEntity (
    private val repo: LocalQuestsRepository
    ) {
        operator fun invoke(title: String,description:String, author: String): Long {
            repo.insertQuestEntity(QuestEntity(title =title,description=description, author = author).also{
                Log.d("USE CASE",it.toString()


                )
                return it.id
            })

        }

}

