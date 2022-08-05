/*
 * Copyright (c) 2014-2022 Stream.io Inc. All rights reserved.
 *
 * Licensed under the Stream License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://github.com/GetStream/stream-chat-android/blob/main/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.instance.dataxbranch.social.StreamChat.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.instance.dataxbranch.R
import com.instance.dataxbranch.destinations.ChannelsActivityScreenDestination

import com.instance.dataxbranch.social.StreamChat.ChatHelper
import com.instance.dataxbranch.social.StreamChat.CustomLoginScreen

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import io.getstream.chat.android.client.errors.ChatError
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.core.internal.InternalStreamChatApi
import io.getstream.chat.android.ui.ChatUI.navigator


/**
 * An Activity that allows users to manually log in to an environment with an API key,
 * user ID, user token and user name.
 */
//ACTIVITIES ARE DIFFICULT AND ANNOYING. NOT USING MULTIPLE. just main
class CustomLoginActivity: AppCompatActivity() {

    @OptIn(InternalStreamChatApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState,)
        val context = LocalContext
        val navController: NavController = findNavController(R.id.sign_in_layout)
        //val  navigator:DestinationsNavigator = getNavigator()
        setContent {
            ChatTheme {
                CustomLoginScreen(

                    //onBackButtonClick = ::finish,
                    /*onLoginButtonClick = { userCredentials ->

                        fun openChannels() {
                            navController.navigate(ChannelsActivityScreenDestination)
                            //startActivity(ChannelsActivity.createIntent(this))
                            finish()
                        }
                        ChatHelper.connectUser(
                            userCredentials = userCredentials,

                            onSuccess = ::openChannels,
                            onError = ::showError
                        )
                    }*/)

            }
        }
    }




    private fun showError(chatError: ChatError) {
        Toast.makeText(this, "Login failed ${chatError.message}", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CustomLoginActivity::class.java)
        }
    }
}
