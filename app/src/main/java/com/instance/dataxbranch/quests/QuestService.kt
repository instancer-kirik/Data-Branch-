package com.instance.dataxbranch.quests

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.repository.LocalQuestsRepository
import com.instance.dataxbranch.updateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
object QuestService {

    fun getQuests(quests: SnapshotStateList<Quest>){
        FirebaseFirestore.getInstance().collection("quests").get().addOnSuccessListener {
            quests.updateList(it.toObjects(Quest::class.java))
        }.addOnFailureListener{
            quests.updateList(listOf())
        }
    }
   fun getLocalQuests(questsRepository: LocalQuestsRepository):Array<QuestWithObjectives>{
       return questsRepository.getQuests()
   }
fun addQuestEntity(questEntity: QuestEntity, localQuestsRepository: LocalQuestsRepository)=
        CoroutineScope(Dispatchers.IO).launch {localQuestsRepository.insertQuestEntity(questEntity) }

}
/*public static void log(int iLogLevel, String sRequest, String sData, Context ctx) {
    if(iLogLevel > 0) {

        Intent intent = new Intent(ctx, LogService.class);
        intent1.putExtra("UPDATE_MAIN_ACTIVITY_VIEW", "UPDATE_MAIN_ACTIVITY_VIEW");
        ctx.startService(intent);
    }
    }
 */