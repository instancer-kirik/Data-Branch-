@file:OptIn(ExperimentalFoundationApi::class)

package com.instance.dataxbranch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//import io.github.rosariopfernandes.firebasecompose.R
import io.github.rosariopfernandes.firebasecompose.firestore.FirestoreCollection
import io.github.rosariopfernandes.firebasecompose.firestore.collectionStateOf
//import io.github.rosariopfernandes.firebasecompose.model.Snack
import io.github.rosariopfernandes.firebasecompose.ui.components.LoadingBar
import io.github.rosariopfernandes.firebasecompose.ui.components.OnlyText
//import io.github.rosariopfernandes.firebasecompose.ui.theme.FirebaseComposeTheme
import com.instance.dataxbranch.QuestService

import com.instance.dataxbranch.ui.theme.DataXBranchTheme
import dagger.hilt.android.AndroidEntryPoint


const val FIRESTORE_COLLECTION = "quests"

@AndroidEntryPoint()
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataXBranchTheme {
               /* // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                    //PreviewConversation()
                }
            }
        }
    }
}*/
                Scaffold(

                    topBar = { Toolbar(this@MainActivity) },
                    content = {padding ->
                        Column(
                            modifier = Modifier
                                .padding(padding)
                        ){Text("YOTE")}

                    

                        val query = Firebase.firestore.collection(FIRESTORE_COLLECTION)
                        val (result) = remember { collectionStateOf (query, this) }
                    when (result) {
                        is FirestoreCollection.Error -> {
                            OnlyText(
                                title = stringResource(R.string.title_error),
                                message = result.exception.message ?: ""
                            )
                        }
                        is FirestoreCollection.Loading -> {
                            LoadingBar()
                        }
                        is FirestoreCollection.Snapshot -> {
                            if (result.list.isEmpty()) {
                                OnlyText(
                                    title = stringResource(id = R.string.title_empty),
                                    message = stringResource(id = R.string.message_empty)
                                )
                            } else {
                                QuestList(context = this@MainActivity, items = result.list)
                            }
                        }
                    }
                }
            )
            }
        }
    }
}

@Composable
fun Toolbar(context: Context) {
    TopAppBar(
        title = { Text(text = "FireSnacks") },
        actions = {
            Button(onClick = { addItems(context) }) {
                Text(text = "Add Items")
            }
        }
    )
}

fun addItems(context: Context) {
    val firestore = Firebase.firestore
    val collection = firestore.collection(FIRESTORE_COLLECTION)
    firestore.runBatch { batch ->
        for (item in getItems()) {
            val docRef = collection.document()
            batch.set(docRef, item)
        }
    }.addOnSuccessListener {
        Toast.makeText(context, "Items added!", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener { e ->
        Toast.makeText(context, "Error adding items: ${e.message}", Toast.LENGTH_LONG).show()
    }
}

private fun getItems() = listOf(
    Quest(title = "Quest1"),Quest(title = "Quest2"),Quest(title = "Quest3")
)

@ExperimentalFoundationApi
@Composable
fun QuestList(
    context: Context,
    items: List<DocumentSnapshot>

) {
    LazyColumnWithSelection()
}
     /*
        content = items(items=items) { snapshot ->
            val quest = snapshot.toObject<Quest>()!!
            QuestItem(quest = quest, onQuestClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("questId", snapshot.id)
                context.startActivity(intent)
            })
        }
    }
}*/
     @Preview
     @Composable
     fun LazyColumnWithSelection(){
         var selectedIndex by remember { mutableStateOf(0) }
         val onItemClick = { index: Int -> selectedIndex = index}
         LazyColumn(
             modifier = Modifier.fillMaxSize(),
         ){
             items(100){ index ->
                 ItemView(
                     index = index,
                     selected = selectedIndex == index,
                     onClick = onItemClick
                 )
             }
         }
     }
@Composable
fun QuestItem(
    quest: Quest,
    onQuestClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = { onQuestClick() })
                .padding(8.dp)
        ) {
            /*Glide( )
                data = quest.featuredImage,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                requestBuilder = {
                    val options = RequestOptions()
                    options.circleCrop()
                    apply(options)
                }
            )
*/
            Icon(painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = null // decorative element
            )
            Text(
                text = quest.title,
                style = MaterialTheme.typography.subtitle1,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}



@Composable
fun QuestForm(){
    val context = LocalContext.current
    val titleValue = remember{mutableStateOf(TextFieldValue())}
    val descriptionValue = remember{mutableStateOf(TextFieldValue())}
    //val titleValue = remember{mutableStateOf(TextFieldValue())}
    val quests = remember{mutableStateListOf(Quest())}
    val isSaved = remember{mutableStateOf(false)}
    val db = FirebaseFirestore.getInstance()
    QuestService.getQuests(quests)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        DetailInput(fieldValue=titleValue, label = "title")
        Button(modifier = Modifier.fillMaxWidth(),
        onClick = {
            val quest = Quest(
                title = titleValue.value.text,
                description = descriptionValue.value.text
            )
            db.collection("quests")
                .add(quest)
                .addOnCompleteListener{
                    if(it.isSuccessful) {
                        showToast(context, "Saved!")
                        isSaved.value = true
                        descriptionValue.value = TextFieldValue()
                        titleValue.value = TextFieldValue()
                    }else{showToast(context, "Error saving")
                    }
                }
                .addOnFailureListener{
                    showToast(context,"Error: ${it.message}")
                }
        }){
            Text(text = "Save")
        }
        if(isSaved.value){
            QuestService.getQuests(quests)
            isSaved.value = false
        }
        Spacer(modifier=Modifier.padding(8.dp))
        LazyRow{
            items(quests){ item: Quest ->
                QuestListItem(quest = item)
            }
        }
        //Greetings()
}}
@Composable
fun QuestListItem(quest: Quest){
    Card(modifier = Modifier.padding(end = 8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
    ){
        Column(modifier = Modifier.padding(8.dp)
        ){
            Text(text = quest.title)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = quest.description)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = quest.author  + " last modified on " + quest.dateUpdated)
        }
    }

}
//private fun showToast
@Composable
fun MyApp() {
    var quest = Quest()

    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuestHeader(quest)
            ObjectiveView(quest.getObjectives())

            Propertyboxcontainer()
            //Greetings()
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Data Branch!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}
private fun showToast(context: Context, msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
@Composable
fun DetailInput(fieldValue: MutableState<TextFieldValue>,
label: String,
singleLine: Boolean = true) {
    OutlinedTextField(
        label = {
            Text(text = label)
        },
        modifier = if (singleLine) {
            Modifier.fillMaxWidth()
        } else {
            Modifier
                .fillMaxWidth()
                .height(140.dp)
        },
        singleLine = singleLine,
        value = fieldValue.value,

        onValueChange = {
            fieldValue.value = it
        })
}

@Composable
fun QuestSelector(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Select a quest!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}


@Composable
private fun ObjectiveView(objectives: ArrayList<Quest.QuestObjective>) {

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {

        items(items = objectives) { objective ->
            ObjectiveViewEdit(objective = objective)
        }
    }
}

@Composable
private fun ObjectiveViewEdit(objective: Quest.QuestObjective) {
    var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }
    var expanded by remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Objective ")
                Text(text = objective.toString())

            }
            OutlinedButton(
                onClick = { expanded = !expanded
                }
            ) {
                if (expanded) {
                    Text("Save")
                    TextField(
                        value = value,
                        onValueChange = { value = it },
                        label = { Text("Enter text") },
                        maxLines = 2,
                        textStyle = TextStyle(
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }else Text("Edit")
            }

        }
    }
}
@Preview
@Composable
fun Propertyboxcontainer(){
    val input = "LLLLLLLL"
    propertybox(input)}
@Composable
private fun propertybox(input: String){
    var value by remember { mutableStateOf(input) }
    var expanded by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { expanded = !expanded
        }
    ) {
        if (expanded) {
            Text("Save")
            TextField(

                value = value,
                onValueChange = { value = it },
                label = { Text("Enter text") },
                maxLines = 2,
                textStyle = TextStyle(
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(20.dp)
            )
        }else Text("Edit")
    }
}


@Composable
fun SimpleFilledTextFieldSample() {
    var text by remember { mutableStateOf("LOREM IPSUM") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}
//@Preview(showBackground = true)
@Composable
fun StyledTextField() {
    var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }

    TextField(
        value = value,
        onValueChange = { value = it },
        label = { Text("Enter text") },
        maxLines = 2,
        textStyle = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
}

@Preview(showBackground = true,
    name = "ObjectiveView")
@Composable
fun DefaultPreview() {
    DataXBranchTheme {
        QuestHeader(Quest())
        ObjectiveView(arrayListOf(Quest.QuestObjective(),Quest.QuestObjective()))
    }
}

@Composable
fun QuestHeader(quest: Quest) {
    var titlevalue by remember { mutableStateOf(quest.title) }
    var spiel by remember { mutableStateOf(quest.description)}
    TextField(
        value = titlevalue,
        onValueChange = { titlevalue = it
            quest.title = it},
        label = { Text("Enter text") },
        maxLines = 1,
        textStyle = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
    TextField(
        value = spiel,
        onValueChange = { spiel = it },
        label = { Text("Enter text") },
        maxLines = 1,
        textStyle = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
}

/*
@Composable
fun Screen() {
    val viewModel = remember { ViewModel() } // or viewModel() etc.
    val (password, setPassword) = viewModel.password.collectAsMutableState()

    TextField(
        value = password,
        onValueChange = setPassword
    )
}*/
//@Preview(showBackground = true)
@Composable
fun MessageCard() {
    val msg= Message(
        "Colleague",
        "Test...Test...Test..."
    )
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {

                Text(
                    text = msg.author,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            //MessageCard(message)
        }
    }
}

@Composable
fun PreviewConversation() {
    DataXBranchTheme {
        Conversation(SampleData.conversationSample)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    DataXBranchTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}
//@Preview(string = "Light Mode")
/*
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    string = "MessageCard_Dark Mode")
 */
@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.Build else Icons.Filled.Done,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}
fun <T> SnapshotStateList<T>.updateList(newList:List<T>){
    clear()
    addAll(newList)
}
@Composable
fun ItemView(index: Int, selected: Boolean, onClick: (Int) -> Unit){
    Text(
        text = "Item $index",
        modifier = Modifier
            .clickable {
                onClick.invoke(index)
            }
            .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
            .fillMaxWidth()
            .padding(12.dp)
    )
}

/*
*
## Querying

On the main screen of your app, you may want to show the cheapest 20 snacks.
In Firestore, you would use the following query:

```kotlin
val query = Firebase.firestore.collection("snacks")
        .orderBy("price")
        .limit(20)
```

To retrieve this data without FirebaseUI, you might use `addSnapshotListener` to listen for
live query updates:

```kotlin
query.addSnapshotListener { snapshot, e ->
    e?.let {
        // Handle error
        return@addSnapshotListener
    }

    val chats = snapshot.toObjects<Snack>()

    // Update UI
    // ...
}
```*/