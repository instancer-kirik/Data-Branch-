package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.cloud.QuestsRepository


class AddQuest(
    private val repo: QuestsRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        author: String
    ) = repo.addQuestToCloud(title, description, author)
}