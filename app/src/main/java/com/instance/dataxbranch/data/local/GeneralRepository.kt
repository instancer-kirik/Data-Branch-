package com.instance.dataxbranch.data.local

import android.app.Application

import com.instance.dataxbranch.data.daos.AbilityDao

import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.AbilityEntity

import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.entities.CharacterEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.firestore.FirestoreUser
import com.instance.dataxbranch.data.firestore.QuestsRepository
import com.instance.dataxbranch.quests.QuestWithObjectives
import kotlinx.coroutines.*

import javax.inject.Singleton
@Singleton
class GeneralRepository(application: Application, db: AppDatabase,questsRepository: LocalQuestsRepository) {

    private var cachedUsers: List<FirestoreUser> = listOf()
    val aDao: AbilityDao=db.abilityDao()
    val uDao: UserDao=db.userDao()
    var mabilities: List<AbilityEntity> = listOf(AbilityEntity())
    var mcharacters: List<CharacterWithStuff> = listOf(
        CharacterWithStuff(
            CharacterEntity(uname="PRIME1"),
            listOf(AbilityEntity(title = "APRIME1")),
            arrayOf(QuestWithObjectives(QuestEntity(title="APRIME1") ,listOf()))
        ),
        CharacterWithStuff(
            CharacterEntity(uname="PRIME2"),
            listOf(AbilityEntity(title = "APRIME2")),
            arrayOf(QuestWithObjectives(QuestEntity(title="APRIME2") ,listOf()))
        ))

    private lateinit var me:User
    lateinit var selectedAE: AbilityEntity
    lateinit var selectedCharacterWithStuff: CharacterWithStuff
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
    fun syncAE(): Job = CoroutineScope(Dispatchers.IO).launch {
        aDao.update(selectedAE)


    }
    fun syncAE(ae:AbilityEntity): Job = CoroutineScope(Dispatchers.IO).launch {
        aDao.update(ae)


    }
    fun getAllCharacters():Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.getAllCharacters().forEach{
                mcharacters=mcharacters.plus(CharacterWithStuff(it))

            }
        }

    private fun CharacterWithStuff(character: CharacterEntity) :CharacterWithStuff{
        return CharacterWithStuff(character,mabilities,arrayOf())
    }

    fun getACharacter(id:Long,questsRepository:LocalQuestsRepository ):Job =
        CoroutineScope(Dispatchers.IO).launch {
            selectedCharacterWithStuff=getCharacterWithStuff(id,questsRepository)
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
     fun getCharacterWithStuff(id: Long, questsRepository: LocalQuestsRepository): CharacterWithStuff {
        //var abilities: List<AbilityEntity> =listOf()
        var qwos: Array<QuestWithObjectives> =questsRepository.getQuests()
           val character = uDao.getCharacterEntity(id)
            //character.abilities.forEach{ abilities=abilities.plus(aDao.getAbilitesByaid(it))}
           // abilities =
            //character.quests.forEach{qwos = qwos.plus(questsRepository.questById(it))}
           // qwos =
           //val objectives: List<ObjectiveEntity> = getObjectiveEntityList(id)
           //val qwo = QuestWithObjectives(quest,objectives)
           return CharacterWithStuff(character,
               mabilities.filter{it.aid in character.abilities},
               qwos.filter{it.quest.id in character.quests}.toTypedArray())
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
    fun insertAbility(title: String): Job =
        CoroutineScope(Dispatchers.IO).launch {
            aDao.insert(AbilityEntity(title=title,author=me_container.user.uname))
        }

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