package com.instance.dataxbranch.github

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.instance.dataxbranch.ui.viewModels.QuestsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@Destination
    @Composable
    fun GithubProfileScreen(viewmodel: GithubViewmodel= hiltViewModel(),questsViewModel: QuestsViewModel= hiltViewModel(),) {
        val profile =viewmodel.user.value
        profile?.let {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = it.image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Name: ${it.name}")

                Row {
                    Text(text = "Repos: ${it.repos}")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Gists: ${it.gists}")
                }

                Row {
                    Text(text = "Followers: ${it.followers}")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Following: ${it.following}")
                }
                Button(onClick = {questsViewModel.viewModelScope.launch {  questsViewModel.addQuest("title","description","author") }} ) {
                    Text(text = "Add Quest")


                }
            }
        }
    }
