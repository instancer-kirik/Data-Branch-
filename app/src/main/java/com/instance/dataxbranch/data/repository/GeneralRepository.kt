package com.instance.dataxbranch.data.repository

import android.app.Application
import android.util.Log
import com.instance.dataxbranch.core.Constants.TAG

import com.instance.dataxbranch.data.daos.AbilityDao

import com.instance.dataxbranch.data.daos.UserDao

import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.daos.ItemDao
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.*
import com.instance.dataxbranch.data.firestore.FirestoreUser
import com.instance.dataxbranch.data.local.CharacterWithStuff
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.quests.QuestWithObjectives
import kotlinx.coroutines.*

import javax.inject.Singleton
@Singleton
class GeneralRepository(application: Application, db: AppDatabase,
                        val questsRepository: LocalQuestsRepository,
                        val itemRepository: ItemRepository
) {


    private var cachedUsers: List<FirestoreUser> = listOf()
    val aDao: AbilityDao=db.abilityDao()
    val uDao: UserDao=db.userDao()
    val qDao: QuestDao =db.questDao()
    val iDao: ItemDao = db.itemDao()
    var mabilities: List<AbilityEntity> = listOf()
    var mcharacters: List<CharacterWithStuff> = listOf(
        CharacterWithStuff(
            CharacterEntity(name="ME"),
            listOf(AbilityEntity(title = "my_ability1")),
            arrayOf(QuestWithObjectives(QuestEntity(title="my_quest1") ,listOf()))
        ),
        CharacterWithStuff(
            CharacterEntity(name="PRIME1"),
            listOf(AbilityEntity(title = "APRIME1")),
            arrayOf(QuestWithObjectives(QuestEntity(title="QPRIME1") ,listOf()))
        ),
        CharacterWithStuff(
            CharacterEntity(name="PRIME2"),
            listOf(AbilityEntity(title = "APRIME2")),
            arrayOf(QuestWithObjectives(QuestEntity(title="QPRIME2") ,listOf()))
        ))

    private lateinit var me:User
    lateinit var selectedAE: AbilityEntity
    var selectedCharacterIndex: Int = 0
    var selectedCharacterWithStuff: CharacterWithStuff = mcharacters[selectedCharacterIndex]
    private var me_container= UserWithAbilities(User(),mabilities)
    init {
        sync()
        //CoroutineScope(Dispatchers.IO).launch {}//on conflict abort
    }
    private fun getMeWithAbilities(){
        CoroutineScope(Dispatchers.IO).launch {
            //.d(TAG, "in GENERALREPO with $me")
            me_container =
                if (me!=null) {me.initflag =true
                    UserWithAbilities(me, mabilities)

                } else {
                    UserWithAbilities(User(), mabilities)
                }

        }
    }
    fun sync(){
        CoroutineScope(Dispatchers.IO).launch {//this might cause issues with data not being loaded fast enough
            uDao.prime(User())
            me = uDao.getMe()
            mabilities = aDao.getAbilites()
            getMeWithAbilities()
            getAllCharacters()

        }
    }
    fun save(me: User): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.save(me)
        }
    fun save(char: CharacterWithStuff =selectedCharacterWithStuff): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.save(char.character)
            //char.abilities.forEach {aDao.save(it)}
            //should save elsewhere, but whateev
            char.quests.forEach {qDao.save(it)}
            char.abilities.forEach{aDao.save(it)}
            char.inventory.forEach{iDao.save(it)}
            //qDao.save(char.quests)
        }
    fun syncAE(): Job = CoroutineScope(Dispatchers.IO).launch {
        aDao.update(selectedAE)


    }
    fun syncAE(ae:AbilityEntity): Job = CoroutineScope(Dispatchers.IO).launch {
        aDao.update(ae)


    }
    fun getAllCharacters():Job =

        CoroutineScope(Dispatchers.IO).launch {
//            //mcharacters= CharacterWithStuff(uDao.getAllCharacters())
            var seen: Int=0
            uDao.getAllCharacters().forEach{
                if(mcharacters.any { character->character.character.character_id==it.character_id }){
                    seen++
                }else {
                    mcharacters = mcharacters.plus(CharacterWithStuff(it))
                }

            }
            Log.d(TAG,"REPO CALLED, seen $seen ALREADY IN LIST")
        }

    private fun CharacterWithStuff(character: CharacterEntity,) : CharacterWithStuff {
        return CharacterWithStuff(character,mabilities,getQuestsbyCharacter(character))
    }
    private fun CharacterWithStuff(characters: List<CharacterEntity>) :List<CharacterWithStuff> {
        val result : MutableList<CharacterWithStuff> = mutableListOf()
        characters.forEach {character->
            result.plus(CharacterWithStuff(character,mabilities,getQuestsbyCharacter(character)))
        }

        return result

    }

    fun getACharacter(id:Long ):Job =
        CoroutineScope(Dispatchers.IO).launch {
            selectedCharacterWithStuff=getCharacterWithStuff(id)
        }
    fun syncCharacter(char: CharacterWithStuff):Job =
        CoroutineScope(Dispatchers.IO).launch {
            selectedCharacterWithStuff=char
            uDao.update(char.character)

        }
    fun makeACharacter(name: String):Job =
        CoroutineScope(Dispatchers.IO).launch {
            var character= CharacterWithStuff(CharacterEntity(name=name))
            mcharacters= mcharacters.plus(character)
            uDao.insertCharacter(character.character)
        }
    fun setUpSelectedWithMe():Job =
        CoroutineScope(Dispatchers.IO).launch {


        }
    //private lateinit var me_container: UserWithAbilities
        //lateinit var me_user: User

        //private lateinit var mquests: Array<QuestWithObjectives>

    /*private fun initRepo(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.insert(User())

                val user =uDao.getMe()
            me_container = if (user != null) {
                if(user.isreal){
                    UserWithAbilities(user,mabilities)
                } else{
                    UserWithAbilities(User(),mabilities)
                }
            } else {
                UserWithAbilities(User(),mabilities)
            }
                    // do something else

            }*/
     fun getCharacterWithStuff(id: Long): CharacterWithStuff {
        //var abilities: List<AbilityEntity> =listOf()
        //var qwos: Array<QuestWithObjectives> =questsRepository.getQuests()
           val character = uDao.getCharacterEntity(id)
            //character.abilities.forEach{ abilities=abilities.plus(aDao.getAbilitesByaid(it))}
           // abilities =
            //character.quests.forEach{qwos = qwos.plus(questsRepository.questById(it))}
           // qwos =
           //val objectives: List<ObjectiveEntity> = getObjectiveEntityList(id)
           //val qwo = QuestWithObjectives(quest,objectives)

        //qwos.filter{it.quest.id in character.quests}.toTypedArray()
           return CharacterWithStuff(character,
               mabilities.filter{it.aid in character.abilities},
               getQuestsbyCharacter(character))
       }
        fun getQuestsbyCharacter(character:CharacterEntity):Array<QuestWithObjectives>{
            val qwos: Array<QuestWithObjectives> =questsRepository.getQuests()
            return qwos.filter{it.quest.id in character.quests}.toTypedArray()
        }
        fun newQuestOnCharacter(questEntity: QuestEntity){
            //var qwo: QuestWithObjectives =
            CoroutineScope(Dispatchers.IO).launch {
                selectedCharacterWithStuff.character.quests=selectedCharacterWithStuff.character.quests.plus(qDao.save(questEntity))
                //uDao.save(selectedCharacterWithStuff.character)

                save()
            }

        }
    fun putAbilityOnCharacter(ae: AbilityEntity) {
        selectedCharacterWithStuff.character.abilities=selectedCharacterWithStuff.character.abilities.plus(ae.aid)
        selectedCharacterWithStuff.abilities= selectedCharacterWithStuff.abilities.plus(ae)
        CoroutineScope(Dispatchers.IO).launch {
            save()
            // udao.update(me)
        }
    }

    fun putItemOnCharacter(item: ItemEntity) {
        selectedCharacterWithStuff.character.items=selectedCharacterWithStuff.character.items.plus(item.iid)

        selectedCharacterWithStuff.inventory= selectedCharacterWithStuff.inventory.plus(item)
        Log.d("REPO_putItemOnCharacter", "item  is $item in generalRepo putItemOnCharacter")
        CoroutineScope(Dispatchers.IO).launch {
            save()
            // udao.update(me)
        }
    }
        fun updateCharacterQuests(){
            selectedCharacterWithStuff.quests=getQuestsbyCharacter(selectedCharacterWithStuff.character)
            Log.d(TAG,"UPDATECHARACTERQUESTS")
        }
        fun refresh(): Job =

            CoroutineScope(Dispatchers.IO).launch {
                mabilities =aDao.getAbilites()
                me=uDao.getMe()
            }
    fun getAbilities(): List<AbilityEntity> = mabilities
    /*fun sync(){

    }*/
    fun getUserFromRoom():User{
        CoroutineScope(Dispatchers.IO).launch {

            me= uDao.getMe()
        }
        return me
    }


    fun pullMeFromCloud(fsid:String){

    }
    fun getMe(): UserWithAbilities =me_container
    fun resetAndSet(new_me: UserWithAbilities){
        CoroutineScope(Dispatchers.IO).launch {
            uDao.nukeTable()
            uDao.setMe(me_container.user)
        }

    }
    fun setMe(new_me: UserWithAbilities) {
        me_container= new_me

        CoroutineScope(Dispatchers.IO).launch {

           uDao.setMe(me_container.user)
        }

    }

   /* withContext(Dispatchers.IO){
        uDao.getMeAbilities()
    }*/

    fun insertAbility(ability: AbilityEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            aDao.save(ability)
        }
    suspend fun insertItem(name:String="ITEM_DEFAULT", item: ItemEntity=ItemEntity(name = name, author =  getMe().user.uname)):Long{
        var id:Long= item.iid

        id= withContext(Dispatchers.IO){
            iDao.save(item)
        }

        if (id==0L) {
            Log.d("REPO_insertItem", "RUH ROH id ==0 ")
        }else{Log.d("REPO_insertItem", "id  is $id")}


        return id
    }
/*WORKS
*/
    /*fun insertAbility(ae:AbilityEntity): AbilityEntity {

fun insertItem(name:String="ITEM_DEFAULT", item: ItemEntity=ItemEntity(name = name, author =  getMe().user.uname)): Job =
        val id:Long= item.iid
        CoroutineScope(Dispatchers.IO).launch {
            iDao.save(item)
        }

        CoroutineScope(Dispatchers.IO).launch {
            aDao.insert(ae)
        }
        return ae
    }*/
/*fun insertAbility(title: String): Job =
        CoroutineScope(Dispatchers.IO).launch {
            aDao.insert(AbilityEntity(title=title,author=me_container.user.uname))
        }*/
    fun setUsers(output: List<FirestoreUser>) {
        cachedUsers=output
    }
    fun getCachedUsers(): List<FirestoreUser> {
       return cachedUsers
    }



    /*fun sync(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.save(me_user)

        }*/
}