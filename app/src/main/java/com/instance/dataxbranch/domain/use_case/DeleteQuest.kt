package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.firestore.QuestsRepository


class DeleteQuest(
    private val repo: QuestsRepository
) {
    suspend operator fun invoke(qid: String) = repo.deleteQuestFromFirestore(qid)
}