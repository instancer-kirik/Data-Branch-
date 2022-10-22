package com.instance.dataxbranch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
        },

    ) { padding->
        var expanded by remember { mutableStateOf(false) }
        var expanded2 by remember { mutableStateOf(false) }
        var expanded3 by remember { mutableStateOf(false) }
        var expanded4 by remember { mutableStateOf(false) }
        Column(modifier = Modifier.padding(padding)) {
            OutlinedButton(
                onClick = {
                    expanded = !expanded
                    if (!expanded) {//since save triggers expanded, handle saving here with value: String
                        //Log.d("saved the objective", "$value")
//                       // updates
//                        uViewModel.save(oe)
                    }
                }
            ) {
                if(expanded){
                    Text(
                        "Get item, ice, water, etc\n" +
                                "Square breathing: 4sec in 4sec hold 4sec out 4sec hold repeat\n" +
                                "Change your clothes, bracelets, necklace, equipment, etc, \n" +

                                "Experience a scent\n" +
                                "tea, incense, an herb or spice, or a candle. try to note its qualities (sweet, spicy, citrusy, and so on).\n" +
                                "\n" +
                                "Go somewhere else\n" +

                                "\nHorse stance. crouch, feet wide apart. variations\n" +
                                "jumping, handstand, shadowbox, throw something, shake it out, run" +
                                "\nList 5-4-3-2-1 by see, hear, touch, smell, taste\n" )
                }
                else{ Text("Physical techniques")
            }}
            OutlinedButton(
                onClick = {
                    expanded2 = !expanded2
                    if (!expanded2) {//since save triggers expanded, handle saving here with value: String
                        //Log.d("saved the objective", "$value")
//                       // updates
//                        uViewModel.save(oe)
                    }
                }
            ) {
                if(expanded2){
                    Text("Visualize a place or task. somewhere. Or recall designated pleasant memory \n\nRotate a horse\n" +
                            "\nList by category:\n musical instruments, animals, ice cream flavors, machines, etc.\n" +
                            "Do math, geometry, count \n" +
                            "Note nuances in surroundings.\n" +
                            "Focus on item or point for a time \n" +
                            "Use an anchoring phrase\n" +
                            "This might be something like: “It is what it is. And it's time to d-d-d-d-duel. ”\n" +
                            "\nGather emotions, ball them up, and put in a box. Imaginary or otherwise.\n" +
                            "when you're ready face them, describe them and how you feel. color, sensation, location, etc"

                    )}
                                else{ Text("Mental techniques")
                }}
            OutlinedButton(
                onClick = {
                    expanded3 = !expanded3
                    if (!expanded3) {//since save triggers expanded, handle saving here with value: String
                        //Log.d("saved the objective", "$value")
//                       // updates
//                        uViewModel.save(oe)
                    }
                }
            ) {
                if(expanded3){
                    Text(
                        "“This time may be tough, but you’ll make it through.”\n" +
                                "\n“You’re strong, and when you're through this, you'll be stronger”\n" +
                                "“It ain't wasted .. if it makes ya stronger. That applies to your cyberware... and your crew too”\n"+

                                "Be with pets or plants \n" +

                                "List favorites: rocks, trees, rivers, foods, books, etc \n" +

                                "_________Plan an activity_________" +
                                "\nSolo, or co-op. Focus on the details, what you’ll wear, when and how you’ll get there.\n" +

                                "Music\n"+
                                "List things or traits of yourself current or future.\n"


                    )}
                else{ Text("Soothing techniques")
                }}
            OutlinedButton(
                onClick = {
                    expanded4 = !expanded4
                    if (!expanded4) {//since save triggers expanded, handle saving here with value: String
                        //Log.d("saved the objective", "$value")
//                       // updates
//                        uViewModel.save(oe)
                    }
                }
            ) {
                if(expanded4){
                    Text("Sanctuary Constructs:"+
                        "\n\nSolo entryway" +
                                "\n\nPurifying Light" +
                                "\n\nMain Room" +
                                "\n\nPeople Mover - way to summon people w/ light" +
                            "\n\n...8 more lines, not to spoil it all yet... " +
                            "\n\nMore with updates. " +
                            "\n\nSource: Life 101 - John-Roger & Peter McWilliams"
//                                "\n\nInformation Retrieval System (computer, librarians, phone) for asking questions and getting answers" +
//                                "\n\nVideo Screen with halo bg light for affirming imagery" +
//                                "\n\nAbility Suits - closet of costumes with associated abilities" +
//                                "\n\nDojo - for practicing (large, since ability suit for flight)" +
//                                "\n\nHealth Center - Staffed with any desired healing practitioner" +
//                                "\n\nPlayroom" +
//                                "\n\nSacred Room - meditation, inner work" +
//                                "\n\nMaster Teacher(MTs) for any concept, and if applicable, a classroom"

                    )}
                else{ Text("Sanctuary Constructs")
                }}


        }


}
}