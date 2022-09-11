package com.instance.dataxbranch.data.entities

//import android.util.Log
import androidx.room.*
import com.instance.dataxbranch.core.Constants.TAG

import com.instance.dataxbranch.utils.Converters
import com.instance.dataxbranch.utils.constants.DEFAULT_UNAME
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(
    tableName = "characters",//my_resources_attributes_stats
    indices = [
        Index(value = ["id"], unique = true)//character_




    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("me_id"),
            childColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE

        ),

    ]

)
@TypeConverters(Converters::class)
@JsonClass(generateAdapter=true)
data class CharacterEntity @JvmOverloads constructor(
    @ColumnInfo(name = "character_id") val character_id: Long=0,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long=0,
    @ColumnInfo(name = "firestore_id") var fsid: String = "-1",
   // @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "uid")val uid: Long = 1L,

    //@ColumnInfo(name = "uname")var uname: String = DEFAULT_UNAME,
    @ColumnInfo(name = "name")var name: String = "name",
    @ColumnInfo(name = "imageUrl") var imageUrl: String = "",
    //val price: Long = 100L,
    @ColumnInfo(name = "tagline")var tagline: String = "like an email signature",
    @ColumnInfo(name = "bio") var bio: String = "bio",
    @ColumnInfo(name = "rating")var rating: Int = 1,
    @ColumnInfo(name = "rating_denominator") var rating_denominator: Int = 1,
    @ColumnInfo(name = "traits") var traits: List<String> =listOf("remarkable"),
    @ColumnInfo(name = "dateAdded")val dateAdded: String= "07-01-2022",//"June 14,2022",
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
    @ColumnInfo(name = "completedQuests")var completedQuests: List<Int> =listOf(),
    @ColumnInfo(name = "completedCloudQuests")var completedCloudQuests: List<String> =listOf(),//listof fsid


    @ColumnInfo(name = "abilities")var abilities: List<Long> =listOf(),
    @ColumnInfo(name = "cloudAbilities")var cloudAbilities: List<String> =listOf(),

    @ColumnInfo(name = "activeCloudQuests")var activeCloudQuests: List<String> =listOf(),
    @ColumnInfo(name = "activeQuests")var activeQuests: List<Long> =listOf(),
    @ColumnInfo(name = "quests") var quests: List<Long> =listOf(),//dockedQuests
    @ColumnInfo(name = "dockedCloudQuests") var dockedCloudQuests: List<String> =listOf(),


    @ColumnInfo(name = "status")var status: String = "intrepid.. curious",
    @ColumnInfo(name = "terms_status")var terms_status: String = "",//update this on accepting terms
    var xp: Int = 0,
    @ColumnInfo(name = "race")var race: String = "Generic",
    @ColumnInfo(name = "class")var className: String = "Nothing",
//store list of authored quests, nuggets,abilities,items etc
    ) {

    fun getActiveQuest(): Long{
        return if(activeQuests.isEmpty()){
            0L
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
    fun getNow():String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        println("Current Date and Time is: $formatted")
        return formatted
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
            fsid =tangent.fsid
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


}
