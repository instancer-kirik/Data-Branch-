package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.firestore.QuestsRepository
import com.instance.dataxbranch.quests.Quest


class AddQuestbyQuest(
    private val repo: QuestsRepository
) {
    operator fun invoke(
        quest: Quest
    ) = repo.addQuestToFirestore(quest)
}