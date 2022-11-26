package com.instance.dataxbranch.data.entities

//import android.util.Log

import androidx.room.*
import com.instance.dataxbranch.data.MapConverter
import com.instance.dataxbranch.domain.getNow
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.utils.Converters
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


/*
@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,//this means that category is connected to other categories. and that parent cats are marked with
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = CASCADE
        )],
    indices = [Index(value = ["parentId"])]
)
class Category(@field:PrimaryKey val id: String, val title: String?, val parentId: String?) {
    @Ignore
    constructor(title: String?) : this(title, null) {
    }

    @Ignore
    constructor(title: String?, parentId: String?) : this(
        UUID.randomUUID().toString(),
        title,
        parentId
    ) {
    }
}
*/

@Entity(
    tableName = "characters",//my_resources_attributes_stats
    indices = [
        Index(value = ["uuid"], unique = true)//character_

    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("me_id"),
            childColumns = arrayOf("user_id"),//"uuid"
            /*onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE*/

        ),

    ]

)
@TypeConverters(Converters::class, MapConverter::class)
@JsonClass(generateAdapter=true)
data class CharacterEntity @JvmOverloads constructor(
   // @ColumnInfo(name = "character_id") val character_id: Long=0,
    //@ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long=0,
    @ColumnInfo(name = "uuid") @PrimaryKey var uuid: String =UUID.randomUUID().toString(),
    //@ColumnInfo(name = "firestore_id") var fsid: String = "-1",
   // @ColumnInfo(name = "title") var title: String? = null,
    //@ColumnInfo(name = "uid") val uid: Long = 1L,
    @ColumnInfo(name = "user_id")var user_id: String?=null,
    //@ColumnInfo(name = "uname")var uname: String = DEFAULT_UNAME,
    @ColumnInfo(name = "name") var name: String = "DEFAULT_NAME",
    @ColumnInfo(name = "imageUrl") var imageUrl: String = "",
    //val price: Long = 100L,
    @ColumnInfo(name = "tagline") var tagline: String = "like an email signature",
    @ColumnInfo(name = "bio") var bio: String = "bio",
    @ColumnInfo(name = "rating") var rating: Int = 1,
    @ColumnInfo(name = "rating_denominator") var rating_denominator: Int = 1,
    @ColumnInfo(name = "traits") var traits: List<String> =listOf("remarkable"),
    @ColumnInfo(name = "dateAdded") val dateAdded: String= "07-01-2022",//"June 14,2022",
    //val sourceUrl: String = ""
    //@field:JvmField // use this annotation if your Boolean field is prefixed with 'is'

    //<xp,description> make ability from parcel. Parcel loader from Interfacer??
    var energy: Int = 3,
    var strength: Int = 4,
    var vitality: Int=5,
    var stamina: Int = 4,
    var wisdom: Int = 4,
    var charisma: Int = 5,
    var intellect: Int = 4,
    var magic: Int = 3,
    var dexterity: Int = 4,
    var agility: Int = 4,
    var speed: Int = 4,
    var height: Int = 4,
    var allignment: Int = 4,//1 being lawful evil, 3 chaotic evil, to 9 Chaotic good
    var life: Int = 100,
    var mana: Int = 5,
    var money: Int =136,
    var level: Int = 1,
    var hearts: Int = 1,
    var attunement: Int = 1,
    var attuned:Int = 0,
    var playerAvatarjson: String = "",
    var defaultScreen:Int = -1,
    var constitution: Int = 5,

    var isreal: Boolean=false,


    var initflag:Boolean = false,
    var history: String ="successfully survived previous encounters",
    var cloud: Boolean = false,

    var hasKilled: Boolean = false,
    var hasDied: Boolean = false,
    var numKills: Int = 0,
    var numDeaths: Int = 0,
    var friends: List<Int> = listOf(),//just use ids
    @ColumnInfo(name = "dob") var dob: String= "",
    @ColumnInfo(name = "dateUpdated") var dateUpdated: String= "",

    @ColumnInfo(name = "completedCloudQuests") var completedCloudQuests: List<String> =listOf(),//listof fsid


    @ColumnInfo(name = "abilities") var abilities: List<Long> =listOf(),
    @ColumnInfo(name = "cloudAbilities") var cloudAbilities: List<String> =listOf(),

    @ColumnInfo(name = "activeCloudQuests") var activeCloudQuests: List<String> =listOf(),
    @ColumnInfo(name = "activeQuests") var activeQuests: List<String> =listOf(),
    @ColumnInfo(name = "quests") var quests: List<String> =listOf(),//dockedQuests
    @ColumnInfo(name = "dockedCloudQuests") var dockedCloudQuests: List<String> =listOf(),


    @ColumnInfo(name = "status") var status: String = "intrepid.. curious",
    @ColumnInfo(name = "terms_status") var terms_status: String = "",//update this on accepting terms
    var xp: Int = 0,
    @ColumnInfo(name = "race") var race: String = "Generic",
    @ColumnInfo(name = "class") var className: String = "Nothing",

     //var items: List<Long> = listOf(),
    var inventory:Map<Long,Int> = mapOf(),
//store list of authored quests, nuggets,abilities,items etc

//progression 11/1/2022
    @ColumnInfo(name = "completedQuests") var completedQuests: Map<String,Pair<String,String>> =mapOf(),//by id:Title (Or a recognizable String stamp of quest completion with time, xp, etc)
    //Map<ID,Pair<date,slug>>
    //I want dates. to see when you complete a quest.
    //build a calendar. see marks for quest completed. and marks for each habit. Lets get that library
    @ColumnInfo(name = "habitTracker") var habitTracker: Map<String,Pair<List<String>,String>> =mapOf(),//by id:List<DateTime>







    ) {

    fun getActiveQuest(): String{
        return if(activeQuests.isEmpty()){
            "0L"
        } else{
            activeQuests[0]
        }
    }
    /*fun updateAttuned(){
        attuned =
    }*/
    fun isDateValid(myDate: String) : Boolean {

         val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        try {

            val date = formatter.parse(myDate)
            if (date != null) {
                return !date.before(Date())
            }
            else{return false}
        } catch(ignored: java.text.ParseException) {
            return false
        }
    }
    fun whichNewer(myDate: String=dateUpdated, qDate: String): Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        try {

            val date = formatter.parse(myDate)
            val qdate = formatter.parse(qDate)
            //date.toString()
            if (date != null) {
                if (qdate != null) {
                    return if(date.before(qdate)){
                        1
                    }else{
                        0
                    }
                }
            } else{return -1}
        } catch(ignored: java.text.ParseException) {
            //Log.d(TAG, "returning -1 with $ignored")
            return -1
        }
        return -1
    }


    fun combine(tangent: CharacterEntity): Int{
        val newer = whichNewer(dateUpdated,tangent.dateUpdated)
        if (newer ==1) {
            //newer is tangent
            friends+=tangent.friends
            activeCloudQuests +=tangent.activeCloudQuests
            cloudAbilities +=tangent.cloudAbilities
            completedCloudQuests += tangent.completedCloudQuests
            dockedCloudQuests += tangent.dockedCloudQuests
            rating = tangent.rating
            rating_denominator = tangent.rating_denominator
            uuid =tangent.uuid
            //uid=tangent.uid
            //uname = tangent.uname characters don't have usernames
            name = tangent.name
            imageUrl = tangent.imageUrl
            tagline = tangent.tagline
            bio = tangent.bio
            traits= tangent.traits
            //dateAdded=tangent.dateAdded
            dateUpdated=tangent.dateUpdated
            dob=tangent.dob
            status=tangent.status
            terms_status=tangent.terms_status
            energy=tangent.energy
            strength=tangent.strength
            vitality=tangent.vitality
            stamina=tangent.stamina
            wisdom=tangent.wisdom
            charisma=tangent.charisma
            intellect=tangent.intellect
            magic=tangent.magic
            dexterity=tangent.dexterity
            agility=tangent.agility
            speed=tangent.speed
            height=tangent.height
            allignment=tangent.allignment
            life=tangent.life
            mana=tangent.mana
            money=tangent.money
            level=tangent.level
            hearts=tangent.hearts
            attunement=tangent.attunement
            defaultScreen=tangent.defaultScreen
            history=tangent.history
            hasKilled = tangent.hasKilled
            hasDied = tangent.hasDied
            numKills = tangent.numKills
            numDeaths = tangent.numDeaths
            //8/21/2022
            xp = tangent.xp
            //9/9/2022
            className = tangent.className
            race = tangent.race
            }
        else if(newer ==0) {
            //newer is user
            //do nothing
            //update maybe?
            //Log.d(TAG, "LOCAL IS NEWEST NO ACTION IN USER")
        }
        else if(newer ==-1) {
            //Log.d(TAG, "BAD DATE COMPARE RETURN IN USER")
        }
return newer
        }


        /*activeCloudQuests +=tangent.activeCloudQuests
        cloudAbilities +=tangent.cloudAbilities
        completedCloudQuests += tangent.completedCloudQuests
        dockedCloudQuests += tangent.dockedCloudQuests
        rating = tangent.rating
        rating_denominator = tangent.rating_denominator
        fsid =tangent.fsid
        //uid=tangent.uid
        uname = tangent.uname
        name = tangent.name
        imageUrl = tangent.imageUrl
        tagline = tangent.tagline
        bio = tangent.bio
        traits= tangent.traits
        //dateAdded=tangent.dateAdded
        dateUpdated=tangent.dateUpdated
        dob=tangent.dob
        status=tangent.status
        terms_status=tangent.terms_status
        energy=tangent.energy
        strength=tangent.strength
        vitality=tangent.vitality
        stamina=tangent.stamina
        wisdom=tangent.wisdom
        charisma=tangent.charisma
        intellect=tangent.intellect
        magic=tangent.magic
        dexterity=tangent.dexterity
        agility=tangent.agility
        speed=tangent.speed
        height=tangent.height
        allignment=tangent.allignment
        life=tangent.life
        mana=tangent.mana
        money=tangent.money
        level=tangent.level
        hearts=tangent.hearts
        attunement=tangent.attunement
        defaultScreen=tangent.defaultScreen
        history=tangent.history
        hasKilled = tangent.hasKilled
        hasDied = tangent.hasDied
        numKills = tangent.numKills
        numDeaths = tangent.numDeaths
        */
/*
    fun onComplete(q:QuestWithObjectives){
        if (q.quest.isHabit){
            habitTracker[q.quest.id].first=
        }

    }*/

    fun habitIncrementAddNow(quest:QuestWithObjectives){
        var editing =habitTracker.toMutableMap()
        habitTracker[quest.quest.uuid]?.let {
            editing[quest.quest.uuid]=Pair(it.first+ LocalDateTime.now().toString(),it.second)
        }?:run{
            editing[quest.quest.uuid]=Pair(listOf(LocalDateTime.now().toString()),quest.quest.describe())
        }
        habitTracker=editing//done
    }
    fun QuestAddNow(quest:QuestWithObjectives){
        var editing =completedQuests.toMutableMap()
        completedQuests[quest.quest.uuid]?.let {
            editing[quest.quest.uuid]=Pair(LocalDateTime.now().toString(),"x${it.second}")
        }?:run{
            editing[quest.quest.uuid]=Pair(LocalDateTime.now().toString(),quest.quest.describe())
        }
        completedQuests=editing//done
    }
    fun setCompletedFakeHabits(){
        habitTracker = mapOf("11S" to Pair(
            listOf(
                getNow(),
                LocalDateTime.of(2022,11,1,11,11).toString(),
                LocalDateTime.of(2022,11,2,11,11).toString(),
                LocalDateTime.of(2022,11,3,11,11).toString(),
                LocalDateTime.of(2022,11,4,11,11).toString(),
                LocalDateTime.of(2022,11,5,11,11).toString(),
                LocalDateTime.of(2022,11,6,11,11).toString(),
                LocalDateTime.of(2022,11,7,11,11).toString(),
                LocalDateTime.of(2022,11,8,11,11).toString(),
                LocalDateTime.of(2022,11,9,11,11).toString())
            ,"R"),
            "12S" to Pair(listOf(
                getNow(),
                LocalDateTime.of(2022,11,1,11,11).toString(),
                LocalDateTime.of(2022,11,2,11,11).toString(),
                LocalDateTime.of(2022,11,3,11,11).toString(),
                LocalDateTime.of(2022,11,4,11,11).toString(),
                LocalDateTime.of(2022,11,9,11,11).toString(),
                LocalDateTime.of(2022,11,12,11,11).toString(),
                LocalDateTime.of(2022,11,5,11,11).toString()),"Q"))//by id:Title (Or a recognizable String stamp of quest completion with time, xp, etc)
    }
    fun setCompletedFakeQuests(){
        completedQuests = mapOf("14S" to Pair(getNow(),"S"),"16S" to Pair(LocalDateTime.now().toString(),"T"),
            "17S" to Pair (LocalDateTime.of(2022,11,1,11,11).toString(),"W"))//by id:Title (Or a recognizable String stamp of quest completion with time, xp, etc)

    }
}
