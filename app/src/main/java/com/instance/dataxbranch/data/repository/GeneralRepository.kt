package com.instance.dataxbranch.data.repository

import android.app.Application
import android.util.Log
import com.google.common.collect.Iterables.size
import com.instance.dataxbranch.core.Constants.TAG

import com.instance.dataxbranch.data.daos.AbilityDao

import com.instance.dataxbranch.data.daos.UserDao

import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.daos.ItemDao
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.*
//import com.instance.dataxbranch.data.firestore.CloudUser
import com.instance.dataxbranch.data.local.CharacterWithStuff
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.quests.QuestWithObjectives
import kotlinx.coroutines.*
import java.util.*

import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class GeneralRepository(application: Application, db: AppDatabase,
                        val questsRepository: LocalQuestsRepository,
                        val itemRepository: ItemRepository
) {

    var needsSync: Boolean = true
   // private var cachedUsers: List<CloudUser> = listOf()
    val aDao: AbilityDao = db.abilityDao()
    val uDao: UserDao = db.userDao()
    val qDao: QuestDao = db.questDao()
    val iDao: ItemDao = db.itemDao()
    var mabilities: List<AbilityEntity> = listOf()
    var mcharacters: List<CharacterWithStuff> by Delegates.observable(listOf()) { property, oldValue, newValue ->

        Log.d("ITEMREPO"," CHANGED  $property and  oldval len=${size(oldValue)}, ${oldValue.toString()} and newval len=${size(newValue)}, ${newValue.toString()}")
        //if (newValue.iid ==0L){ selectedItem =oldValue}// this prevents resetting to id=0 bug
    }
    var ncharacters:List<CharacterEntity> = listOf()
    private lateinit var me: User
    lateinit var selectedAE: AbilityEntity

    //var selectedItem: ItemEntity = ItemEntity()
    var selectedCharacterIndex: Int = 0
    var selectedCharacterWithStuff: CharacterWithStuff = CharacterWithStuff(CharacterEntity())
    private var me_container = UserWithAbilities(User(), mabilities)

    init {
        sync()

        //CoroutineScope(Dispatchers.IO).launch {}//on conflict abort
    }

    private fun getMeWithAbilities() {
        CoroutineScope(Dispatchers.IO).launch {
            //.d(TAG, "in GENERALREPO with $me")
            me_container =
                if (me != null) {
                    me.initflag = true
                    UserWithAbilities(me, mabilities)

                } else {
                    UserWithAbilities(User(), mabilities)
                }

        }
    }

    fun primeCharacters() {
    mcharacters = listOf(
        CharacterWithStuff(
        CharacterEntity(name ="ME"),
        listOf(AbilityEntity(title = "my_ability1")),
        arrayOf(QuestWithObjectives(QuestEntity(title="my_quest1") ,listOf()))
        ),
        CharacterWithStuff(
        CharacterEntity(name ="PRIME1"),
        listOf(AbilityEntity(title = "APRIME1")),
        arrayOf(QuestWithObjectives(QuestEntity(title="QPRIME1") ,listOf()))
        ),
        CharacterWithStuff(
        CharacterEntity(name ="PRIME2"),
        listOf(AbilityEntity(title = "APRIME2")),
        arrayOf(QuestWithObjectives(QuestEntity(title="QPRIME2") ,listOf()))
        )
    )
        CoroutineScope(Dispatchers.IO).launch {
            mcharacters.forEach{uDao.prime(it.character)}  }
}
    fun sync(andItems:Boolean = false, andQuests:Boolean = false){
//        fixInventory()
        fixInventory()
        Log.d("REPO","SYNC CALLED with ${needsSync}, andItems=$andItems, andQuests = $andQuests")
        if (needsSync){

        CoroutineScope(Dispatchers.IO).launch {//this might cause issues with data not being loaded fast enough
            uDao.prime(User())

            me = uDao.getMe()
            mcharacters = CharacterWithStuff(uDao.getAllCharacters())
            mabilities = aDao.getAbilites()
            getMeWithAbilities()
            getAllCharacters()

        }
            if(andItems){
                itemRepository.sync()
                fixInventory()}

            if(andQuests){
                questsRepository.refresh()
                updateCharacterQuests()
            }
    }
        needsSync=false
    }
    fun refresh(): Job =

        CoroutineScope(Dispatchers.IO).launch {
            mabilities =aDao.getAbilites()
            me=uDao.getMe()
        }
    fun save(me: User): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.save(me)
            needsSync=true
        }
    fun save2(char: CharacterWithStuff =selectedCharacterWithStuff): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.save(char.character)
            //char.abilities.forEach {aDao.save(it)}
            //should save elsewhere, but whateev
            char.quests.forEach {qDao.save(it)}
            char.abilities.forEach{aDao.save(it)}
            char.inventory.forEach{iDao.save(it)}
            //qDao.save(char.quests)
            needsSync=true
        }
    fun save(char: CharacterWithStuff=selectedCharacterWithStuff):Job =
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG,"FUCKING SAVED ${char.character}" )
//            selectedCharacterWithStuff=char
            uDao.save(char.character)
            updateByCharacterList(char)
            /*char.quests.forEach {qDao.update(it)}
            char.abilities.forEach{aDao.update(it)}
            char.inventory.forEach{iDao.update(it)}*/
            needsSync=true
        }
    fun syncAE(ae:AbilityEntity=selectedAE): Job = CoroutineScope(Dispatchers.IO).launch {
        aDao.update(ae)
        needsSync=true
    }
    fun syncItem(item:ItemEntity=itemRepository.selectedItem): Job = CoroutineScope(Dispatchers.IO).launch {
        iDao.update(item)
        needsSync=true
    }
    fun getAllCharacters():Job =

        CoroutineScope(Dispatchers.IO).launch {
            if (mcharacters.isEmpty()){ primeCharacters() }
            ncharacters = uDao.getAllCharacters()
            Log.d(TAG,"REPO BUILT, ${ncharacters} IN LIST")
            mcharacters = CharacterWithStuff(ncharacters)
            mcharacters.forEach {Log.d(TAG,"REPO BUILT, ${it.toString()} IN LIST")}


        }


          /*  var seen: Int=0
            val gotten = uDao.getAllCharacters()
            Log.d(TAG,"REPO CALLED, seen $seen ALREADY IN LIST")
                gotten.forEach{

                    Log.d(TAG,"gotten $it ")
                    if (it.id!=0L){Log.d(TAG,"actual gotten $it ")}
                if(mcharacters.any { character->character.character.id==it.id }){
                    seen++
                }else {
                    mcharacters = mcharacters.plus(CharacterWithStuff(it))
                }

            }
            Log.d(TAG,"REPO CALLED, seen $seen ALREADY IN LIST")*/


    private fun CharacterWithStuff(character: CharacterEntity,) : CharacterWithStuff {
        return CharacterWithStuff(character,getAbilitiesbyCharacter(character),getQuestsbyCharacter(character))
    }
    private fun CharacterWithStuff(characters: List<CharacterEntity>) :List<CharacterWithStuff> {
        var result : MutableList<CharacterWithStuff> = mutableListOf()
        characters.forEach {character->
            result.add(CharacterWithStuff(character,getAbilitiesbyCharacter(character),getQuestsbyCharacter(character)))
        }

        return result.toList()

    }

    fun getACharacter(uuid:UUID ):Job =
        CoroutineScope(Dispatchers.IO).launch {
            selectedCharacterWithStuff=getCharacterWithStuff(uuid)
        }

    fun makeACharacter(name: String):Job =
        CoroutineScope(Dispatchers.IO).launch {
            var character= CharacterWithStuff(CharacterEntity(name =name))
            mcharacters= mcharacters.plus(character)
            uDao.insertCharacter(character.character)
            needsSync=true
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
     fun getCharacterWithStuff(uuid: UUID): CharacterWithStuff {
        //var abilities: List<AbilityEntity> =listOf()
        //var qwos: Array<QuestWithObjectives> =questsRepository.getQuests()
           val character = uDao.getCharacterEntity(uuid)
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
    fun getAbilitiesbyCharacter(character:CharacterEntity):List<AbilityEntity>{
       // val qwos: Array<QuestWithObjectives> =questsRepository.getQuests()
        return mabilities.filter{it.aid in character.abilities}
    }
        fun putQuestOnCharacter(questEntity: QuestEntity){
            //var qwo: QuestWithObjectives =
            CoroutineScope(Dispatchers.IO).launch {
                selectedCharacterWithStuff.character.quests=selectedCharacterWithStuff.character.quests.plus(qDao.save(questEntity))
                //uDao.save(selectedCharacterWithStuff.character)

                save()
            }

        }
    fun putQuestOnCharacter(quest: QuestWithObjectives){
        //var qwo: QuestWithObjectives =
        CoroutineScope(Dispatchers.IO).launch {
            selectedCharacterWithStuff.character.quests=selectedCharacterWithStuff.character.quests.plus(qDao.save(quest))
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

    fun putItemOnCharacter(item: ItemEntity=itemRepository.selectedItem) {
        selectedCharacterWithStuff.character.items=selectedCharacterWithStuff.character.items.plus(item.iid)

        selectedCharacterWithStuff.inventory= selectedCharacterWithStuff.inventory.plus(item)
        Log.d("REPO_putItemOnCharacter", "item  is $item in generalRepo putItemOnCharacter")
        CoroutineScope(Dispatchers.IO).launch {
            save()
            // udao.update(me)
        }
    }
    fun fixInventory(){selectedCharacterWithStuff.inventory = filterLocalItemsOnID()}
    fun filterLocalItemsOnID(ids:List<Long> =selectedCharacterWithStuff.character.items): Array<ItemEntity> = itemRepository.getitems().filter{it.iid in ids}.toTypedArray()

    fun updateCharacterQuests(){
            selectedCharacterWithStuff.quests=getQuestsbyCharacter(selectedCharacterWithStuff.character)
            Log.d(TAG,"UPDATECHARACTERQUESTS")
        }

    fun getAbilities(): List<AbilityEntity> = mabilities

    fun getUserFromRoom():User{
        CoroutineScope(Dispatchers.IO).launch {

            me= uDao.getMe()
        }
        return me
    }


    fun getMe(): UserWithAbilities =me_container
    fun resetAndSet(new_me: UserWithAbilities){
        CoroutineScope(Dispatchers.IO).launch {
            uDao.nukeTable()
            uDao.setMe(me_container.user)
        }
        needsSync=true
    }
    fun setMe(new_me: UserWithAbilities) {
        me_container= new_me

        CoroutineScope(Dispatchers.IO).launch {

           uDao.setMe(me_container.user)
        }
        needsSync=true
    }

   /* withContext(Dispatchers.IO){
        uDao.getMeAbilities()
    }*/

    fun insertAbility(ability: AbilityEntity): Job =
        CoroutineScope(Dispatchers.IO).launch {
            aDao.save(ability)
            needsSync=true
        }
    suspend fun insertItem(name:String="ITEM_DEFAULT", item: ItemEntity=ItemEntity(name = name, author =  getMe().user.uname)):Long{
        var id:Long= item.iid

        id= withContext(Dispatchers.IO){
            iDao.save(item)
        }

        if (id==0L) {
            Log.d("REPO_insertItem", "RUH ROH id ==0 ")
        }else{Log.d("REPO_insertItem", "id  is $id")}

        needsSync=true
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
  /*  fun setUsers(output: List<CloudUser>) {
        cachedUsers=output
    }
    fun getCachedUsers(): List<CloudUser> {
       return cachedUsers
    }*/
  /*  fun addButDontOverride(news: List<CharacterWithStuff>){
    news.forEach{it->

        mcharacters = mcharacters.plus(it)
    }

    if(mcharacters.isNotEmpty()){

    }}
    */

    fun updateByCharacterList(selectedCharacter: CharacterWithStuff=selectedCharacterWithStuff) {
        val old = mcharacters[selectedCharacterIndex].toString()
        mcharacters = mcharacters.toMutableList().apply {
            this[selectedCharacterIndex] = selectedCharacter
        }
        Log.d("REPO", "mcharacters updated index $selectedCharacterIndex with $selectedCharacter ================================from --------------OLD--------------- $old")
    }

    fun deleteCharacter(character: CharacterWithStuff, ) {
        mcharacters=mcharacters.filter { it.character.uuid != character.uuid }
        CoroutineScope(Dispatchers.IO).launch {
            uDao.delete(character.character) //check to see if not deleting quests/abilities
        }
        return
    }
    fun delete(ae:AbilityEntity){
        mabilities=mabilities.filter { it.aid != ae.aid }
        CoroutineScope(Dispatchers.IO).launch {
            aDao.delete(ae) //check to see if not deleting quests/abilities
        }
        return
    }

    /*fun sync(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.save(me_user)

        }*/
}