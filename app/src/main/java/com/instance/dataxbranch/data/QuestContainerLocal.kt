package com.instance.dataxbranch.data

import androidx.room.Embedded
import androidx.room.Relation
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.QuestEntity

 class QuestContainerLocal(
    @Embedded
    var quest: QuestEntity,
    //val title: String? =quest.title,
    //val author: String? =quest.author,
    @Relation(
        parentColumn = "id",
        entityColumn = "quest"
    )
    var objectives: List<ObjectiveEntity>
)