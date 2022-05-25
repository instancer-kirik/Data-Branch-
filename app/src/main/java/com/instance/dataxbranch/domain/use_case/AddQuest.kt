package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.repository.QuestsRepository

class AddQuest(
    private val repo: QuestsRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String
    ) = repo.addQuestToFirestore(title, description)
}