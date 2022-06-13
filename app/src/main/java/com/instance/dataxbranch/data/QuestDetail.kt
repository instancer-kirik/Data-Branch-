package com.instance.dataxbranch.data

import androidx.room.DatabaseView

/*
@DatabaseView("SELECT quests.id, quests.title, q," +
        "department.name AS departmentName FROM quests " +
        "INNER JOIN objectives ON objectives.id= quests.id")
data class QuestDetail(
    val id: Long,
    val name: String?,
    val departmentId: Long,
    val departmentName: String?
)*/
