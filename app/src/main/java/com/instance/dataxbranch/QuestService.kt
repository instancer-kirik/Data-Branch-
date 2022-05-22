package com.instance.dataxbranch

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore

object QuestService {
    private val db = FirebaseFirestore.getInstance()
    fun getQuests(quests: SnapshotStateList<Quest>){
        db.collection("quests").get().addOnSuccessListener {
            quests.updateList(it.toObjects(Quest::class.java))
        }.addOnFailureListener{
            quests.updateList(listOf())
        }
    }
}
/*public static void log(int iLogLevel, String sRequest, String sData, Context ctx) {
    if(iLogLevel > 0) {

        Intent intent = new Intent(ctx, LogService.class);
        intent1.putExtra("UPDATE_MAIN_ACTIVITY_VIEW", "UPDATE_MAIN_ACTIVITY_VIEW");
        ctx.startService(intent);
    }
}*/