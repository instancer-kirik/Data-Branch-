package com.instance.dataxbranch.data.local

//import androidx.coort androidx.comport androidx.compose.runtireber
import androidx.room.Embedded
import androidx.room.Relation
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.entities.User


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
    fun fixattunment():Boolean{
        val them=abilities.filter{ it.inloadout }
        if (them.size > user.attunement){//too many abilities
        them.forEach { ae ->
            ae.inloadout=false

        }
            return true
    }
        return false}
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
}



//val title: String,
//val title: String? =quest.title,
//val author: String? =quest.author,
