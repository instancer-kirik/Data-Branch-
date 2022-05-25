package com.instance.dataxbranch.quests


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.instance.dataxbranch.Quest
import com.instance.dataxbranch.composables.CircularProgressBar
import com.instance.dataxbranch.composables.QuestCard
import com.instance.dataxbranch.data.DataOrException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
/*
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class QuestsActivity : AppCompatActivity() {
    private val viewModel: QuestsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataOrException = viewModel.data
            QuestsActivity(dataOrException)
        }
    }

    @Composable
    fun QuestsActivity(dataOrException: DataOrException<List<Quest>, Exception>) {
        val quests = dataOrException.data
        quests?.let {
            LazyColumn {
                items(
                    items = quests
                ) { quest ->
                    QuestCard(quest = quest)
                }
            }
        }

        val e = dataOrException.e
        e?.let {
            Text(
                text = e.message!!,
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressBar(
                isDisplayed = viewModel.loading.value
            )
        }
    }
}*/