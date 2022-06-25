package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.firestore.QuestsRepository


class GetQuests (
    private val repository: QuestsRepository

    ){
    operator fun invoke() = repository.getQuestsFromFirestore()
}