package com.instance.dataxbranch.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun QuietScreen() {
    val context = LocalContext.current

    Scaffold(

        topBar = {},
        floatingActionButton = {

            // EditAbilityEntityFloatingActionButton()
        }
    ) { _->

        Text("Physical grounding techniques:\n" +

                " Get item, ice, water, etc\n" +
                "Square breathing: 4sec in 4sec hold 4sec out 4sec hold repeat\n" +
                "Change your clothes, bracelets, necklace, equipment, etc, \n" +

                "Experience a scent\n" +
                "tea, incense, an herb or spice, or a candle. try to note its qualities (sweet, spicy, citrusy, and so on).\n" +
                "\n" +
                "Go somewhere else\n" +

                "horse stance. crouch, feet wide apart. variations\n" +
                "jumping, handstand, shadowbox, throw something\n" +
                "List 5-4-3-2-1 by see, hear, touch, smell, taste\n" +

                "\n" +
                "Mental grounding techniques:\n" +
                "Note nuances in surroundings.\n" +
                "Visualize a place or task.somewhere else.\n Rotate a horse, Recall by photo" +
                " List by category:\n musical instruments, animals, ice cream flavors, machines\n"+
                "Put it in a box. imaginary or otherwise\n" +
                "Do math, geometry, count \n" +
                "Use an anchoring phrase\n" +
                "This might be something like: “It is what it is. And it's time to d-d-d-d-duel. ”\n" +



                "gather emotions, ball them up, and put in a box. Imaginary or otherwise.\n" +
                "when you're ready face them, describe them and how you feel. color, sensation etc\n\n" +




                "Soothing grounding techniques:\n" +

                "“This time may be tough, but you’ll make it through.”\n" +
                "“You’re strong, and when you're through this, you'll be stronger”\n" +
                "“You’re trying hard, and you’re doing your best.”\n" +

                "Use time/thoughts with pets or plants \n" +

                "List favorites: rocks, trees, rivers, foods, books, etc \n" +

                "Plan an activity\n" +

                "Solo, or co-op. Focus on the details, what you’ll wear, when and how you’ll get there.\n" +


                " List things or traits of yourself current or future.\n" +

                " Listen to music\n" )




}
}