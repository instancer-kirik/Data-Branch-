package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.data.entities.QuestEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddQuestEntitybyQuest(
    private val dao: QuestDao
) {
/*
    val moshi: Moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<Quest.QuestObjective> = moshi.adapter(Quest.QuestObjective::class.java)
    val type: Type = Types.newParameterizedType(
        Quest.QuestObjective::class.java
    )
    val jsonAdapter: JsonAdapter<Quest.QuestObjective> =
        moshi.adapter(type)

    val json = jsonAdapter.toJson(Quest.QuestObjective())
*/

    operator fun invoke(//returns questEntity to operand// actually now QuestWithObjectives
        value: Quest

    ) =CoroutineScope(Dispatchers.IO).launch{
        val q =QuestEntity(
            //id = value.id.toLong() doesn't set here. autogens
            uuid = value.qid,
            title=value.title + "",
            originalTitle = value.originalTitle,
            country = value.country,
            active = 0,
            description=value.description + "",
            questGiver = value.questGiver + "",
            author= value.author + "",
            featuredImage = value.featuredImage + "",
            rating = value.rating,
            sourceUrl = value.sourceUrl,
            ingredients = value.ingredients,
            region = value.region
        )
        dao.save(q).also {
            value.objectives.forEach { objective -> dao.save(objective.convert(q.uuid)) }}


    }

}

//repo.addQuestToFirestore(quest)
/*
val questObjectiveJsonAdapter= QuestObjectiveJsonAdapter()
                val moshi: Moshi = Moshi.Builder().build()
                val adapter: JsonAdapter<Quest.QuestObjective> = moshi.adapter(Quest.QuestObjective::class.java)

                quest.objectives.forEach { objective -> ObjectiveViewEdit(objective =  adapter.fromJsonValue(objective)!!
                ObjectiveEntity(
                oid = objective.oid,
                obj = objective.obj,
                beginDateAndTime = objective.beginDateAndTime,
                desc = objective.desc,
                objectiveType = objective.objectiveType,
                requiredAmount = objective.requiredAmount,
                currentAmount = objective.currentAmount,
                quest = "DEBUG_AddQuestEntitybyQuest"
            )
 */