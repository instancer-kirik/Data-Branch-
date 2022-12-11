package com.instance.dataxbranch.data.entities

import android.content.Context
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.*
import androidx.room.*
import com.instance.dataxbranch.ui.theme.RainbowColors
import com.instance.dataxbranch.ui.theme.purple400
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
    @ColumnInfo(name = "when") var date: String = "",
    @ColumnInfo(name = "created") var dateOfCreation: String= "",
    @ColumnInfo(name = "updated") var dateLastUpdated: String = "",
    @ColumnInfo(name = "repeat") var repeat: Int = 0,//0=none, 1=daily, 2=weekly, 3=monthly, 4=yearly
) {

    @OptIn(ExperimentalTextApi::class)
    fun describe(): AnnotatedString =
        buildAnnotatedString {
            //append("Do not allow people to dim your shine\n")
            withStyle(
                SpanStyle(
                    brush = Brush.linearGradient(
                        colors = RainbowColors
                    )
                )
            ) {
                append(""+title )
            }

            //append("\nTell them to put some sunglasses on.")
        }

}
