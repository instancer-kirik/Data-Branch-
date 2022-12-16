package com.instance.dataxbranch.quests

import androidx.room.Embedded
import androidx.room.Relation
import com.instance.dataxbranch.data.EntityType
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.ui.calendar.custom.Event
import javax.inject.Inject

data class QuestWithObjectives @Inject constructor(
    @Embedded
    var quest: QuestEntity,

    //val title: String,
    //val title: String? =quest.title,
    //val author: String? =quest.author,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "qid"
    )
    var objectives: List<ObjectiveEntity> = listOf()//just added listOf primer 12/10/22
 ){
fun toDayDisplayData(): Event{
    return Event(
       uuid = quest.uuid,
       type = EntityType.QUEST,
        text = quest.title?:"Q",
    )
}
}

     /*fun addObjectives(vararg objs:ObjectiveEntity)  {Each { obj->objectives.add(obj)}
     }*/





