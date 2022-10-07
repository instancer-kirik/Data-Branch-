package com.instance.dataxbranch.ui


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.R
import com.instance.dataxbranch.core.Constants.ADD_QUEST
import com.instance.dataxbranch.core.Utils.Companion.printError
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.ui.components.AddQuestAlertDialog
import com.instance.dataxbranch.ui.components.OnlyText
import com.instance.dataxbranch.ui.components.QuestToolbar
import com.instance.dataxbranch.ui.viewModels.QuestsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun QuestsScreen(
    viewModel: QuestsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
){
    //Button(onClick = {navigator.navigate(MyQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to my quests")}
    Scaffold(
        topBar = { QuestToolbar(navigator) },
        floatingActionButton = {
            AddQuestFloatingActionButton()
        }
    ) { padding ->
        if (viewModel.openDialogState.value) {
            AddQuestAlertDialog()
        }
        when (val questsResponse = viewModel.questsState.value) {
            is Response.Loading -> ProgressBar()
            is Response.Success -> LazyColumn(viewModel, questsResponse.data, modifier = Modifier
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
fun LazyColumn(viewModel: QuestsViewModel, questsResponse: List<Quest>, modifier: Modifier) {
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int -> selectedIndex = index}
    LazyColumn(
        modifier.fillMaxSize(),
    ){//repace count with quest.objectives.size
        itemsIndexed(questsResponse){ index,quest ->

            QuestView(
                viewModel= viewModel,
                quest = quest,
                index = index,
                selected = selectedIndex == index,
                onClick = onItemClick
            )
        }
    }
}

/*@Composable
fun LazyColumn(viewModel: QuestsViewModel, questsResponse: List<Quest>, modifier: () -> Unit){

}*/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestView(viewModel: QuestsViewModel, quest: Quest, index: Int, selected: Boolean, onClick: (Int) -> Unit){
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
        QuestCardContent(viewModel, quest)
    }

}


@Composable
private fun QuestCardContent(viewModel: QuestsViewModel, quest: Quest) {
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
            Text(text = "Title-- " )
            Text(
                text = quest.title,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Button(onClick={viewModel.addQuestToRoom(quest)}){
                Text("pull from cloud")
            }
            if (expanded) {
                //Text("test")

                quest.objectives.forEach { objective -> ObjectiveViewNoEdit(objective = objective)}
                
                
            }
        }
        IconButton(onClick = { expanded = !expanded
        if(!expanded){//saves on click when closing
                viewModel.addQuest(quest)
        }}) {

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
fun ObjectiveViewNoEdit(objective: Quest.QuestObjective) {
    var value by remember { mutableStateOf(objective.obj) }
    var expanded by remember { mutableStateOf(false) }
    var value2 by remember { mutableStateOf(objective.desc) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {

            Spacer(modifier = Modifier.padding(8.dp))
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {

                Text(text = "Objective ")
                Text(text = objective.toString())

            }


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
            ObjectiveViewNoEdit(objective = objective)
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
