package com.instance.dataxbranch.data.local

//import androidx.coort androidx.comport androidx.compose.runtireber
import androidx.room.Embedded
import androidx.room.Relation
import com.instance.dataxbranch.data.entities.*
//import com.instance.dataxbranch.data.firestore.FirestoreCharacter
import com.instance.dataxbranch.quests.QuestWithObjectives


data class CharacterWithStuff (
    @Embedded
    var character: CharacterEntity,

    //val title: String,
    //val title: String? =quest.title,
    //val author: String? =quest.author,
    @Relation(
        parentColumn = "abilities",
        entityColumn = "aid",


    )
    var abilities: List<AbilityEntity> = listOf(),

    @Relation(
        parentColumn = "quests",
        entityColumn = "id",


        )
    var quests: Array<QuestWithObjectives>,
    //val aidList: Array<String>

){
val uuid = character.uuid
    @Relation(
        parentColumn = "inventory",
        entityColumn = "id",


        )
    var inventory: Array<ItemEntity> = arrayOf()
    //val aidList: Array<String>
    /*var equipment: MutableMap<String, Long?> = mutableMapOf("L" to null, "R" to null,
        "Head" to null,"Necklace" to null, "Torso" to null,"Legs" to null,"Feets" to null,
        "Shoulders" to null,"Arms" to null,"Wrists" to null, "Hands" to null,
        "Ring1" to null,"Ring2" to null,"Ring3" to null,"Ring4" to null,"Ring5" to null )*/
    fun fixattunement(){
        //val them=abilities.filter{ it.inloadout }
        character.attuned = abilities.filter{it.inloadout}.size

        }

    fun clearLoadoutAbilities(){
        abilities.filter{ it.inloadout }.forEach { ae ->
            ae.inloadout=false
        }
    }

    fun combine(tangent: CharacterWithStuff){
        character.combine(tangent.character)
        abilities+=tangent.abilities
    }
    /*fun toFireStoreCharacter(fsid:String?=null):FirestoreCharacter{
        if(fsid != null){
            character.fsid=fsid
        }
        return FirestoreCharacter(//notice how these are all cloud data
            activeCloudQuests =character.activeCloudQuests,
            cloudAbilities =character.cloudAbilities,
            completedCloudQuests = character.completedCloudQuests,
            dockedCloudQuests = character.dockedCloudQuests,
            rating = character.rating,
            rating_denominator = character.rating_denominator,
            fsid =character.fsid,
            uid=character.uid,
            uname = character.uname,
            name = character.name,
            imageUrl = character.imageUrl,
            tagline = character.tagline,
            bio = character.bio,
            traits= character.traits,
            dateAdded=character.dateAdded, dateUpdated=character.dateUpdated,
            dob=character.dob,
            status=character.status,
            terms_status=character.terms_status,
            energy=character.energy,
            strength=character.strength,
            vitality=character.vitality,
            stamina=character.stamina,
            wisdom=character.wisdom,
            charisma=character.charisma,
            intellect=character.intellect,
            magic=character.magic,
            dexterity=character.dexterity,
            agility=character.agility,
            speed=character.speed,
            height=character.height,
            allignment=character.allignment,
            life=character.life,
            mana=character.mana,
            money=character.money,
            level=character.level,
            hearts=character.hearts,
            attunement=character.attunement,
            defaultScreen=character.defaultScreen,
            history=character.history,

            )
    }*/

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharacterWithStuff

        if (character != other.character) return false
        if (abilities != other.abilities) return false
        if (!quests.contentEquals(other.quests)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = character.hashCode()
        result = 31 * result + abilities.hashCode()
        result = 31 * result + quests.contentHashCode()
        return result
    }
}
   /* fun setStats(m:Map<String,Int>){
        character.hearts= m["hearts "]!!

        character.energy= m["energy "] !!
      character.hearts=m["hearts "]!!
    character.life=m["LIFE "]!!
    character.agility= m["AGILITY "]!!
             character.intellect=m["INT "]!!
             character.wisdom=m["WIS "]!!
             character.charisma=m["CHA "]!!
             character.magic=m["MAG "]!!
             character.strength=m["STR "]!!
             character.stamina=m["STA "]!!
             character.dexterity=m["DEX "]!!
             character.speed=m["SPD "]!!
             character.constitution=m["CON "]!!
    }*/




//val title: String,
//val title: String? =quest.title,
//val author: String? =quest.author,
