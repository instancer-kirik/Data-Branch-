package com.instance.dataxbranch.quests

import android.util.Log
import com.instance.dataxbranch.quests.Quest

data class CloudQuest (
    val qid: String= "-1",

    val title: String = "Study Math_Default",
    val imageUrl: String = "",


    val tagline: String = "Dessert",


    val description: String = "default description",
    val questGiver: String = "Kirik",
    val author: String= "Kirik",
    val rating: Int = 5,
    val rating_denominator: Int = 5,
    val ingredients: String="",
    val dateAdded: String= "",
    val dateUpdated: String= "",
    val objectives: ArrayList<Quest.QuestObjective> = arrayListOf(
        Quest.QuestObjective()
    ),
    val country: String? = "",
    val sourceUrl: String="",
    val region: String = "state or region here. goal: sort by region"
) {
    fun toQuest(): Quest {
        return Quest(
            qid = qid,
            title = title,
            description = description,
            questGiver = questGiver,
            author = author,
            featuredImage = imageUrl,
            rating = rating,
            rating_denominator = rating_denominator,
            country = country,
            sourceUrl = sourceUrl,
            ingredients = ingredients,
            objectives = objectives,
            region = region,
            tagline = tagline
        )
    }

    data class builder(
        var qid: String= "-1",

                       var title: String = "Study Math_Default",
                       var imageUrl: String = "",

                       var tagline: String = "Dessert",

                       var description: String = "default description",
                       var questGiver: String = "Kirik",
                       var author: String = "Kirik",
                       var rating: Int = 5,
                       var rating_denominator: Int = 5,
                       var ingredients: String="",
                       var dateAdded: String= "",
                       var dateUpdated: String= "",
                       var objectives: ArrayList<Quest.QuestObjective> = arrayListOf(
                           Quest.QuestObjective()
                       ),
                       var country: String? = "",
                        var sourceUrl: String="",
                        var region: String = "state or region here. goal: sort by region"
    ) {
    fun qid(qid: String) = apply {

    Log.d("CloudQuest", "builder qid: $qid")
        if (qid=="-1") {
            this.qid = java.util.UUID.randomUUID().toString()
        } else {
            this.qid = qid
        }
        }

    fun title(title: String) = apply { this.title = title }
    fun imageUrl(imageUrl: String) = apply { this.imageUrl = imageUrl }
    fun tagline(tagline: String) = apply { this.tagline = tagline }
    fun description(description: String) = apply { this.description = description }
    fun questGiver(questGiver: String) = apply { this.questGiver = questGiver }
    fun author(author: String) = apply { this.author = author }
    fun rating(rating: Int) = apply { this.rating = rating }
    fun rating_denominator(rating_denominator: Int) = apply { this.rating_denominator = rating_denominator }
    fun ingredients(ingredients: String) = apply { this.ingredients = ingredients }
    fun dateAdded(dateAdded: String) = apply { this.dateAdded = dateAdded }
    fun dateUpdated(dateUpdated: String) = apply { this.dateUpdated = dateUpdated }
    fun objectives(objectives: ArrayList<Quest.QuestObjective>) = apply { this.objectives = objectives }
    fun country(country: String?) = apply { this.country = country }
    fun sourceUrl(sourceUrl: String) = apply { this.sourceUrl = sourceUrl }
    fun region(region: String) = apply { this.region = region }
    fun buildFromQuest(quest: Quest) = CloudQuest(qid, quest.title, quest.featuredImage, quest.tagline, quest.description, quest.questGiver, quest.author, quest.rating, quest.rating_denominator, quest.ingredients, quest.dateAdded, quest.dateUpdated, quest.objectives, quest.country, quest.sourceUrl, quest.region)
    fun build() = CloudQuest(qid, title, imageUrl, tagline, description, questGiver, author, rating, rating_denominator, ingredients, dateAdded, dateUpdated, objectives, country, sourceUrl, region)
   }}
/*
For a model class with default values like the `Snack` class above, Firestore can perform automatic
serialization in `DocumentReference#set()` and automatic deserialization in
`DocumentSnapshot#toObject()`. For more information on data mapping in Firestore, see the
documentation on [custom objects][firestore-custom-objects].

*/



//var _objectives: MutableList<Quest.QuestObjective> = mutableListOf()

//var sourceUrl: String = ""
//@field:JvmField // use this annotation if your Boolean field is prefixed with 'is'