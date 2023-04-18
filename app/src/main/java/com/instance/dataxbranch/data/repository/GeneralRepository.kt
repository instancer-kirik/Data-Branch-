package com.instance.dataxbranch.data.repository

//import android.util.Log

//import com.instance.dataxbranch.data.firestore.CloudUser

import android.app.Application
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.DayStatus
import com.instance.dataxbranch.data.EventType
import com.instance.dataxbranch.data.daos.AbilityDao
import com.instance.dataxbranch.data.daos.ItemDao
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.*
import com.instance.dataxbranch.data.local.CharacterWithStuff
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.domain.getNow
import com.instance.dataxbranch.domain.parse
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.ui.calendar.custom.DayData
import com.instance.dataxbranch.ui.calendar.custom.Event
import com.instance.dataxbranch.ui.calendar.custom.DayOf12dWeek
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Singleton
import kotlin.properties.Delegates


@Singleton
class GeneralRepository(application: Application, db: AppDatabase,
                        val questsRepository: LocalQuestsRepository,
                        val itemRepository: ItemRepository,
                        val notes: NoteRepository
) {

    var needsSync: Boolean = true
    var initialized: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        Log.d("TAG","$property  is $newValue ...was $oldValue")
    }
   // private var cachedUsers: List<CloudUser> = listOf()
    val aDao: AbilityDao = db.abilityDao()
    val uDao: UserDao = db.userDao()
    val qDao: QuestDao = db.questDao()
    val iDao: ItemDao = db.itemDao()
    var mabilities: List<AbilityEntity> = listOf()
    var mcharacters: List<CharacterWithStuff> by Delegates.observable(listOf()) { property, oldValue, newValue ->
        Log.d("TAG"," MCHARACTERS  $property and  oldval len=${oldValue.size}, ${oldValue.toString()} and newval len=${newValue.size}, ${newValue.toString()}")
        //if (newValue.iid ==0L){ selectedItem =oldValue}// this prevents resetting to id=0 bug
    }

    var ncharacters:List<CharacterEntity> = listOf()
    //var mDayStatuses: MutableMap<LocalDate,String> = mutableMapOf()
    private lateinit var me: User
    lateinit var selectedAE: AbilityEntity
    val totalDates: MutableMap<LocalDate,DayOf12dWeek> = mutableMapOf()
    var mcalendar:MutableMap<LocalDate,DayData> = mutableMapOf()
    //var selectedItem: ItemEntity = ItemEntity()
    var selectedCharacterIndex by Delegates.observable(0) { property, oldValue, newValue ->
        Log.d(TAG,"SELECTED $property IS NOW $newValue")
    }
    var selectedCharacterWithStuff by Delegates.observable(CharacterWithStuff(CharacterEntity())) { property, oldValue, newValue ->
        Log.d(TAG,"SELECTED_CHARACTER CHANGE $property IS NOW $newValue")
    }
    private var me_container = UserWithAbilities(User(), mabilities)

    init {
        sync()
        calendarPreprocessing()
        initialized= true
        //CoroutineScope(Dispatchers.IO).launch {}//on conflict abort
    }

    private fun calendarPreprocessing() {// sets the correct date to day of week

        var start = LocalDate.of(2022,1,1)
        val end = LocalDate.now().plusYears(5) // this is the end of the calendar adds 5 years to current date. should be fine for future
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






            uDao.prime(CharacterEntity(name ="ME"))//should keep abilities maybe
            uDao.prime( CharacterEntity(name ="PRIME1") )
            uDao.prime( CharacterEntity(name ="PRIME2"))

            //  mcharacters.forEach{uDao.prime()}  }
        } else {
            // table is not empty
            Log.d("GeneralRepo -- primeCharacters", "NO OP ")
        }
        Log.d("GeneralRepo -- primeCharacters", "primed? "+uDao.isPrimed())
        return CharacterWithStuff(uDao.getAllCharacters())
}
    fun sync(andItems:Boolean = false, andQuests:Boolean = false){
        Log.d(TAG,"SYNCING")
//        fixInventory()
        fixInventory()
        //Log.d("REPO","SYNC CALLED with ${needsSync}, andItems=$andItems, andQuests = $andQuests")
        if (needsSync){

        CoroutineScope(Dispatchers.IO).launch {//this might cause issues with data not being loaded fast enough
            uDao.prime(User())

            me = uDao.getMe()
            me.initflag = true
            Log.d(TAG,"Gotted ${me.selectedCharacterID} ..id")
            mcharacters = CharacterWithStuff(uDao.getAllCharacters())



            if (!initialized) { Log.d(TAG,"IN INIT sync characters ${mcharacters.size}")//should only be called once
                initialized=true

                selectedCharacterIndex = mcharacters.indexOfFirst { it.character.uuid == me.selectedCharacterID }


                if(mcharacters.isNotEmpty()&&selectedCharacterIndex>=0){
                    selectedCharacterWithStuff =mcharacters[selectedCharacterIndex]
                }

                mabilities = aDao.getAbilities()

                getMeWithAbilities()
                getAllCharacters()
                characterSetup()

            }

        }
            if(andItems){
                itemRepository.sync()
                fixInventory()}

            if(andQuests){
                questsRepository.refresh()
                updateCharacterQuests()
            }
            populateFakeQuests()
            populateFakeHabits()
            CoroutineScope(Dispatchers.IO).launch {
                computeForCalendar()
            }
    }//OUTSIDE OF THIS BRACKET SUBJECT TO MULTIPLE CALLS WHEN NOT NEEDED


        needsSync=false
    }
    fun populateFakeQuests(){
        Log.d("GeneralRepo","POPULATE FAKE QUESTS")
//        setCompletedFakeQuests()
        val quest1 = QuestWithObjectives(QuestEntity(title = "questxx1", isHabit = false,/* uuid = "000000000000001"*/),listOf(
            ObjectiveEntity( obj="quest1obj1"),ObjectiveEntity( obj="quest1obj2")
        ))
        val quest2 = QuestWithObjectives(QuestEntity(title = "questxx2", isHabit = false, /*uuid = "000000000000002"*/),listOf(
            ObjectiveEntity( obj="quest2obj1"),ObjectiveEntity( obj="quest2obj2")
        ))
        questsRepository.insertQWOtomquests(quest1)
        questsRepository.insertQWOtomquests(quest2)
        selectedCharacterWithStuff.character.completedQuests = mapOf(
            quest1.quest.uuid to Pair(getNow(),quest1.quest.describe()),
            quest2.quest.uuid to Pair(LocalDateTime.of(2022,12,9,12,11).toString(),quest2.quest.describe())
            /* "16S" to Pair(LocalDateTime.now().toString(),"T"),
             "17S" to Pair (LocalDateTime.of(2022,,21,,211).toString(),"W")*/)//by id:Title (Or a recognizable String stamp of quest completion with time, xp, etc)
    }
    fun populateFakeHabits(){ //first makes habits
        Log.d("GeneralRepo","POPULATE FAKE HABITS")
        val habit1 = QuestWithObjectives(QuestEntity(title = "habitxx1", isHabit = true,/*uuid = "000000000000011"*/),listOf())
        val habit2 = QuestWithObjectives(QuestEntity(title = "habitxx2", isHabit = true, /*uuid = "000000000000012"*/),listOf())
        questsRepository.insertQWOtomquests(habit1)
        questsRepository.insertQWOtomquests(habit2)
        selectedCharacterWithStuff.character.habitTracker = mapOf(habit1.quest.uuid to Pair(
            listOf(
                getNow(),
                LocalDateTime.of(2022,12,1,12,11).toString(),
                LocalDateTime.of(2022,12,2,12,11).toString(),
                LocalDateTime.of(2022,12,3,12,11).toString(),
                LocalDateTime.of(2022,12,4,12,11).toString(),
                LocalDateTime.of(2022,12,5,12,11).toString(),
                LocalDateTime.of(2022,12,6,12,11).toString(),
                LocalDateTime.of(2022,12,7,12,11).toString(),
                LocalDateTime.of(2022,12,8,12,11).toString(),
                LocalDateTime.of(2022,12,9,12,11).toString())
            ,habit1.quest.describe()),
            habit2.quest.uuid to Pair(listOf(
                getNow(),
                LocalDateTime.of(2022,12,1,12,11).toString(),
                LocalDateTime.of(2022,12,2,12,11).toString(),
                LocalDateTime.of(2022,12,3,12,11).toString(),
                LocalDateTime.of(2022,12,4,12,11).toString(),
                LocalDateTime.of(2022,12,9,12,11).toString(),
                LocalDateTime.of(2022,12,12,12,11).toString(),
                LocalDateTime.of(2022,12,5,12,11).toString()),habit2.quest.describe()))//by id:Title (Or a recognizable String stamp of quest completion with time, xp, etc)
    }
    fun refresh(): Job =

        CoroutineScope(Dispatchers.IO).launch {
            mabilities =aDao.getAbilities()
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
                Log.d(TAG,"GOTCHA BISH $e ------------------------------------- in save@GeneralRepo")
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

        }

fun characterSetup(char: CharacterWithStuff =selectedCharacterWithStuff){
    if (char.abilities.isEmpty()) {
        putAbilityOnCharacter(AbilityEntity(title = "Add Custom Ability (click +)"))
    }
    if (char.quests.isEmpty()) {
        putQuestOnCharacter(

                QuestEntity(title = "Add A Custom Quest")

        )
    }
    if (char.inventory.isEmpty()) {
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
            return qwos.filter{it.quest.uuid in character.quests}.toTypedArray()
        }
    fun getAbilitiesbyCharacter(character:CharacterEntity):List<AbilityEntity>{
       // val qwos: Array<QuestWithObjectives> =questsRepository.getQuests()
        return mabilities.filter{it.aid in character.abilities}
    }
    fun putQuestOnCharacter(questEntity: QuestEntity,char: CharacterWithStuff=selectedCharacterWithStuff): Job = CoroutineScope(Dispatchers.IO).launch {
            //var qwo: QuestWithObjectives =
            Log.d(TAG, "BISH QUESTED")
      //      CoroutineScope(Dispatchers.IO).launch {ObjectiveEntity(obj = "Objective 1")
        char.quests.plus(QuestWithObjectives(questEntity ,listOf()))
        qDao.save(questEntity)

                char.character.quests=char.character.quests.plus(questEntity.uuid)
                //uDao.updateCharacter(char.character)
                //uDao.save(selectedCharacterWithStuff.character)

                save(char)


        }
    /*fun putQuestOnCharacter(quest: QuestWithObjectives,char : CharacterWithStuff=selectedCharacterWithStuff){
        //var qwo: QuestWithObjectives =
        Log.d(TAG, "BISH QUESTED2")
        CoroutineScope(Dispatchers.IO).launch {
            qDao.save(quest)
            char.character.quests=char.character.quests.plus(quest.quest.uuid)
            //uDao.save(selectedCharacterWithStuff.character)

            save(char)
        }

    }*/
    fun putAbilityOnCharacter(ae: AbilityEntity, char : CharacterWithStuff=selectedCharacterWithStuff) {
        char.character.abilities=char.character.abilities.plus(ae.aid)
        char.abilities= char.abilities.plus(ae)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "BISH Ability")
            save(char)
            // udao.update(me)
        }
    }

    fun putItemOnCharacter(item: ItemEntity=itemRepository.selectedItem, char: CharacterWithStuff=selectedCharacterWithStuff){
        Log.d(TAG, "BISH item got")
        char.character.inventory=char.character.inventory.plus(item.iid,item.stackable)

        char.inventory= char.inventory.plus(item)
        //Log.d("REPO_putItemOnCharacter", "item  is $item in generalRepo putItemOnCharacter")
        CoroutineScope(Dispatchers.IO).launch {
            save(char)
            // udao.update(me)
        }

    }
    fun fixInventory(char: CharacterWithStuff=selectedCharacterWithStuff){char.inventory = buildInventory()}

    fun buildInventory(idToQuant:Map<Long,Int> =selectedCharacterWithStuff.character.inventory): MutableMap<ItemEntity,Int> {
        val items: List<ItemEntity> = itemRepository.getitems().filter{it.iid in idToQuant.keys}
        return items.associateWith { idToQuant[it.iid]?:1 } as MutableMap<ItemEntity, Int>
    }


        //itemRepository.getitems().filter{it.iid in ids}.toTypedArray()

    fun updateCharacterQuests(char: CharacterWithStuff=selectedCharacterWithStuff){
            char.quests=getQuestsbyCharacter(char.character)
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
    */  /*.also {//return is passed down
            Log.d(TAG, "result is now $it \n stuff is now  ${selectedCharacterWithStuff.character.habitTracker} ${selectedCharacterWithStuff.character.completedQuests} ${selectedCharacterWithStuff.character.activeQuests}")
            getCompletedHabits(startDate,endDate,m = it).also{it2->
                Log.d(TAG, "result is now $it2")
                 getActiveQuests(startDate,endDate,m = it2).also{it3->
                     Log.d(TAG, "result is now $it3")
                     return getDayStatuses(startDate,endDate,m = it3)
                 }
            }
        }*/


//  val result : MutableMap<LocalDate,DayData> = mutableMapOf()


/*
    fun getCompletedQuests(date: LocalDate, dayData: DayData) {
        // Get the completed quests that have a target date that matches the current date.
        val valid = selectedCharacterWithStuff.character.completedQuests.filter {
            val q = parse(it.value.first)
            q.toLocalDate() == date
        }

        valid.forEach {
            val displayData = Event(
                uuid = it.key,
                type = EventType.QUEST,
                title = it.value.second,
                description = it.value.second
            )
            dayData.events.add(displayData)
        }
    }*/
    fun computeForCalendar(
        startDate: LocalDate=LocalDate.of(2022,10,1),
        endDate:LocalDate=LocalDate.of(2023,10,1),
        completedQuests:Boolean = true,
        habits: Boolean = true,
        quests:Boolean = true):
       Boolean/* Map<LocalDate,DayData>*/{// want status, title, description, type, and id
        //Does Completed Quests, Habits, and Active Quests
    try {
        getCompletedQuests(startDate, endDate)
        getActiveQuests(startDate, endDate)
        getCompletedHabits(startDate, endDate)
        getDayStatuses(startDate, endDate)
        getEventsFromCharacter(startDate, endDate)
        getNotesForCalendarFromCharacter(startDate, endDate)
        //events from User?
    }catch (e:Exception){
        Log.d("REPO_computeForCalendar", "ERROR: $e")
        return false
    }
        return true
    }

    private fun getEventsFromCharacter(startDate: LocalDate, endDate: LocalDate) {


            val valid =  selectedCharacterWithStuff.character.events.filter{
                //Map<ID,Pair<date,slug>>
                val q = parse(it.value.first)
                //Log.d("SPAM1","$q ${q.isAfter(startDate.atStartOfDay())} ${q.isBefore(endDate.atTime(23,59))}")
                q.isAfter(startDate.atStartOfDay()) && q.isBefore(endDate.atTime(23,59))


            }
            Log.d(TAG,"Events are ${selectedCharacterWithStuff.character.completedQuests} ,valid = $valid" )

            valid.forEach{
                //Map<ID,Pair<date,slug>>
                val atDate= parse(it.value.first).toLocalDate()
                mcalendarAddEvent( Event( uuid=it.key,type =EventType.DEFAULT,text = it.value.second),
                    atDate)
            }
            return //result

    }
    private fun getNotesForCalendarFromCharacter(startDate: LocalDate, endDate: LocalDate) {


        val valid =  selectedCharacterWithStuff.character.notes.filter{
            //Map<ID,Pair<date,slug>>
            val q = parse(it.value.first)
            //Log.d("SPAM1","$q ${q.isAfter(startDate.atStartOfDay())} ${q.isBefore(endDate.atTime(23,59))}")
            q.isAfter(startDate.atStartOfDay()) && q.isBefore(endDate.atTime(23,59))


        }
        Log.d(TAG,"Events are ${selectedCharacterWithStuff.character.completedQuests} ,valid = $valid" )

        valid.forEach{
            //Map<ID,Pair<date,slug>>
            val atDate= parse(it.value.first).toLocalDate()
            mcalendarAddEvent( Event( uuid=it.key,type =EventType.NOTE,text = it.value.second),
                atDate)
        }
        return //result

    }
    fun getCalendarStuff(): Map<LocalDate,DayData> = mcalendar



    private fun getActiveQuests(
        startDate: LocalDate,
        endDate: LocalDate,
        /*m: MutableMap<LocalDate, DayData>
    ): MutableMap<LocalDate, DayData> */){
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
            val atDate= parse(it.quest.targetDateTime).toLocalDate()
            mcalendarAddEvent(Event(it),atDate)
        }
        Log.d(TAG, "getActiveQuests result is now $mcalendar , valid is now $valid")
        return// mcalendar
    }

    private fun getCompletedQuests(startDate: LocalDate, endDate:LocalDate,/*result:MutableMap<LocalDate,DayData>): MutableMap<LocalDate,DayData> */){

        val valid =  selectedCharacterWithStuff.character.completedQuests.filter{
            //Map<ID,Pair<date,slug>>
            val q = parse(it.value.first)
            Log.d("SPAM1","$q ${q.isAfter(startDate.atStartOfDay())} ${q.isBefore(endDate.atTime(23,59))}")
            q.isAfter(startDate.atStartOfDay()) && q.isBefore(endDate.atTime(23,59))


        }
        Log.d(TAG,"Completed quests are ${selectedCharacterWithStuff.character.completedQuests} ,valid = $valid" )
       /* val numbersMaps = (0 until valid.size).map { i ->
            i+1 to numbers[it]
        }*/

        //val result : MutableMap<LocalDate,List<String>> = mutableMapOf()
        valid.forEach{
            //Map<ID,Pair<date,slug>>
            val atDate= parse(it.value.first).toLocalDate()
            mcalendarAddEvent( Event( uuid=it.key,type =EventType.QUEST,text = it.value.second),
                atDate)



        }
        return //result
    }

    private fun getCompletedHabits(startDate: LocalDate, endDate:LocalDate,/* m: MutableMap<LocalDate,DayData>): MutableMap<LocalDate,DayData>*/) {

        selectedCharacterWithStuff.character.habitTracker.forEach{
            var q:LocalDate
            it.value.first.forEach{it2->
                q=parse(it2).toLocalDate()
                mcalendarAddEvent(Event( uuid=it.key,type = EventType.HABIT,text = it.value.second)
                    ,q)
                Log.d("SPAM2","$q ${q.isAfter(startDate)} ${q.isBefore(endDate)}")
            }
        }
        Log.d(TAG, "HABITS result is now $mcalendar ")
        return //m
    }

    /*fun getDayStatuses1(startDate: LocalDate, endDate:LocalDate,*//* m: MutableMap<LocalDate,DayData>): MutableMap<LocalDate,DayData>*//*) {

        me_container.user.dayStatuses.forEach{

            val q:LocalDate = parse(it.key).toLocalDate()
                //val displayData = Event( it.key,EventType.HABIT,it.value.second)
            if(mcalendar[q]!=null){//if DayData mapping exists
                //modifies daystatus, keeps displayData
                mcalendar[q]= mcalendar[q]?.copy(newStatus =DayStatus.fromStringOrDefault(it.value.first))?: DayData(color = Color.Black, DayStatus.fromStringOrDefault(it.value.first), listOf())
                //status = DayStatus.fromStringOrDefault(it.value.first)) ?: DayData(color = Color.White,  DayStatus.fromStringOrDefault(it.value.first), listOf())

            }else{//if DayData mapping does not exist
                mcalendar[q]= DayData(color = Color.Black, DayStatus.fromStringOrDefault(it.value.first), listOf())
            }
            Log.d("SPAM2","$q ${q.isAfter(startDate)} ${q.isBefore(endDate)}")

        }
        Log.d(TAG, "Status result is now $mcalendar ")
        return //mcalendar
    }*/
    private fun getDayStatuses(startDate: LocalDate, endDate: LocalDate) {
        me_container.user.dayStatuses
            .filter {
                val q: LocalDate = parse(it.key).toLocalDate()
                q.isAfter(startDate) && q.isBefore(endDate)
            }
            .forEach {
                val q: LocalDate = parse(it.key).toLocalDate()
                mcalendar[q] =
                    mcalendar[q]?.copy(newStatus = it.value.first)
                    ?: DayData(status = it.value.first) //DayStatus.fromStringOrDefault(it.value.first)
            }
    }
    /* val numbersMaps = (0 until valid.size).map { i ->
                i+1 to numbers[it]
            }*/
    private fun updateByCharacterList(selectedCharacter: CharacterWithStuff=selectedCharacterWithStuff) {
//takes in selected character.  If it's not the same as the current one, it updates the current one
try{
        //switches to mutable list? and updates selected character.
        if (selectedCharacterIndex>=0) {

            val old = mcharacters[selectedCharacterIndex].toString()
            mcharacters = mcharacters.toMutableList().apply {
                this[selectedCharacterIndex] = selectedCharacter
            }
        }
        else{selectedCharacterIndex=mcharacters.size-1}//this might break. find which character, set to that index? added -1
        //Log.d("REPO", "mcharacters updated index $selectedCharacterIndex with $selectedCharacter ================================from --------------OLD--------------- $old")
    }catch (e: Exception){
        Log.d("REPO", "mcharacters updated index $selectedCharacterIndex with $selectedCharacter ================================from --------------OLD--------------- $e")

    }
    }
/*fun mcalendarAddEvent(value:Event,atDate:LocalDate){

    if (mcalendar[atDate] != null) {
        // Add the displayData object to the existing DayData object's events list
        mcalendar[atDate]?.events = mcalendar[atDate]?.events?.plus(value) ?: listOf(value)
    } else {
        // Create a new DayData object and add the displayData object to its events list
        mcalendar[atDate] = DayData(
            color = Color.Black,
            status = DayStatus.DEFAULT,
            events = listOf(value)
        )
    }
}*/
    fun mcalendarAddEvent(value: Event, atDate: LocalDate) {
        val existingDayData = mcalendar[atDate]
        if (existingDayData != null) {
            // Check if the event's ID is already present in the existing DayData object's events list
            if (existingDayData.events.none { it.uuid == value.uuid }) {
                // Add the event to the existing DayData object's events list
                mcalendar[atDate] = existingDayData.copy(eventList = existingDayData.events + value)
            }
        } else {
            // Create a new DayData object and add the event to its events list
            mcalendar[atDate] = DayData(
                color = Color.Black,
                status = DayStatus.DEFAULT.toString(),
                events = listOf(value)
            )
        }
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

    fun setDayData(dayData:  DayData, date: LocalDate):DayData {

        //val dayStatus = DayStatus.fromStringOrDefault(option)
        //val dayData = DayData(color,dayStatus, listOf())
        mcalendar[date] = dayData
//val dayStatusEntity = DayStatusEntity(date.toString(), option)
        me_container.user.setDateStatus(dayData = dayData, date = date.toString() )
        //val dayData = mCalendarData[date]
        //dayData?.status = DayStatus.valueOf(option)
        //mCalendarData[date] = dayData!!
        //Log.d("REPO", "mCalendarData updated $date with $option")
        return dayData
    }

    fun getDayStatus(date: LocalDate?): Pair<String, String> {
        return me_container.user.dayStatuses[date.toString()]?:Pair(DayStatus.DEFAULT.toString(),Color.Transparent.toString())
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
