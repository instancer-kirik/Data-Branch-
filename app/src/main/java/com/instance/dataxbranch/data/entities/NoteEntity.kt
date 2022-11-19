package com.instance.dataxbranch.data.entities

import android.content.Context
import androidx.room.*
import com.instance.dataxbranch.utils.Converters
import com.squareup.moshi.JsonClass
import java.util.*

@Entity(tableName = "notes",indices = [
    Index(value = ["uuid"], unique = true)])

@TypeConverters(Converters::class)
@JsonClass(generateAdapter=true)
data class NoteEntity @JvmOverloads constructor(
    @ColumnInfo(name = "uuid") @PrimaryKey var uuid: String = UUID.randomUUID().toString(),
    //@ColumnInfo(name = "nid")@PrimaryKey(autoGenerate = true) val iid: Long=0,
    @ColumnInfo(name = "title") var title: String = "Note1",
    @ColumnInfo(name = "body") var body: String = "null",
    //@ColumnInfo(name = "damage") var damage: Int =0,
    //@ColumnInfo(name = "castTime") var castTime: String="now",
    //@ColumnInfo(name = "cooldown") var cooldown: String="wait a sec",
    @ColumnInfo(name = "uname at creation") var author: String="",
    @ColumnInfo(name = "context1") var context1: String="",
    @ColumnInfo(name = "context") var context: Context? = null,

) {
}
