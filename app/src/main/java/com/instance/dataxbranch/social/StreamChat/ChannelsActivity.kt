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
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.instance.dataxbranch.DataBranchApp
import com.instance.dataxbranch.R
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.destinations.CustomLoginScreenDestination
import com.instance.dataxbranch.destinations.HubScreenDestination
import com.instance.dataxbranch.destinations.LoginScreenDestination
import com.instance.dataxbranch.destinations.MyChannelListUIDestination

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.querysort.QuerySortByField
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.sample.ui.BaseConnectedActivity

import com.instance.dataxbranch.social.StreamChat.login.UserLoginActivity
import com.ramcosta.composedestinations.annotation.Destination
import io.getstream.chat.android.compose.state.channels.list.ChannelItemState
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.header.ChannelListHeader
import io.getstream.chat.android.compose.ui.channels.info.SelectedChannelMenu
import io.getstream.chat.android.compose.ui.channels.list.ChannelItem
import io.getstream.chat.android.compose.ui.channels.list.ChannelList
import io.getstream.chat.android.compose.ui.components.SearchInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.offline.extensions.globalState
import io.getstream.chat.android.ui.ChatUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction2


@Destination
@Composable
fun ChannelsActivityScreen(navi: DestinationsNavigator){
    
    val factory by lazy {
        ChannelViewModelFactory(
            ChatClient.instance(),
            QuerySortByField.descByName("last_updated"),
            null
        )
    }

    val listViewModel: ChannelListViewModel = viewModel(
    factory = ChannelViewModelFactory(ChatClient.instance(),
        QuerySortByField.descByName("last_updated"),
        null)
    )
    fun onCreate(savedInstanceState: Bundle?) {
    }
        /**
         * To use the Compose SDK/Components, simply call [setContent] to provide a Compose UI
         * definition, in which you gain access to all the UI component functions.
         *
         * You can use the default [ChannelsScreen] component that sets everything up for you,
         * or build a custom component yourself, like [MyCustomUi].
         */
        Scaffold{padding->
            ChatTheme(dateFormatter = DataBranchApp.dateFormatter) {
                @Composable fun hubthing(navigator: DestinationsNavigator){
                    Button(onClick = { navigator.navigate(HubScreenDestination)}){Text("TO HUB")}
                    MyChannelListUI(navigator)}
                Text(padding.toString())
                ChannelsScreen(
                    title = stringResource(id = R.string.app_name),
                    isShowingHeader = true,
                    isShowingSearch = true,
                    onItemClick = ::openMessages,
                    onBackPressed = { navi.navigate(MyChannelListUIDestination)},
                    onHeaderAvatarClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            ChatHelper.disconnectUser()
                        }
                        //openUserLogin()
                    }
                )


//                MyCustomUi()
            }
        }
    }



/**
     * An example of a screen UI that's much more simple than the ChannelsScreen component, that features a custom
     * ChannelList item.
     */
    @Destination
    @Composable
fun MyChannelListUI(navi: DestinationsNavigator) {
        ChatClient.instance().globalState
        val user by ChatClient.instance().clientState.user.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Row{
                ChannelListHeader(
                    title = stringResource(id = R.string.app_name),
                    currentUser = user
                )
           
            }}
        ) {padding->
            Text(padding.toString())
            Button(onClick = { navi.navigate(HubScreenDestination)}){Text("TO HUB2")}
            ChannelList(
                modifier = Modifier.fillMaxSize(),
                itemContent = {
                    CustomChannelListItem(channelItem = it, user = user)
                },
                divider = {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(0.5.dp)
                            .background(color = ChatTheme.colors.textLowEmphasis)
                    )
                }
            )
        }
    }

    /**
     * An example of a customized DefaultChannelItem component.
     */
    @Composable
    private fun CustomChannelListItem(channelItem: ChannelItemState, user: User?) {
        ChannelItem(
            channelItem = channelItem,
            currentUser = user,
            onChannelLongClick = {},
            onChannelClick = {},
            trailingContent = {
                Spacer(modifier = Modifier.width(8.dp))
            },
            centerContent = {
                Text(
                    text = ChatTheme.channelNameFormatter.formatChannelName(it.channel, user),
                    style = ChatTheme.typography.bodyBold,
                    color = ChatTheme.colors.textHighEmphasis
                )
            }
        )
    }

    /**
     * An example of what a custom UI can be, when not using [ChannelsScreen].
     *
     * It's important to note that if we want to use the [SelectedChannelMenu] to expose information and
     * options that the user can make with each channel, we need to use a [Box] and overlap the
     * two elements. This makes it easier as it's all presented in the same layer, rather than being
     * wrapped in drawers or more components.
     */
    @Composable
    private fun MyCustomUi(listViewModel:ChannelListViewModel,navController: NavController) {
        var query by remember { mutableStateOf("") }

        val user by listViewModel.user.collectAsState()
        val delegatedSelectedChannel by listViewModel.selectedChannel
        val connectionState by listViewModel.connectionState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                ChannelListHeader(
                    title = stringResource(id = R.string.app_name),
                    currentUser = user,
                    connectionState = connectionState
                )

                SearchInput(
                    modifier = Modifier
                        .background(color = ChatTheme.colors.appBackground)
                        .fillMaxWidth()
                        .padding(8.dp),
                    query = query,
                    onValueChange = {
                        query = it
                        listViewModel.setSearchQuery(it)
                    }
                )

                ChannelList(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = listViewModel,
                    onChannelClick = ::openMessages,
                    onChannelLongClick = { listViewModel.selectChannel(it) }
                )
            }

            val selectedChannel = delegatedSelectedChannel
            if (selectedChannel != null) {
                SelectedChannelMenu(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(16.dp),
                    isMuted = listViewModel.isChannelMuted(selectedChannel.cid),
                    selectedChannel = selectedChannel,
                    currentUser = user,
                    onChannelOptionClick = { action -> listViewModel.performChannelAction(action) },
                    onDismiss = { listViewModel.dismissChannelAction() }
                )
            }
        }
    }

  //,navController: NavController
    private fun openMessages(channel: Channel) {
       // navController.navigate("messagelist/${channel.cid}")
        Log.d(TAG, "openmessages in ChannelsActivity ")
    }
@Composable
    private fun openUserLogin(navi: DestinationsNavigator) {
        //finish()
        navi.navigate(CustomLoginScreenDestination)
        //overridePendingTransition(0, 0)

/*
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChannelsActivity::class.java)
        }
    }*/
}
/*/*
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
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.instance.dataxbranch.DataBranchApp
import com.instance.dataxbranch.R
import com.instance.dataxbranch.ui.destinations.HubScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.querysort.QuerySortByField
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.sample.ui.BaseConnectedActivity

import com.instance.dataxbranch.social.StreamChat.login.UserLoginActivity
import io.getstream.chat.android.compose.state.channels.list.ChannelItemState
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.header.ChannelListHeader
import io.getstream.chat.android.compose.ui.channels.info.SelectedChannelMenu
import io.getstream.chat.android.compose.ui.channels.list.ChannelItem
import io.getstream.chat.android.compose.ui.channels.list.ChannelList
import io.getstream.chat.android.compose.ui.components.SearchInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.offline.extensions.globalState

class ChannelsActivity : BaseConnectedActivity() {

    private val factory by lazy {
        ChannelViewModelFactory(
            ChatClient.instance(),
            QuerySortByField.descByName("last_updated"),
            null
        )
    }

    private val listViewModel: ChannelListViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * To use the Compose SDK/Components, simply call [setContent] to provide a Compose UI
         * definition, in which you gain access to all the UI component functions.
         *
         * You can use the default [ChannelsScreen] component that sets everything up for you,
         * or build a custom component yourself, like [MyCustomUi].
         */
        setContent {
            ChatTheme(dateFormatter = DataBranchApp.dateFormatter) {
                @Composable fun hubthing(navigator: DestinationsNavigator){
                    Button(onClick = { navigator.navigate(HubScreenDestination)}){Text("TO HUB")}}

                ChannelsScreen(
                    title = stringResource(id = R.string.app_name),
                    isShowingHeader = true,
                    isShowingSearch = true,
                    onItemClick = ::openMessages,
                    onBackPressed = ::finish,
                    onHeaderAvatarClick = {
                        ChatHelper.disconnectUser()

                        openUserLogin()
                    }
                )

//                MyCustomUiSimplified()
//                MyCustomUi()
            }
        }
    }


    /**
     * An example of a screen UI that's much more simple than the ChannelsScreen component, that features a custom
     * ChannelList item.
     */
    @Composable
    private fun MyCustomUiSimplified(navigator: DestinationsNavigator) {
        val user by ChatClient.instance().globalState.user.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Row{
                ChannelListHeader(
                    title = stringResource(id = R.string.app_name),
                    currentUser = user
                )

            }}
        ) {
            Button(onClick = { navigator.navigate(HubScreenDestination)}){Text("TO HUB2")}
            ChannelList(
                modifier = Modifier.fillMaxSize(),
                itemContent = {
                    CustomChannelListItem(channelItem = it, user = user)
                },
                divider = {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(0.5.dp)
                            .background(color = ChatTheme.colors.textLowEmphasis)
                    )
                }
            )
        }
    }

    /**
     * An example of a customized DefaultChannelItem component.
     */
    @Composable
    private fun CustomChannelListItem(channelItem: ChannelItemState, user: User?) {
        ChannelItem(
            channelItem = channelItem,
            currentUser = user,
            onChannelLongClick = {},
            onChannelClick = {},
            trailingContent = {
                Spacer(modifier = Modifier.width(8.dp))
            },
            centerContent = {
                Text(
                    text = ChatTheme.channelNameFormatter.formatChannelName(it.channel, user),
                    style = ChatTheme.typography.bodyBold,
                    color = ChatTheme.colors.textHighEmphasis
                )
            }
        )
    }

    /**
     * An example of what a custom UI can be, when not using [ChannelsScreen].
     *
     * It's important to note that if we want to use the [SelectedChannelMenu] to expose information and
     * options that the user can make with each channel, we need to use a [Box] and overlap the
     * two elements. This makes it easier as it's all presented in the same layer, rather than being
     * wrapped in drawers or more components.
     */
    @Composable
    private fun MyCustomUi() {
        var query by remember { mutableStateOf("") }

        val user by listViewModel.user.collectAsState()
        val delegatedSelectedChannel by listViewModel.selectedChannel
        val connectionState by listViewModel.connectionState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                ChannelListHeader(
                    title = stringResource(id = R.string.app_name),
                    currentUser = user,
                    connectionState = connectionState
                )

                SearchInput(
                    modifier = Modifier
                        .background(color = ChatTheme.colors.appBackground)
                        .fillMaxWidth()
                        .padding(8.dp),
                    query = query,
                    onValueChange = {
                        query = it
                        listViewModel.setSearchQuery(it)
                    }
                )

                ChannelList(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = listViewModel,
                    onChannelClick = ::openMessages,
                    onChannelLongClick = { listViewModel.selectChannel(it) }
                )
            }

            val selectedChannel = delegatedSelectedChannel
            if (selectedChannel != null) {
                SelectedChannelMenu(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(16.dp),
                    isMuted = listViewModel.isChannelMuted(selectedChannel.cid),
                    selectedChannel = selectedChannel,
                    currentUser = user,
                    onChannelOptionClick = { action -> listViewModel.performChannelAction(action) },
                    onDismiss = { listViewModel.dismissChannelAction() }
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun openMessages(channel: Channel) {
        startActivity(MessagesActivity.createIntent(this, channel.cid))
    }

    private fun openUserLogin() {
        finish()
        startActivity(UserLoginActivity.createIntent(this))
        overridePendingTransition(0, 0)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChannelsActivity::class.java)
        }
    }
}
*/