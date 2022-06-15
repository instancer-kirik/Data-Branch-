package com.instance.dataxbranch.data.entities

import androidx.room.*
//import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.utils.Converters

@Entity(
    tableName = "quests",
    indices = [
        Index(value = ["id"], unique = true),
        //Index(value = ["oid"])
        //Index(value = ["trakt_id"], unique = true),
        //Index(value = ["tmdb_id"])
    ],
   /* foreignKeys = [
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

 data class QuestEntity @JvmOverloads constructor(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long=0,
    @ColumnInfo(name = "qid") var qid: String? = "-1",

    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "original_title") val originalTitle: String? = null,

    @ColumnInfo(name = "country") var country: String? = null,

    @ColumnInfo(name = "active") var active: Int? = null,
    @ColumnInfo(name = "completed") var completed: Boolean=false,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "publisher") var publisher: String? = null,

    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "featuredImage") var featuredImage: String="",
    @ColumnInfo(name = "rating") var rating: Int = 0,
    @ColumnInfo(name = "sourceUrl") val sourceUrl: String="",
    @ColumnInfo(name = "ingredients") var ingredients: String = "",
    //@ColumnInfo(name = "objectivesjson") var objectivesjson: List<String> = listOf(""),//this to be json
    @ColumnInfo(name = "region") var region: String = "state or region here. goal: sort by region",
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