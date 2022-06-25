package com.instance.dataxbranch.quests

import androidx.room.Embedded
import androidx.room.Relation
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import javax.inject.Inject

data class QuestWithObjectives @Inject constructor(
    @Embedded
    var quest: QuestEntity,

    //val title: String,
    //val title: String? =quest.title,
    //val author: String? =quest.author,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    var objectives: List<ObjectiveEntity>
 )

     /*fun addObjectives(vararg objs:ObjectiveEntity)  {Each { obj->objectives.add(obj)}
     }*/





