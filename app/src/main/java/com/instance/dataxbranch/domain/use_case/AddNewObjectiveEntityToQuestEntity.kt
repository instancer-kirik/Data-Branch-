package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.data.local.LocalQuestsRepository

class AddNewObjectiveEntityToQuestEntity(private val repo: LocalQuestsRepository){//

    operator fun invoke(quest: QuestWithObjectives) = repo.newObjectiveEntity(quest)


}//.add(ObjectiveEntity(quest =quest.toString())