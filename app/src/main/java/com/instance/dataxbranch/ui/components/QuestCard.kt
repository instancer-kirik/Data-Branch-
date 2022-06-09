package com.instance.dataxbranch.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.core.Constants.DELETE_QUEST
import com.instance.dataxbranch.quests.QuestsViewModel

@ExperimentalFoundationApi
@Composable
fun QuestCard(//this has a button to delete a quest. removes from firestore
    quest: Quest,
    viewModel: QuestsViewModel = hiltViewModel()
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
            ){
                Text(
                    text = quest.title,
                    color = Color.DarkGray,
                    fontSize = 25.sp
                )
                Text(
                    text = "by ${quest.author}",
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
            IconButton(
                onClick = {
                        viewModel.deleteQuest(quest)

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = DELETE_QUEST,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}