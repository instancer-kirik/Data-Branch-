package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.data.repository.QuestsRepository

class AddQuestbyQuest(
    private val repo: QuestsRepository
) {
    operator fun invoke(
        quest: Quest
    ) = repo.addQuestToFirestore(quest)
}