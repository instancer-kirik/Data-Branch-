package com.instance.dataxbranch.data.entities

import androidx.room.*
import com.instance.dataxbranch.utils.Converters
import com.squareup.moshi.JsonClass
import java.util.*


@Entity(
    tableName = "abilities",
    indices = [
        Index(value = ["aid"], unique = true)
    ],
   /* foreignKeys = [
        ForeignKey(
            entity= CharacterEntity::class,
            parentColumns = arrayOf("uuid"),
            childColumns = arrayOf("aid"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE

        )]*/
        /*    CREATE TABLE "abilities" (
        "uid" INTEGER NOT NULL UNIQUE,
        "LeveL" INTEGER,
        "name" INTEGER,
        "nugget_id" INTEGER NOT NULL,
        "icon" BLOB,
        "attached_file" BLOB,
        "info" TEXT,
        PRIMARY KEY("uid" AUTOINCREMENT) );*/

)
@TypeConverters(Converters::class)
@JsonClass(generateAdapter=true)
data class AbilityEntity @JvmOverloads constructor(
    @ColumnInfo(name = "aid")@PrimaryKey(autoGenerate=true) val aid: Long=0,
    @ColumnInfo(name = "title") var title: String? = "Ability1",
    @ColumnInfo(name = "casted") var casted: Boolean=false,
    @ColumnInfo(name = "desc") var desc: String? = null,
    //@ColumnInfo(name = "objectiveType") var objectiveType: Quest.ObjectiveType = Quest.ObjectiveType.Default,
    @ColumnInfo(name = "requiredEnergy") var requiredEnergy: Int?=null,
    @ColumnInfo(name = "damage") var damage: Int?=null,

    @ColumnInfo(name = "castTime") var castTime: String="now",
    @ColumnInfo(name = "cooldown") var cooldown: String="chainable combo ability locked, practice more",
    @ColumnInfo(name = "trainedAmt") var trainedAmt: Int=0,
    @ColumnInfo(name = "uname at creation") var author: String="",
    var levels: List<String> = listOf("beginner","adept","experienced","expert"),//var levels: List<Pair<Int,String>> = listOf(),
    var levelup: List<Int> = listOf(5,15,55,200),//when trainedAmt is above each increment fetches level.
    var inloadout: Boolean = false
   // var requiredStats: List<Pair<String,Int>> = listOf(), this one's trickier.. need stat type and value of it energy mana juice etc

) {
    fun getLevel():String{

        levelup.forEachIndexed{index,it->
            if (trainedAmt<it)
                return levels[index]
        }
        return levels.last()
    }
    fun OnCasted():Int{
        trainedAmt+=1
        return (trainedAmt)
    }
}
data class Action(
    val name: String,
    val description: String,
    val energyCost: Int,
    val manaCost: Int=0,
    val cooldown: Int,
    val targetType: TargetType
) {
    enum class TargetType {
        SELF, ALLY, ENEMY, AREA
    }

    fun performAction(user: Character, target: Character) {
        // Implement the logic to perform the action
        // This could include modifying character stats, applying effects, calculating damage, etc.
        // You can customize this method based on the specific actions in your game
    }
}