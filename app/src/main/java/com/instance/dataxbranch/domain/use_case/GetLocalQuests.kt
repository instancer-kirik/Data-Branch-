package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.daos.QuestDao

class GetLocalQuests(

    private val dao: QuestDao

){
    operator fun invoke() = dao.getAll()
}