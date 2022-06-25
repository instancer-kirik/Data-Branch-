package com.instance.dataxbranch.data.firestore

import com.instance.dataxbranch.quests.Quest

data class FirestoreUser (
    val fsid: String = "-2",
    val uid: Long = 1L,
    val uname: String = "default_uname",
    val name: String = "kirik",
    val imageUrl: String = "",
    //val price: Long = 100L,
    val tagline: String = "Dessert",
    val bio: String = "Dessert",
    //val sourceUrl: String = ""
    //@field:JvmField // use this annotation if your Boolean field is prefixed with 'is'


    val rating: Int = 5,
    val rating_denominator: Int = 5,
    val traits: List<String> =listOf("remarkable"),
    val dateAdded: String= "",
    val dob: String= "",
    val dateUpdated: String= "",
    val completedQuests: List<Long>,
    val abilities: List<Long>,
    val activeQuest: List<Long>,
    val dockedQuests: List<Long>,

    var energy: Int = 5,
    var strength: Int = 4,
    var vitality: Int = 5,
    var stamina: Int = 4,
    var wisdom: Int = 4,
    var charisma: Int = 5,
    var intellect: Int = 4,
    var magic: Int = 5,
    var dexterity: Int = 4,
    var agility: Int = 4,
    var speed: Int = 4,
    var height: Int = 4,
    var allignment: Int = 4,//1 being lawful evil, 3 chaotic evil, to 9 Chaotic good
    var life: Int = 100,
    var mana: Int = 5,
    var money: Int =136,
    var level: Int = 100,


    var history: String ="one day, I was born, and another I got a sledgehammer. today I'm writing this",
)
/*
For a model class with default values like the `Snack` class above, Firestore can perform automatic
serialization in `DocumentReference#set()` and automatic deserialization in
`DocumentSnapshot#toObject()`. For more information on data mapping in Firestore, see the
documentation on [custom objects][firestore-custom-objects].

*/



//var _objectives: MutableList<Quest.QuestObjective> = mutableListOf()

