package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.QuestWithObjectives
import com.instance.dataxbranch.data.repository.LocalQuestsRepository

class AddNewObjectiveEntityToQuestEntity(private val repo: LocalQuestsRepository){//

    operator fun invoke(quest: QuestWithObjectives) = repo.newObjectiveEntity(quest)


}//.add(ObjectiveEntity(quest =quest.toString())