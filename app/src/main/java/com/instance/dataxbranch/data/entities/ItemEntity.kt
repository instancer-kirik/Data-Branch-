package com.instance.dataxbranch.data.entities

import androidx.room.*
import com.instance.dataxbranch.utils.Converters
import com.squareup.moshi.JsonClass

@Entity (tableName = "items",
indices = [
Index(value = ["aid"], unique = true)
],
foreignKeys = [
ForeignKey(
entity= CharacterEntity::class,
parentColumns = arrayOf("id"),
childColumns = arrayOf("iid"),
onUpdate = ForeignKey.CASCADE,
onDelete = ForeignKey.CASCADE

)]

)  /*CREATE TABLE "items" ( "name" TEXT,
"uid" INTEGER NOT NULL UNIQUE,
"recipe_id" INTEGER,
"description" TEXT,
"ingredients1_uid" INTEGER,
"ingredients2_uid" INTEGER,
"ingredients3_uid" INTEGER,
"other_ingredients" TEXT,
"weight" REAL,
"density" INTEGER,
"info" TEXT,
"note" TEXT,
"Color" TEXT,
"recipes_with_item" BLOB,
"links" TEXT,
PRIMARY KEY("uid" AUTOINCREMENT) );*/
@TypeConverters(Converters::class)
@JsonClass(generateAdapter=true)
data class ItemEntity @JvmOverloads constructor(

    @ColumnInfo(name = "iid")@PrimaryKey(autoGenerate = true) val iid: Long=0,
    @ColumnInfo(name = "name") var name: String? = "Ability1",
    @ColumnInfo(name = "desc") var desc: String? = null,
    @ColumnInfo(name = "damage") var damage: Int?=null,
    @ColumnInfo(name = "castTime") var castTime: String="now",
    @ColumnInfo(name = "cooldown") var cooldown: String="wait a sec",
    @ColumnInfo(name = "uname at creation") var author: String="",
    var color: String = "",
    var weight: Float = 0.0F,
    var density: Float = 0.0F,
    var note: String? = "",
    var recipes_with_item: List<Long> = listOf(),
    var links: List<String> =  listOf(),
    var ingredients: List<Long> =  listOf(),
    var other_ingredients: String?= null,
    var has: Boolean = false,
    var levels: List<String> = listOf("regular","fine","superior","exceptional","masterful","artifact"),//var levels: List<Pair<Int,String>> = listOf(),
    //var levelup: List<Int> = listOf(5,15,55,200),//when trainedAmt is above each increment fetches level.
    //var inloadout: Boolean = false
    // var requiredStats: List<Pair<String,Int>> = listOf(), this one's trickier.. need stat type and value of it energy mana juice etc

) {
    fun getLevel():String{

        /*levelup.forEachIndexed{index,it->
            if (trainedAmt<it)
                return levels[index]
        }*/
        return levels.first()//.last()
    }
    fun OnCasted():Int{
        /*trainedAmt+=1
        return (trainedAmt)*/
        return 1
    }
}


