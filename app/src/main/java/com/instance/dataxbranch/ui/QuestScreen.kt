package com.instance.dataxbranch.ui


import android.app.PendingIntent.getActivity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.MainActivity
import com.instance.dataxbranch.Quest
import com.instance.dataxbranch.R
import com.instance.dataxbranch.core.Constants.ADD_QUEST
import com.instance.dataxbranch.core.Utils.Companion.printError
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.QuestsViewModel
import com.instance.dataxbranch.ui.components.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun QuestsScreen(
    viewModel: QuestsViewModel = hiltViewModel(),

){

    Scaffold(
        floatingActionButton = {
            AddQuestFloatingActionButton()
        }
    ) { padding ->
        if (viewModel.openDialogState.value) {
            AddQuestAlertDialog()
        }
        when (val questsResponse = viewModel.questsState.value) {
            is Response.Loading -> ProgressBar()
            is Response.Success -> _LazyColumnWithSelection(questsResponse.data,modifier = Modifier
                .fillMaxSize()
                .padding(padding))
            is Response.Error -> OnlyText("Error",questsResponse.message)

        }//printError(questsResponse.message)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val additionResponse = viewModel.isQuestAddedState.value) {
                is Response.Loading -> CircularProgressIndicator()
                is Response.Success -> Unit
                is Response.Error -> printError(additionResponse.message)
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val deletionResponse = viewModel.isQuestDeletedState.value) {
                is Response.Loading -> CircularProgressIndicator()
                is Response.Success -> Unit
                is Response.Error -> printError(deletionResponse.message)

            }
        }
    }

}


@Composable
fun _LazyColumnWithSelection(questsResponse: List<Quest>, modifier: Modifier){
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int -> selectedIndex = index}
    LazyColumn(
        modifier.fillMaxSize(),
    ){//repace count with quest.objectives.size
        itemsIndexed(questsResponse){ index,quest ->

            QuestView(
                quest = quest,
                index = index,
                selected = selectedIndex == index,
                onClick = onItemClick
            )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestView(quest: Quest, index: Int, selected: Boolean, onClick: (Int) -> Unit){
    Box(modifier = Modifier
        .clickable {
            onClick.invoke(index)
        }
        .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
        .fillMaxWidth()
        .padding(12.dp)){
    Text(
        text = "Index $index",
    )
        QuestCardContent(quest)
    }

}
@Composable
fun QuestListItem(quest: Quest){
    Card(
        modifier = Modifier.padding(end = 8.dp),
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

@Composable
private fun QuestCardContent(quest: Quest) {
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
            Text(text = "Title: ")
            Text(
                text = quest.title,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                //Text("test")
                quest.objectives.forEach { objective -> ObjectiveViewEdit(objective = objective)}
                
                
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.Check else Icons.Filled.ArrowDropDown,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}
@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}
@Composable
private fun ObjectiveView(objectives: ArrayList<Quest.QuestObjective>) {

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {

        items(items = objectives) { objective ->
            //Text(objective.obj+" nya")
            ObjectiveViewEdit(objective = objective)
        }
    }
}
@Composable
private fun ObjectiveViewEdit(objective: Quest.QuestObjective) {
    var value by remember { mutableStateOf(objective.obj) }
    var expanded by remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    Surface(
        color = MaterialTheme.colors.primaryVariant,
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
                if(!expanded){//since save triggers expanded, handle saving here with value: String
                    //Log.d("saved the objective", "$value")
                    objective.obj=value
                }
                }
            ) {
                if (expanded) {
                    Text("Save")
                    TextField(
                        value = "$value",
                        onValueChange = { value = it },
                        label = { Text("Edit text") },
                        maxLines = 2,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }else Text("Edit")
            }

        }
    }
}

@Composable
fun AddQuestFloatingActionButton(viewModel: QuestsViewModel = hiltViewModel()
) {
    FloatingActionButton(
        onClick = {
            viewModel.openDialogState.value = true
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = ADD_QUEST
        )
    }
}
