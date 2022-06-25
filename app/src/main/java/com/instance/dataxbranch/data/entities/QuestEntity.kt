package com.instance.dataxbranch.data.entities

import androidx.room.*
//import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.utils.Converters
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@Entity(
    tableName = "quests",
    indices = [
        Index(value = ["id"], unique = true),
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
//@JvmOverloads for constuctor
 data class QuestEntity @JvmOverloads constructor(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long=0,
    @ColumnInfo(name = "qid") var qid: String? = "-1",

    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "original_title") val originalTitle: String? = null,

    @ColumnInfo(name = "country") var country: String? = null,

    @ColumnInfo(name = "active") var active: Int? = null,//change in MyQuestsScreen on lazyselection
    @ColumnInfo(name = "completed") var completed: Boolean=false,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "publisher") var publisher: String? = null,
    @ColumnInfo(name = "AuthorUid") var authoruid: String? = null,
    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "featuredImage") var featuredImage: String="",
    @ColumnInfo(name = "rating") var rating: Int = 0,
    @ColumnInfo(name = "sourceUrl") var sourceUrl: String="",
    @ColumnInfo(name = "ingredients") var ingredients: String = "",
    //@ColumnInfo(name = "objectivesjson") var objectivesjson: List<String> = listOf(""),//this to be json
    @ColumnInfo(name = "region") var region: String = "state or region here. goal: sort by region",
    @ColumnInfo(name = "reward") var reward: String = "",
    @ColumnInfo(name = "reward_xp") var rewardxp: Int = 25,//set by calculating it on quest create rewardxp*difficulty
    //@ColumnInfo(name = "oids") var oids: List<Int> =listOf(),
    //var check: Nothing = TODO()
/*@ColumnInfo(name = "trakt_id") override val traktId: Int? = null,
    @ColumnInfo(name = "tmdb_id") override val tmdbId: Int? = null,
    @ColumnInfo(name = "imdb_id") val imdbId: String? = null,
    @ColumnInfo(name = "overview") val summary: String? = null,
    @ColumnInfo(name = "homepage") val homepage: String? = null,*/

) {

}

/**/