package com.instance.dataxbranch.data.entities

import androidx.room.*
import com.instance.dataxbranch.quests.Quest
import com.squareup.moshi.JsonClass
import java.util.*

@Entity(
    tableName = "objectives",
    indices = [
        Index(value = ["uuid"], unique = true)
        //Index(value = ["trakt_id"], unique = true),
        //Index(value = ["tmdb_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = arrayOf("uuid"),
            childColumns = arrayOf("qid"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE

        )
    ]

)
@JsonClass(generateAdapter=true)
data class ObjectiveEntity @JvmOverloads constructor(//oid and qid are room's id... the other one is firebase---- it's now rqid
     @ColumnInfo(name = "uuid")@PrimaryKey val uuid:String= UUID.randomUUID().toString(),//Long=0,was oid
    @ColumnInfo(name = "obj") var obj: String? = null,
    @ColumnInfo(name = "qid") var qid: String? = null,
    //@ColumnInfo(name = "id") var id: Long=0,
    @ColumnInfo(name = "completed") var completed: Boolean=false,
    @ColumnInfo(name = "desc") var desc: String? = null,
    @ColumnInfo(name = "objectiveType") var objectiveType: Quest.ObjectiveType = Quest.ObjectiveType.Default,
    @ColumnInfo(name = "requiredAmount") var requiredAmount: Int?=null,
    @ColumnInfo(name = "currentAmount") var currentAmount: Int?=null,
    @ColumnInfo(name = "beginDateAndTime") var beginDateAndTime: String="now",

    ) {

}
