package com.instance.dataxbranch

import android.os.Build
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
class Quest(val id: Int=-1,
            var title: String = "Title",
            var active: Boolean = true,
            var description: String = "default description",
            val publisher: String = "Kirik",
            var author: String = "Kirik",
            val featuredImage: String="",
            val rating: Int = 0,
            val sourceUrl: String="",
            val ingredients: List<Any> = listOf(),
            var objectives: ArrayList<QuestObjective> = arrayListOf(QuestObjective())
){
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

    lateinit var dateAdded: String
    lateinit var dateUpdated: String
    //var _objectives: MutableList<QuestObjective> = mutableListOf()



    class QuestObjective(){

        var obj: String? = "make objective"
        var desc: String? = null
        var check: Boolean = false
        public var goalType: GoalType = GoalType.Default
        var requiredAmount: Int? = null;
        var currentAmount: Int = 0;


        fun IsReached(): Boolean {
            return (currentAmount >= requiredAmount!!)
        }

        fun ItemCollected() {
            if (goalType == GoalType.Gathering) {
                currentAmount++;
            }
        }


        override fun toString(): String {
            return obj.toString() + desc.toString()
        }


        //override fun notifyPropertyChanged(fieldId: Int){}
        //@Bindable
        fun getObjective(): String {
            return obj.toString()
        }


        //@Bindable
        fun getDescription(): String? {
            return this.desc
        }


        //@Bindable
        fun getAmount(): String? {
            return desc
        }
        /*fun setAmount(desc: String) {
        this.desc=desc
        notifyPropertyChanged(BR.desc)
    }*/
    }

    fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateAdded = LocalDateTime.now().toString()
            dateUpdated = dateAdded
        }


    }
    fun getObjective(i: Int): String {
        return objectives[i].getObjective()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun toString(): String {
        return super.toString()
    }

    fun defaultObjective(): QuestObjective {
        val objective = QuestObjective()
        objective.obj="Make Objective"
        return objective
        }


    @JvmName("getObjectives1")
    fun getObjectives(): ArrayList<QuestObjective> {
        return this.objectives
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


    public enum class GoalType {
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


