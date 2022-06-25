package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.QuestEntity

class AddQuestEntity(
    private val dao: QuestDao
)
     {
        suspend operator fun invoke(
             ent: QuestEntity
        ) = dao.save(ent)
}