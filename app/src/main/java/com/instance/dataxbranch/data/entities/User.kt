package com.instance.dataxbranch.data.entities

import androidx.room.*
import com.google.firebase.firestore.auth.User
import com.instance.dataxbranch.utils.Converters
import com.instance.dataxbranch.utils.constants.DEFAULT_UNAME
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

@Entity(
    tableName = "my_resources_attributes_stats",
    indices = [
        Index(value = ["me_id"], unique = true)
    ]

)
@TypeConverters(Converters::class)
@JsonClass(generateAdapter=true)
data class User @JvmOverloads constructor(
    @ColumnInfo(name = "me_id")@PrimaryKey(autoGenerate = true) val me_id: Long=0,
   // @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "uid")val uid: Long = 1L,
    @ColumnInfo(name = "uname")var uname: String = DEFAULT_UNAME,
    @ColumnInfo(name = "name")var name: String = "name",
    @ColumnInfo(name = "imageUrl")val imageUrl: String = "",
    //val price: Long = 100L,
    @ColumnInfo(name = "tagline")var tagline: String = "like an email signature",
    @ColumnInfo(name = "bio") var bio: String = "bio",
    @ColumnInfo(name = "firestore_id") val fsid: String? = null,
    //val sourceUrl: String = ""
    //@field:JvmField // use this annotation if your Boolean field is prefixed with 'is'

    //<xp,description> make ability from parcel. Parcel loader from Interfacer??
    var energy: Int = 3,
    var strength: Int = 4,
    var constitution: Int = 5,
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
    var isreal: Boolean=false,
    var attunement: Int = 1,
    var defaultScreen:Int = -1,
    var initflag:Boolean = false,
    var history: String ="successfully survived previous encounters",
    @ColumnInfo(name = "rating")var rating: Int = 1,
    @ColumnInfo(name = "rating_denominator") var rating_denominator: Int = 1,
    @ColumnInfo(name = "traits")val traits: List<String> =listOf("remarkable"),
    @ColumnInfo(name = "dateAdded")val dateAdded: String= "June 14,2022",
    @ColumnInfo(name = "dob")val dob: String= "",
    @ColumnInfo(name = "dateUpdated")val dateUpdated: String= "",
    @ColumnInfo(name = "completedQuests")var completedQuests: List<Int> =listOf(),
    @ColumnInfo(name = "abilities")var abilities: List<Int> =listOf(),
    @ColumnInfo(name = "activeQuest")var activeQuest: Long =1L,
    @ColumnInfo(name = "dockedQuests")val dockedQuests: List<Int> =listOf(),
    @ColumnInfo(name = "status")var status: String = "intrepid.. curious"

//authored quests, nuggets,
    ) {


}
