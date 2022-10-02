package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.cloud.QuestsRepository
import com.instance.dataxbranch.quests.Quest


class AddQuestbyQuest(
    private val repo: QuestsRepository
) {
    operator fun invoke(
        quest: Quest
    ) = repo.addQuestToCloud(quest)
}