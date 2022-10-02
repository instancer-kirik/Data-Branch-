package com.instance.dataxbranch.data.firestore

import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.utils.constants

data class FirestoreUser(
    val fsid: String = "-2",
    val uid: Long = 1L,
    val uname: String = constants.DEFAULT_UNAME,
    val name: String = "name",
    val imageUrl: String = "",
    ////////////////////////////////////IF I CHANGE THIS, CHANGE TYPE CONVERTERS IN BOTH LOCAL AND REMOTE/////////////////////////////////////////////////
    val tagline: String = "like an email signature",
    val bio: String = "bio",
    //val sourceUrl: String = ""
    //@field:JvmField // use this annotation if your Boolean field is prefixed with 'is'


    val rating: Int = 1,
    val rating_denominator: Int = 1,


    val traits: List<String> =listOf("remarkable"),

    val dateAdded: String= "",

    val dob: String= "",
    val dateUpdated: String= "",
    //val completedQuests: List<String>,
    var completedCloudQuests: List<String> =listOf(),


    val cloudAbilities: List<String> =listOf(),
    val activeCloudQuests: List<String> =listOf(),//for quests or  abilities cannot have long, only has access to string
    val dockedCloudQuests: List<String> = listOf(),
    var status: String = "intrepid.. curious",
    var terms_status: String = "", //update with timestamp of accepting terms

    var energy: Int = 5,
    var strength: Int = 4,
    var vitality: Int = 5,
    var constitution: Int = 5,
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
    var hearts: Int = 1,
    var attunement: Int = 1,

    var defaultScreen:Int = -1,


    var history: String ="one day, I was born, and another I got a sledgehammer. today I'm writing this",
    //8/21/2022
var xp: Int = 0,














){
    fun toLocalUser():User{
    return User(//notice how these are all cloud data
        //fsid =this.fsid,
        uid=this.uid,
        uname = this.uname,
        name = this.name,
        imageUrl = this.imageUrl,
        tagline = this.tagline,
        bio = this.bio,
        rating = this.rating,
        rating_denominator = this.rating_denominator,
        traits= this.traits,
        dateAdded=this.dateAdded,
        energy=this.energy,
        strength=this.strength,
        vitality=this.vitality,
        stamina=this.stamina, wisdom=this.wisdom,
        charisma=this.charisma,
        intellect=this.intellect,
        magic=this.magic,
        dexterity=this.dexterity,
        agility=this.agility,
        speed=this.speed,
        height=this.height,
        allignment=this.allignment,
        life=this.life,
        mana=this.mana,
        money=this.money,
        level=this.level,
        hearts=this.hearts,
        attunement=this.attunement,
        defaultScreen=this.defaultScreen,
        history=this.history,
        dob=this.dob,
        dateUpdated=this.dateUpdated,
        completedCloudQuests = this.completedCloudQuests,
        cloudAbilities =this.cloudAbilities,
        activeCloudQuests =this.activeCloudQuests,
        dockedCloudQuests = this.dockedCloudQuests,
        status=this.status,
        terms_status=this.terms_status,
        //8/21/2022
        xp = this.xp
    )
}}
/*
For a model class with default values like the `Snack` class above, Firestore can perform automatic
serialization in `DocumentReference#set()` and automatic deserialization in
`DocumentSnapshot#toObject()`. For more information on data mapping in Firestore, see the
documentation on [custom objects][firestore-custom-objects].

*/



//var _objectives: MutableList<Quest.QuestObjective> = mutableListOf()

