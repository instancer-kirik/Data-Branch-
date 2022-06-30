package com.instance.dataxbranch.data.local

import android.app.Application

import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.daos.AbilityDao

import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.AbilityEntity

import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.AppDatabase
import kotlinx.coroutines.*

import javax.inject.Singleton
@Singleton
class GeneralRepository(application: Application, db: AppDatabase) {

    val aDao: AbilityDao=db.abilityDao()
    val uDao: UserDao=db.userDao()
    var mabilities: List<AbilityEntity> = listOf(AbilityEntity())
    private lateinit var me:User
    lateinit var selectedAE: AbilityEntity

    private var me_container= UserWithAbilities(User(),mabilities)
    init {
        CoroutineScope(Dispatchers.IO).launch {//this might cause issues with data not being loaded fast enough
            uDao.prime(User())
            me = uDao.getMe()
            mabilities = aDao.getAbilites()
            getMeWithAbilities()
        }
        //CoroutineScope(Dispatchers.IO).launch {}//on conflict abort
    }
    private fun getMeWithAbilities(){
        CoroutineScope(Dispatchers.IO).launch {
            //.d(TAG, "in GENERALREPO with $me")
            me_container =
                if (me!=null) {me.initflag=true
                    UserWithAbilities(me, mabilities)

                } else {
                    UserWithAbilities(User(), mabilities)
                }

        }
    }
    fun save(me: User): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.save(me)
        }
    fun sync(): Job = CoroutineScope(Dispatchers.IO).launch {
        aDao.update(selectedAE)
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
    /*fun sync(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            uDao.save(me_user)

        }*/
}