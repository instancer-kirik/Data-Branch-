package com.instance.dataxbranch.data.entities

//import com.instance.dataxbranch.quests.Quest
//import android.util.Log
import androidx.room.*
import com.instance.dataxbranch.domain.getNow
import com.instance.dataxbranch.utils.Converters
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@Entity(
    tableName = "quests",
    indices = [
        Index(value = ["uuid"], unique = true),
        //Index(value = ["oid"])
        //Index(value = ["trakt_id"], unique = true),
        //Index(value = ["tmdb_id"])
    ],
   /*CREATE TABLE "quests" ( "quest_id" INTEGER NOT NULL UNIQUE, "Quest_Name" INTEGER NOT NULL, "Description" INTEGER, "Reward" INTEGER, "ENCODED_OBJECTIVES_DATES_EVENTMARKERS_PLOTLINES" INTEGER, "SOURCE" INTEGER NOT NULL, "DATE" REAL, "SOURCE_USER_ID" INTEGER, "SOURCE_MEDIA" TEXT, "SOURCE_MODEL" TEXT, "PARTY_SIZE" INTEGER, "TRANSPO_LEVEL" INTEGER, "SPECIAL_TRANSPO" TEXT, "SPECIAL_EQUIPMENT" TEXT, "LOCATION" TEXT, "DURATION" REAL, "LEVEL" INTEGER, PRIMARY KEY("quest_id" AUTOINCREMENT) );
INSERT INTO "main"."quests" VALUES('0','0','','','','0','','','','','','','','','','','');
INSERT INTO "main"."quests" VALUES('1','first_quest','this is your context ALSO mode of transportation is -1(walkable) 0(skateboard) 1(bike) 2(car) 3(racecar) 4(rally) 5(offroad vehicle-clearance/snow) 6(truck) 7(drift) 8(SPECIAL_TRANSPO)','twenty chicken nuggets','a bunch of data here worry about this later. also source is -1(I created) 0(usercreated own) 1(pulled from movie/game/story 2(given by someone else)','0','7/7/2021','1','','','-1','0','','nothing','here','now','1');
INSERT INTO "main"."quests" VALUES('2','0','','','','0','','','','','','','','','','','');

 foreignKeys = [
        ForeignKey(
            entity = ObjectiveEntity::class,
            parentColumns = arrayOf("rqid"),
            childColumns = arrayOf("oid"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE

        )
    ]
*/
)
@TypeConverters(Converters::class)
@JsonClass(generateAdapter=true)
 data class QuestEntity @JvmOverloads constructor(
    @ColumnInfo(name = "uuid") @PrimaryKey val uuid:String = UUID.randomUUID().toString(),//(autoGenerate = true)
    //@ColumnInfo(name = "qid") var qid: String? = "-1",

    @ColumnInfo(name = "title") var title: String? = null,
    //@ColumnInfo(name = "original_title") val originalTitle: String? = null,

    //@ColumnInfo(name = "country") var country: String? = null,

    @ColumnInfo(name = "active") var active: Int? = null,//change in MyQuestsScreen on lazyselection
    @ColumnInfo(name = "completed") var completed: Boolean?=null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "questGiver") var questGiver: String? = null,
    @ColumnInfo(name = "AuthorUid") var authoruid: String? = null,
    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "featuredImage") var featuredImage: String? = null,
    ///////////////6/26/23
    //@ColumnInfo(name = "rating") var rating: Int = 0,
    //@ColumnInfo(name = "rating_denominator") var rating_denominator: Int = 5,
    //@ColumnInfo(name = "sourceUrl") var sourceUrl: String="",
    @ColumnInfo(name = "ingredients") var ingredients: String? = null,
    //@ColumnInfo(name = "objectivesjson") var objectivesjson: List<String> = listOf(""),//this to be json
    @ColumnInfo(name = "region") var region: String?= null,
    @ColumnInfo(name = "reward") var reward: String?= null,
    @ColumnInfo(name = "reward_xp") var rewardxp: Int?=null,//set by calculating it on quest create rewardxp*difficulty

    @ColumnInfo(name = "isHabit") var isHabit: Boolean?=null,//if habit, reward is randomly, and sporatically given,
    @ColumnInfo(name = "habitStreak") var habitStreak: Int? = null,
    @ColumnInfo(name = "dateLastDone") var dateLastDone: String? = null,
    @ColumnInfo(name = "target") var targetDateTime: String = LocalDateTime.now().plusDays(5).toString(),



    //@ColumnInfo(name = "oids") var oids: List<Int> =listOf(),
    //var check: Nothing = TODO()
    /*@ColumnInfo(name = "trakt_id") override val traktId: Int? = null,
        @ColumnInfo(name = "tmdb_id") override val tmdbId: Int? = null,
        @ColumnInfo(name = "imdb_id") val imdbId: String? = null,
        @ColumnInfo(name = "overview") val summary: String? = null,
        @ColumnInfo(name = "homepage") val homepage: String? = null,*/

) {

     fun onDone(){
         dateLastDone=getNow()
     }
    fun beenAwhile():Boolean{
        return dateAnalyzer(qDate =getNow())==1
    }
    fun dateAnalyzer(myDate: String? =dateLastDone, qDate: String): Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val cal: Calendar = Calendar.getInstance()
        if(myDate == ""){return 0}
        try {

            formatter.parse(myDate)?.let { cal.setTime(it) }
            cal.add(Calendar.DATE, 1)
            //Log.d(TAG,"DATE DEBUG IN QUESTENTITY ${cal.before(qDate)}")
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
    fun describe():String{
        return "${title} "
    }
}

/**/