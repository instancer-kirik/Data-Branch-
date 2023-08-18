package com.instance.dataxbranch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.data.entities.PartyMember
import com.instance.dataxbranch.ui.viewModels.PartyViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel

@Composable
fun CreatePartyScreen(viewModel: PartyViewModel = hiltViewModel(),userViewModel: UserViewModel = hiltViewModel()    ) {
    // Mutable state variables to hold the input values
    var partyName by remember { mutableStateOf("") }
    //v/ar leaderName
    var leaderName by remember { mutableStateOf(userViewModel.getSelectedCharacter().character.name) }
    // Function to handle party creation
    val createParty = {
        // Call the ViewModel function to create the party
        viewModel.createParty(partyName,leader = PartyMember(userViewModel.getMeWithAbilities().user,userViewModel.getSelectedCharacter().character), others = emptyList())
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Party Name input field
        TextField(
            value = partyName,
            onValueChange = { partyName = it },
            label = { Text("Party Name") },
            modifier = Modifier.padding(16.dp)
        )

        // Leader Name input field
        TextField(
            value = leaderName ,
            onValueChange = { leaderName = it },
            label = { Text("Leader Name") },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Button to create the party
        Button(
            onClick = createParty,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Create Party")
        }
    }
}