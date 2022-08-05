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
package com.instance.dataxbranch.social.StreamChat;
//package io.getstream.chat.android.compose.sample.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.app.TaskStackBuilder
import com.instance.dataxbranch.DataBranchApp
import com.instance.dataxbranch.social.StreamChat.login.UserLoginActivity


/**
 * An Activity without UI responsible for startup routing. It navigates the user to
 * one of the following screens:
 *
 * - Login screen, if the user is not authenticated
 * - Channels screen, if the user is authenticated
 * - Messages screen, if the user is coming from a push notification
 */

class StartupActivity : AppCompatActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userCredentials = DataBranchApp.credentialsRepository.loadUserCredentials()
        if (userCredentials != null) {
            // Ensure that the user is connected
            ChatHelper.connectUser(userCredentials)

            if (intent.hasExtra(KEY_CHANNEL_ID)) {
                // Navigating from push, route to the messages screen
                val channelId = requireNotNull(intent.getStringExtra(KEY_CHANNEL_ID))
                TaskStackBuilder.create(this)
                    //.addNextIntent(ChannelsActivity.createIntent(this))
                    .addNextIntent(MessagesActivity.createIntent(this, channelId))
                    .startActivities()
            } else {
                // Logged in, navigate to the channels screen
                //startActivity(ChannelsActivity.createIntent(this))

            }
        } else {
            // Not logged in, start with the login screen
            startActivity(UserLoginActivity.createIntent(this))
        }
        finish()
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channelId"

        fun createIntent(context: Context, channelId: String): Intent {
            return Intent(context, StartupActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }

        fun createIntent(context: Context): Intent {
            return Intent(context, StartupActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, "")
            }
        }
    }
}
