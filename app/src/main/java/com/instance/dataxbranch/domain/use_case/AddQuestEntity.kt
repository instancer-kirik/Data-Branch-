package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.local.AppDatabase
import com.instance.dataxbranch.data.repository.QuestsRepository

class AddQuestEntity(
    private val dao: QuestDao
)
     {
        suspend operator fun invoke(
             ent: QuestEntity
        ) = dao.insert(ent)
}