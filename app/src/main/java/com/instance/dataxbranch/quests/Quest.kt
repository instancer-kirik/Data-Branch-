package com.instance.dataxbranch.quests

import android.os.Build
import com.instance.dataxbranch.data.QuestWithObjectives
import com.instance.dataxbranch.data.daos.QuestDao
import com.instance.dataxbranch.data.entities.ObjectiveEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import com.squareup.moshi.JsonClass
//import androidx.databinding.BaseObservable
//import androidx.databinding.Bindable

import java.time.LocalDateTime

/*
            GONNA GO WITH THIS IS CLIENT SIDE FOR NOW since questentity is on db
should have: primary clear condition
            secondary objective(s)
            stepped objectives before primary
            fail conditions?
            rewards
            time
            experience
            is active? player needs list of active quests. make sure to set behavior to change both on set/abandon/complete/fail
            verify by questgiver/ moderator


 */

/*
on editing one must solve nulls and defaults
obj,

//Kotlin code
interface EventManagementInterface {
    fun someMethod()
}

class EventManager {

    companion object FirebaseManager : EventManagementInterface {

        override fun someMethod() {

        }
    }
}
(args: array<String>)
Note that the compiler only uses the properties defined inside the primary constructor for the automatically generated functions.
 To exclude a property from the generated implementations, declare it inside the class body
 */
/*
class User(firstName: String, lastName: String) : BaseObservable() {
    private var firstName: String
    private var lastName: String
    @Bindable
    fun getFirstName(): String {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
        notifyPropertyChanged(BR.firstName)
    }

    @Bindable
    fun getLastName(): String {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
        notifyPropertyChanged(BR.lastName)
    }

    init {
        this.firstName = firstName
        this.lastName = lastName
    }
}*/

class Quest(val qid: String= "-1",
            var title: String = "QuestTitle",
            var id: Int = 1,
            var active: Boolean = true,
            var description: String = "default description",
            val publisher: String = "Kirik",
            var author: String = "Kirik",
            val featuredImage: String="",
            val rating: Int = 0,
            val country: String? = "",
            val sourceUrl: String="",
            val ingredients: String= "",
            var objectives: ArrayList<QuestObjective> = arrayListOf(
                QuestObjective(), QuestObjective()
            ),
            val region: String = "state or region here. goal: sort by region"
){
    suspend fun toRoom(dao: QuestDao) {

        val h=dao.save(QuestEntity(
                qid = this.qid,
                title = this.title,
                description = this.description,
                country = this.country,
                rating = this.rating,
                publisher = this.publisher,
                featuredImage = this.featuredImage,
                sourceUrl = this.sourceUrl,
                ingredients = this.ingredients,
                author = this.author
            ))
        convertobjectives(id=h,dao)
        }

    fun convertobjectives(id:Long,dao: QuestDao){
        //var objs: List<ObjectiveEntity> = listOf()
        objectives.forEach { dao.save(it.convert(id))

    }}
    //objectivesjson = Json.encodeToString(this.objectives),
    // Given
   /* val qid: Int = 0
    var title: String = "Create Quest"
    var description: String = "default description"
    var active: Boolean = true
    val publisher: String = "Kirik"
    var author: String = "Kirik"
    val featuredImage: String? = null
    val rating: Int = 0
    val sourceUrl: String = ""
    val ingredients: List<Any> = listOf()
    val categories: List<Quest> = TODO()

             val allSubCats: Array<List<GoalType>> =
                 categories.map { it. subCategories }.toTypedArray()
    */


    //var _objectives: MutableList<QuestObjective> = mutableListOf()



    lateinit var originalTitle: String

    @JsonClass(generateAdapter=true)
    open class QuestObjective(obj: String, beginDateAndTime: String, desc: String?, objectiveType: ObjectiveType, requiredAmount: Int?) {
        constructor() : this("", "",
            "", ObjectiveType.Default, -1


            //"", hashMapOf<Any, Any>(),
            //-1, LinkedTreeMap<Any, Any>()
        )
        fun convert(id:Long):ObjectiveEntity{
            return ObjectiveEntity(
                id=id,
                obj = obj,
                beginDateAndTime = beginDateAndTime,
                desc = desc,
                objectiveType = objectiveType,
                requiredAmount = requiredAmount,
                quest = "DEBUG_Quest"
            )

        }


        var obj: String= "title"
        var beginDateAndTime: String =  "beginDateAndTime: String, title: String?"
        var desc: String? = null

        var objectiveType: ObjectiveType = ObjectiveType.Default
        var requiredAmount: Int? = null;





        override fun toString(): String {
            return "$obj $desc"
        }


        //override fun notifyPropertyChanged(fieldId: Int){}
        //@Bindablevar currentAmount: Int = 0;
        //
        //
        //        fun IsReached(): Boolean {
        //            return (currentAmount >= requiredAmount!!)
        //        }
        //        fun getObjective(): String {
        //            return obj.toString()
        //        }//@Bindable
        //        fun getDescription(): String? {
        //            return this.desc
        //        }//@Bindable
        //        fun getAmount(): String? {
        //            return desc
        //        }
    //        fun ItemCollected() {
        //            if (objectiveType == ObjectiveType.Gathering) {
        //                currentAmount++;
        //            }
        //        }








    }



    lateinit var dateAdded: String
    lateinit var dateUpdated: String
    init{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateAdded = LocalDateTime.now().toString()
            update()
            originalTitle=title
        }
    }
    private fun update(){
        dateUpdated = LocalDateTime.now().toString()
    }

    fun getObjective(i: Int): String {
        return objectives[i].toString()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun toString(): String {
        return super.toString()
    }

    fun defaultObjective(): QuestObjective {
        val objective = QuestObjective()
        objective.obj="Make Default Objective"
        return objective
        }




    /*  private fun Quest(): Quest {
        active = true
        return this
    }


    fun setObjective(objective: String) {
        this.firstName = firstName
        notifyPropertyChanged(BR.firstName)

        fun setObjective(objective: String) {
            this.obj = objective
            notifyPropertyChanged(BR.firstName)
        }

        fun setDescription(desc: String) {
            this.desc = desc
            notifyPropertyChanged(BR.lastName)
        }
    }*/


    fun addObjective() {
        var objective_new = QuestObjective()
        objectives.add(objective_new)
        print("save or cancel")

    }

    enum class ObjectiveType {
        Default,
        Kill,
        Fetch,
        Escort,
        Explore,
        Do,
        Gathering
    }
     enum class GoalType {
        Default,
        Kill,
        Fetch,
        Escort,
        Explore,
        Do,
        Gathering
    }

    fun saveQuest() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateUpdated = LocalDateTime.now().toString()
        }

        print("Saved, jk just printing")
    }

    /*fun toRoom(): QuestEntity {
        val qe = QuestEntity(
            //objectivesjson = Json.encodeToString(this.objectives),

            title = this.title,
            description = this.description,
            id = 0,
        )
        val moshi: Moshi = Moshi.Builder().build()
        val entityJsonAdapter: JsonAdapter<ObjectiveEntity> =
            moshi.adapter(ObjectiveEntity::class.java)
        objectives.forEach {objective ->ObjectiveEntity(
            oid = objective.oid,
            obj = objective.obj, beginDateAndTime = objective.beginDateAndTime, desc = objective.desc, objectiveType =objective.objectiveType, requiredAmount =objective.requiredAmount, currentAmount =objective.currentAmount,quest  ="DEBUG_Quest")
            }
        return qe
    }*/

    /*  val moshi: Moshi = Moshi.Builder().build()
        val entityJsonAdapter: JsonAdapter<ObjectiveEntity> =
            moshi.adapter(ObjectiveEntity::class.java)*/
    override fun hashCode(): Int {
        var result = qid.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + active.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + publisher.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + featuredImage.hashCode()
        result = 31 * result + rating
        result = 31 * result + (country?.hashCode() ?: 0)
        result = 31 * result + sourceUrl.hashCode()
        result = 31 * result + ingredients.hashCode()
        result = 31 * result + objectives.hashCode()
        result = 31 * result + region.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + dateAdded.hashCode()
        result = 31 * result + dateUpdated.hashCode()
        return result
    }
}
/*
enum class RGB { RED, GREEN, BLUE }

inline fun <reified T : Enum<T>> printAllValues() {
print(enumValues<T>().joinToString { it.name })
}

printAllValues<RGB>() // prints RED, GREEN, BLUE
 */
/*
@SerializedName("Obj")
@Expose
var obj: String= "make objective"
@SerializedName("Description")
@Expose
var desc: String? = null
@SerializedName("Check")
@Expose
var check: Boolean = false
@SerializedName("GoalType")
@Expose
public var goalType: GoalType = GoalType.Default
@SerializedName("NUMrequired")
@Expose
var requiredAmount: Int? = null;
@SerializedName("NUMhave")
@Expose
var currentAmount: Int = 0;
*/


