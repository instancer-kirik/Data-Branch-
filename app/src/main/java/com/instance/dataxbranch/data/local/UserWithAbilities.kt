package com.instance.dataxbranch.data.local

//import androidx.coort androidx.comport androidx.compose.runtireber
import androidx.room.Embedded
import androidx.room.Relation
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.firestore.FirestoreUser


data class UserWithAbilities (
    @Embedded
    var user: User,

    //val title: String,
    //val title: String? =quest.title,
    //val author: String? =quest.author,


    @Relation(
        parentColumn = "abilities",
        entityColumn = "aid",


    )
    var abilities: List<AbilityEntity>,
    //val aidList: Array<String>

){
    fun fixattunement(){
        //val them=abilities.filter{ it.inloadout }
        user.attuned = abilities.filter{it.inloadout}.size

        }
    fun clearLoadoutAbilities(){
        abilities.filter{ it.inloadout }.forEach { ae ->
            ae.inloadout=false
        }
    }
    fun combine(tangent: UserWithAbilities){
        user.combine(tangent.user)
        abilities+=tangent.abilities
    }
    fun toFireStoreUser(fsid:String?=null):FirestoreUser{
        if(fsid != null){
           // user.fsid=fsid
        }
        return FirestoreUser(//notice how these are all cloud data
            activeCloudQuests =user.activeCloudQuests,
            cloudAbilities =user.cloudAbilities,
            completedCloudQuests = user.completedCloudQuests,
            dockedCloudQuests = user.dockedCloudQuests,
            rating = user.rating,
            rating_denominator = user.rating_denominator,
            //fsid =user.fsid,
            uid=user.uid,
            uname = user.uname,
            name = user.name,
            imageUrl = user.imageUrl,
            tagline = user.tagline,
            bio = user.bio,
            traits= user.traits,
            dateAdded=user.dateAdded, dateUpdated=user.dateUpdated,
            dob=user.dob,
            status=user.status,
            terms_status=user.terms_status,
            energy=user.energy,
            strength=user.strength,
            vitality=user.vitality,
            stamina=user.stamina,
            wisdom=user.wisdom,
            charisma=user.charisma,
            intellect=user.intellect,
            magic=user.magic,
            dexterity=user.dexterity,
            agility=user.agility,
            speed=user.speed,
            height=user.height,
            allignment=user.allignment,
            life=user.life,
            mana=user.mana,
            money=user.money,
            level=user.level,
            hearts=user.hearts,
            attunement=user.attunement,
            defaultScreen=user.defaultScreen,
            history=user.history,

            )
    }
}
   /* fun setStats(m:Map<String,Int>){
        user.hearts= m["hearts "]!!

        user.energy= m["energy "] !!
      user.hearts=m["hearts "]!!
    user.life=m["LIFE "]!!
    user.agility= m["AGILITY "]!!
             user.intellect=m["INT "]!!
             user.wisdom=m["WIS "]!!
             user.charisma=m["CHA "]!!
             user.magic=m["MAG "]!!
             user.strength=m["STR "]!!
             user.stamina=m["STA "]!!
             user.dexterity=m["DEX "]!!
             user.speed=m["SPD "]!!
             user.constitution=m["CON "]!!
    }*/




//val title: String,
//val title: String? =quest.title,
//val author: String? =quest.author,
