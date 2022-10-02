package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.cloud.QuestsRepository


class DeleteQuest(
    private val repo: QuestsRepository
) {
    suspend operator fun invoke(qid: String) = repo.deleteQuestFromCloud(qid)
}