package com.instance.dataxbranch.data

import com.instance.dataxbranch.quests.Quest

data class FireStoreQuest (
    val id: Long = 1L,
    val title: String = "Study Math_Default",
    val imageUrl: String = "",
    //val price: Long = 100L,
    val tagline: String = "Dessert",
    //val sourceUrl: String = ""
    //@field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    val description: String = "default description",
    val publisher: String = "Kirik",
    val authors: List<String> = listOf("Kirik"),
    val rating: Int = 5,
    val rating_denominator: Int = 5,
    val ingredients: String="",
    val dateAdded: String= "",
    val dateUpdated: String= "",
    val objectives: ArrayList<Quest.QuestObjective> = arrayListOf(
        Quest.QuestObjective()
    ),
)
/*
For a model class with default values like the `Snack` class above, Firestore can perform automatic
serialization in `DocumentReference#set()` and automatic deserialization in
`DocumentSnapshot#toObject()`. For more information on data mapping in Firestore, see the
documentation on [custom objects][firestore-custom-objects].

*/



//var _objectives: MutableList<Quest.QuestObjective> = mutableListOf()

