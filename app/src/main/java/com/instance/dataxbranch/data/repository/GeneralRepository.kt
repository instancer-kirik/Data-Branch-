package com.instance.dataxbranch.data.repository

//import android.util.Log

//import com.instance.dataxbranch.data.firestore.CloudUser

import android.app.Application
import android.util.Log
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.daos.AbilityDao
import com.instance.dataxbranch.data.daos.ItemDao
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.*
import com.instance.dataxbranch.data.local.CharacterWithStuff
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.domain.parse
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.ui.calendar.custom.DayOf12dWeek
import kotlinx.coroutines.*
import java.time.LocalDate
import java.util.*
import javax.inject.Singleton
import kotlin.properties.Delegates


@Singleton
class GeneralRepository(application: Application, db: AppDatabase,
                        val questsRepository: LocalQuestsRepository,
                        val itemRepository: ItemRepository
) {

    var needsSync: Boolean = true
    var initialized: Boolean = false
   // private var cachedUsers: List<CloudUser> = listOf()
    val aDao: AbilityDao = db.abilityDao()
    val uDao: UserDao = db.userDao()
    val qDao: QuestDao = db.questDao()
    val iDao: ItemDao = db.itemDao()
    var mabilities: List<AbilityEntity> = listOf()
    var mcharacters: List<CharacterWithStuff> by Delegates.observable(listOf()) { property, oldValue, newValue ->

        //Log.d("ITEMREPO"," CHANGED  $property and  oldval len=${size(oldValue)}, ${oldValue.toString()} and newval len=${size(newValue)}, ${newValue.toString()}")
        //if (newValue.iid ==0L){ selectedItem =oldValue}// this prevents resetting to id=0 bug
    }
    var ncharacters:List<CharacterEntity> = listOf()
    private lateinit var me: User
    lateinit var selectedAE: AbilityEntity
    val totalDates: MutableMap<LocalDate,DayOf12dWeek> = mutableMapOf()
    //var selectedItem: ItemEntity = ItemEntity()
    var selectedCharacterIndex by Delegates.observable(0) { property, oldValue, newValue ->
        Log.d(TAG,"SELECTED $property IS NOW $newValue")
    }
    var selectedCharacterWithStuff: CharacterWithStuff =CharacterWithStuff(CharacterEntity())
    private var me_container = UserWithAbilities(User(), mabilities)

    init {
        sync()
        calendarPreprocessing()
        //CoroutineScope(Dispatchers.IO).launch {}//on conflict abort
    }

    private fun calendarPreprocessing() {

        var start = LocalDate.of(2022,1,1)
        val end = LocalDate.now().plusYears(5)
        var dayOfWeek= DayOf12dWeek.A
        while (!start.isAfter(end)) {
            totalDates[start] = dayOfWeek
            dayOfWeek += 1
            start = start.plusDays(1)
        }
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

    fun primeCharacters(): List<CharacterWithStuff>? {
    //selectedCharacterWithStuff= mcharacters.first { it.character.name == "ME" }
        if (uDao.isPrimed()==null) {
            //table is empty


            val charme = CharacterWithStuff(
                CharacterEntity(name ="ME"),

            listOf(AbilityEntity(title = "add an ability (tap +)")),
            arrayOf(QuestWithObjectives(QuestEntity(title="Add A Quest") ,listOf()))
            )
            uDao.prime(charme.character)//should keep abilities maybe
            uDao.prime( CharacterEntity(name ="PRIME1") )
            uDao.prime( CharacterEntity(name ="PRIME2"))

            //  mcharacters.forEach{uDao.prime()}  }
        } else {
            // table is not empty
            Log.d("GeneralRepo -- primeCharacters", "NO OP ")
        }

        return CharacterWithStuff(uDao.getAllCharacters())
}
    fun sync(andItems:Boolean = false, andQuests:Boolean = false){
//        fixInventory()
        fixInventory()
        //Log.d("REPO","SYNC CALLED with ${needsSync}, andItems=$andItems, andQuests = $andQuests")
        if (needsSync){

        CoroutineScope(Dispatchers.IO).launch {//this might cause issues with data not being loaded fast enough
            uDao.prime(User())

            me = uDao.getMe()
            Log.d(TAG,"Gotted ${me.selectedCharacterID} ..id")
            mcharacters = CharacterWithStuff(uDao.getAllCharacters())
            if (!initialized) {
                Log.d(TAG,"IN INTITITIT ${mcharacters.size}")
                selectedCharacterIndex =
                    mcharacters.indexOfFirst { it.character.uuid == me.selectedCharacterID }
                if(mcharacters.isNotEmpty()&&selectedCharacterIndex>=0){
                    selectedCharacterWithStuff =mcharacters[selectedCharacterIndex]
                    Log.d(TAG,"SETS CHARACTER ${selectedCharacterWithStuff} ")

                }
                initialized=true
            }
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
        selectedCharacterWithStuff.setCompletedFakeHabits()
        selectedCharacterWithStuff.setCompletedFakeQuests()
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
            char.inventory.forEach{iDao.save(it.key)}
            //qDao.save(char.quests)
            needsSync=true
        }
    fun save(char: CharacterWithStuff=selectedCharacterWithStuff):Job =
        CoroutineScope(Dispatchers.IO).launch {
            //Log.d(TAG,"FUCKING SAVED ${char.character}" )
//            selectedCharacterWithStuff=char
            ////////////////////////////////////////////////might switch order. this was the way it was
            try {
                uDao.save(char.character)
            }catch (e: Exception){
                Log.d(TAG,"GOTCHA BISH $e")
            }
            updateByCharacterList(char)
            //char.quests.forEach {qDao.update(it)}
            //char.abilities.forEach{aDao.update(it)}
            //char.inventory.forEach{iDao.update(it)}
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
            if (mcharacters.isEmpty()){
                mcharacters = primeCharacters()?:CharacterWithStuff(uDao.getAllCharacters())
            }
            else{ ncharacters = uDao.getAllCharacters()
                //Log.d(TAG,"REPO BUILT, ${ncharacters} IN LIST")
                mcharacters = CharacterWithStuff(ncharacters)}

           //mcharacters.forEach {Log.d(TAG,"REPO BUILT, ${it.toString()} IN LIST")}
            if(selectedCharacterWithStuff.character.name=="DEFAULT_NAME") {
                //11/5/2022 added checking to fix coldstart crash with firstOrNull
               mcharacters.firstOrNull { it.character.name == "ME" }.also{
                   if(it!=null)
                    selectedCharacterWithStuff =it
                }

                selectedCharacterIndex = 0}
                if (selectedCharacterWithStuff.abilities.isEmpty()) {
                    putAbilityOnCharacter(AbilityEntity(title = "Add Custom Ability (click +)"))
                }
                if (selectedCharacterWithStuff.quests.isEmpty()) {
                    putQuestOnCharacter(
                        QuestWithObjectives(
                            QuestEntity(title = "Add A Custom Quest"), listOf(
                                ObjectiveEntity(obj = "click the +")
                            )
                        )
                    )
                }
                if (selectedCharacterWithStuff.inventory.isEmpty()) {

                    putItemOnCharacter(ItemEntity(name = "skeleton"))
                    putItemOnCharacter(ItemEntity(name = "mesh"))
                    putItemOnCharacter(ItemEntity(name = "meat"))
                    putItemOnCharacter(ItemEntity(name = "stuff"))

            }
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


    /*private fun CharacterWithStuff(character: CharacterEntity) : CharacterWithStuff {
        return CharacterWithStuff(character,getAbilitiesbyCharacter(character),getQuestsbyCharacter(character))
    }*/
//    private fun CharacterWithStuff(character: CharacterEntity) : CharacterWithStuff {
//        return CharacterWithStuff(character,getAbilitiesbyCharacter(character),getQuestsbyCharacter(character))
//    }
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
    private fun getCharacterWithStuff(uuid: UUID): CharacterWithStuff {
           val character = uDao.getCharacterEntity(uuid)
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
            Log.d(TAG, "BISH QUESTED")
            CoroutineScope(Dispatchers.IO).launch {
                selectedCharacterWithStuff.character.quests=selectedCharacterWithStuff.character.quests.plus(qDao.save(questEntity))
                //uDao.save(selectedCharacterWithStuff.character)

                save()
            }

        }
    fun putQuestOnCharacter(quest: QuestWithObjectives){
        //var qwo: QuestWithObjectives =
        Log.d(TAG, "BISH QUESTED2")
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
            Log.d(TAG, "BISH Ability")
            save()
            // udao.update(me)
        }
    }

    fun putItemOnCharacter(item: ItemEntity=itemRepository.selectedItem) {
        Log.d(TAG, "BISH item got")
        selectedCharacterWithStuff.character.inventory=selectedCharacterWithStuff.character.inventory.plus(item.iid,item.stackable)

        selectedCharacterWithStuff.inventory= selectedCharacterWithStuff.inventory.plus(item)
        //Log.d("REPO_putItemOnCharacter", "item  is $item in generalRepo putItemOnCharacter")
        CoroutineScope(Dispatchers.IO).launch {
            save()
            // udao.update(me)
        }

    }
    fun fixInventory(){selectedCharacterWithStuff.inventory = buildInventory()}

    fun buildInventory(idToQuant:Map<Long,Int> =selectedCharacterWithStuff.character.inventory): MutableMap<ItemEntity,Int> {
        val items: List<ItemEntity> = itemRepository.getitems().filter{it.iid in idToQuant.keys}
        return items.associateWith { idToQuant[it.iid]?:1 } as MutableMap<ItemEntity, Int>
    }


        //itemRepository.getitems().filter{it.iid in ids}.toTypedArray()

    fun updateCharacterQuests(){
            selectedCharacterWithStuff.quests=getQuestsbyCharacter(selectedCharacterWithStuff.character)
            //Log.d(TAG,"UPDATECHARACTERQUESTS")
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
            uDao.setMe(new_me.user)
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
    suspend fun insertAbility(title:String="ABILITY_DEFAULT", ae: AbilityEntity=AbilityEntity(title = title, author =  getMe().user.uname)):Long{
        var id:Long= ae.aid

        id= withContext(Dispatchers.IO){
            aDao.save(ae)
        }
//        if (id==0L) {
//            Log.d("REPO_insertAbility", "RUH ROH id ==0 ")
//        }else{Log.d("REPO_insertAbility", "id  is $id")}
        needsSync=true
        return id
    }
    suspend fun insertItem(name:String="ITEM_DEFAULT", item: ItemEntity=ItemEntity(name = name, author =  getMe().user.uname)):Long{
        var id:Long= item.iid

        id= withContext(Dispatchers.IO){
            iDao.save(item)
        }

//        if (id==0L) {
//            Log.d("REPO_insertItem", "RUH ROH id ==0 ")
//        }else{Log.d("REPO_insertItem", "id  is $id")}

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
    fun computeForCalendar(startDate: LocalDate=LocalDate.of(2022,10,1), endDate:LocalDate=LocalDate.of(2023,10,1),completedQuests:Boolean = true,habits: Boolean = true, quests:Boolean = true):Map<LocalDate,List<String>>{//want date by display
        val result : MutableMap<LocalDate,List<String>> = mutableMapOf()

        getCompletedQuests(startDate,endDate,result)
            .also {//return is passed down
                Log.d(TAG, "result is now $it \n stuff is now  ${selectedCharacterWithStuff.character.habitTracker} ${selectedCharacterWithStuff.character.completedQuests} ${selectedCharacterWithStuff.character.activeQuests}")
                getCompletedHabits(startDate,endDate,m = it).also{it2->
                    Log.d(TAG, "result is now $it2")
                    return getActiveQuests(startDate,endDate,m = it2)
                }
            }

    }

    private fun getActiveQuests(
        startDate: LocalDate,
        endDate: LocalDate,
        m: MutableMap<LocalDate, List<String>>
    ): MutableMap<LocalDate, List<String>> {
        val valid =  selectedCharacterWithStuff.quests.filter{
            val q = parse(it.quest.targetDateTime)
            Log.d("SPAM","$q ${q.isAfter(startDate.atStartOfDay())} ${q.isBefore(endDate.atTime(23,59))}")
            q.isAfter(startDate.atStartOfDay()) && q.isBefore(endDate.atTime(23,59))

        }
        /* val numbersMaps = (0 until valid.size).map { i ->
             i+1 to numbers[it]
         }*/

        //val result : MutableMap<LocalDate,List<String>> = mutableMapOf()
        valid.forEach{//now have moments in target
            //want sorted like date x strings


            val atDate= parse(it.quest.targetDateTime).toLocalDate()
            val displayString = setCalendarDisplayString("A",it)
            m[atDate]=m[atDate]?.plus(displayString)?:listOf(displayString)
        }
        Log.d(TAG, "getActiveQuests result is now $m , valid is now $valid")
        return m
    }

    fun getCompletedQuests(startDate: LocalDate, endDate:LocalDate,result:MutableMap<LocalDate,List<String>>): MutableMap<LocalDate,List<String>> {

        val valid =  selectedCharacterWithStuff.character.completedQuests.filter{
            //val formatter =
            //val formatted = current.format(formatter)
            val q = parse(it.value.first)
            Log.d("SPAM","$q ${q.isAfter(startDate.atStartOfDay())} ${q.isBefore(endDate.atTime(23,59))}")
            q.isAfter(startDate.atStartOfDay()) && q.isBefore(endDate.atTime(23,59))


        }
        Log.d(TAG,"Completed quests are ${selectedCharacterWithStuff.character.completedQuests} ,valid = $valid" )
       /* val numbersMaps = (0 until valid.size).map { i ->
            i+1 to numbers[it]
        }*/

        //val result : MutableMap<LocalDate,List<String>> = mutableMapOf()
        valid.forEach{//now have moments in target
            //want sorted like date x strings
            val atDate= parse(it.value.first).toLocalDate()
            val displayString = setCalendarDisplayString(it.value.second)
            result[atDate]=result[atDate]?.plus(displayString)?:listOf(displayString)
        }
        return result
    }

    private fun setCalendarDisplayString(s: String="",q: QuestWithObjectives?=null): String {

        if (q!=null){
            return s+q.quest.describe()
        }
        return s
    }
    fun getCompletedHabits(startDate: LocalDate, endDate:LocalDate, m: MutableMap<LocalDate,List<String>>): MutableMap<LocalDate,List<String>> {

        selectedCharacterWithStuff.character.habitTracker.forEach{
            var q:LocalDate
                it.value.first.forEach{it2->
                    q=parse(it2).toLocalDate()
                    val displayString = setCalendarDisplayString(it.value.second)
                    //m.put(q.toLocalDate(),it.value.second)
                    m[q]=m[q]?.plus(displayString)?:listOf(displayString)
                    Log.d("SPAM","$q ${q.isAfter(startDate)} ${q.isBefore(endDate)}")
                }


            //q.isAfter(startDate.atStartOfDay()) && q.isBefore(endDate.atTime(23,59))

        }
        /* val numbersMaps = (0 until valid.size).map { i ->
             i+1 to numbers[it]
         }*/

       /* //val result : MutableMap<LocalDate,List<String>> = mutableMapOf()
        valid.forEach{//now have moments in target
            //want sorted like date x strings
            //m[parse(it.value.first).toLocalDate()]?.plus(setCalendarDisplayString(it.value.second))
            val atDate= parse(it.value.first).toLocalDate()

            m[atDate]=m[atDate]?.plus(displayString)?:listOf(displayString)
        }*/
        Log.d(TAG, "HABITS result is now $m ")
        return m
    }
    fun updateByCharacterList(selectedCharacter: CharacterWithStuff=selectedCharacterWithStuff) {

        if (selectedCharacterIndex>=0) {
            val old = mcharacters[selectedCharacterIndex].toString()
            mcharacters = mcharacters.toMutableList().apply {
                this[selectedCharacterIndex] = selectedCharacter
            }
        }
        else{selectedCharacterIndex=mcharacters.size}//this might break. find which character, set to that index?
        //Log.d("REPO", "mcharacters updated index $selectedCharacterIndex with $selectedCharacter ================================from --------------OLD--------------- $old")
    }

    fun deleteCharacter(character: CharacterWithStuff) {
        mcharacters=mcharacters.filter { it.character.uuid != character.character.uuid }
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

fun MutableMap<ItemEntity, Int>.plus(item: ItemEntity): MutableMap<ItemEntity, Int> {
    //in already vs absent
    //if stackable
    if (item.stackable){
        //present + stackable
        //absent + stackable
        println("increments")
        this[item] = (this[item]?:0) + 1
    }else{
        //present + non stackable
        if(this.containsKey(item)){
            println("present already")
        }else{ //absent + non stackable
            println("absent")
            this[item] = 1

        }
    }
    return this
}
fun Map<Long, Int>.plus(iid: Long,isStackable:Boolean): Map<Long, Int> {
    //gets around empty map cast as mutablemap err
    val result:MutableMap<Long,Int> = if(this.isEmpty()){ mutableMapOf() }else{ this as MutableMap<Long, Int>}
    if (isStackable){
        //present + stackable
        //absent + stackable
        result[iid] = (result[iid]?:0) + 1
    }else{
        //present + non stackable
        if(this.containsKey(iid)){
            println("present already")
        }else{ //absent + non stackable
            println("absent")
            result[iid] = 1

        }
    }
    return result
}
