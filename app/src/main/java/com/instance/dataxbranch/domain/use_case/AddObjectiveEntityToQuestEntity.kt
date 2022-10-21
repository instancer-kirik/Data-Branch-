package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.repository.LocalQuestsRepository
import com.instance.dataxbranch.quests.QuestWithObjectives

class AddObjectiveEntityToQuestEntity(private val repo: LocalQuestsRepository){//

    operator fun invoke(quest: QuestWithObjectives,objective: ObjectiveEntity) = repo.bind(quest,objective)


}