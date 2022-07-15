package com.instance.dataxbranch.social.StreamChat

import io.getstream.chat.android.compose.sample.ui.BaseConnectedActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.instance.dataxbranch.DataBranchApp
import com.instance.dataxbranch.R
import io.getstream.chat.android.common.state.DeletedMessageVisibility
import io.getstream.chat.android.common.state.MessageMode
import io.getstream.chat.android.common.state.Reply

import io.getstream.chat.android.compose.state.imagepreview.ImagePreviewResultType
import io.getstream.chat.android.compose.state.messages.SelectedMessageOptionsState
import io.getstream.chat.android.compose.state.messages.SelectedMessageReactionsPickerState
import io.getstream.chat.android.compose.state.messages.SelectedMessageReactionsState
import io.getstream.chat.android.compose.ui.components.composer.MessageInput
import io.getstream.chat.android.compose.ui.components.messageoptions.defaultMessageOptionsState
import io.getstream.chat.android.compose.ui.components.reactionpicker.ReactionsPicker
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedMessageMenu
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedReactionsMenu
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.messages.attachments.AttachmentsPicker
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.Reaction
import io.getstream.chat.android.common.state.MessageAction
import io.getstream.chat.android.common.state.React

import io.getstream.chat.android.compose.ui.components.SimpleMenu

import io.getstream.chat.android.compose.ui.components.reactionoptions.ReactionOptionItem


import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed

import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple

import androidx.compose.runtime.key
import androidx.compose.runtime.remember


import androidx.compose.ui.unit.dp
import com.getstream.sdk.chat.model.ModelType

import io.getstream.chat.android.compose.state.reactionoptions.ReactionOptionItemState

import io.getstream.chat.android.compose.ui.util.ReactionIcon
import io.getstream.chat.android.core.internal.InternalStreamChatApi
import java.util.*

@OptIn(ExperimentalFoundationApi::class)

class MessagesActivity : BaseConnectedActivity() {

    private val factory by lazy {
        MessagesViewModelFactory(
            context = this,
            channelId = intent.getStringExtra(KEY_CHANNEL_ID) ?: "",
            deletedMessageVisibility = DeletedMessageVisibility.ALWAYS_VISIBLE,
        )
    }

    private val listViewModel by viewModels<MessageListViewModel>(factoryProducer = { factory })

    private val attachmentsPickerViewModel by viewModels<AttachmentsPickerViewModel>(factoryProducer = { factory })
    private val composerViewModel by viewModels<MessageComposerViewModel>(factoryProducer = { factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val channelId = intent.getStringExtra(KEY_CHANNEL_ID) ?: return

        setContent {
            ChatTheme(dateFormatter = DataBranchApp.dateFormatter) {
                MessagesScreen(
                    channelId = channelId,
                    onBackPressed = { finish() },
                    onHeaderActionClick = {}
                )

                // MyCustomUi()
            }
        }
    }

    @Composable
    fun MyCustomUi() {
        val isShowingAttachments = attachmentsPickerViewModel.isShowingAttachments
        val selectedMessageState = listViewModel.currentMessagesState.selectedMessageState
        val user by listViewModel.user.collectAsState()
        val lazyListState = rememberLazyListState()

        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    MyCustomComposer()
                }
            ) {
                MessageList(
                    modifier = Modifier
                        .padding(it)
                        .background(ChatTheme.colors.appBackground)
                        .fillMaxSize(),
                    viewModel = listViewModel,
                    lazyListState = if (listViewModel.currentMessagesState.parentMessageId != null) rememberLazyListState() else lazyListState,
                    onThreadClick = { message ->
                        composerViewModel.setMessageMode(MessageMode.MessageThread(message))
                        listViewModel.openMessageThread(message)
                    },
                    onImagePreviewResult = { result ->
                        when (result?.resultType) {
                            ImagePreviewResultType.QUOTE -> {
                                val message = listViewModel.getMessageWithId(result.messageId)

                                if (message != null) {
                                    composerViewModel.performMessageAction(Reply(message))
                                }
                            }

                            ImagePreviewResultType.SHOW_IN_CHAT -> {
                            }
                            null -> Unit
                        }
                    }
                )
            }

            if (isShowingAttachments) {
                AttachmentsPicker(
                    attachmentsPickerViewModel = attachmentsPickerViewModel,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .height(350.dp),
                    onAttachmentsSelected = { attachments ->
                        attachmentsPickerViewModel.changeAttachmentState(false)
                        composerViewModel.addSelectedAttachments(attachments)
                    },
                    onDismiss = {
                        attachmentsPickerViewModel.changeAttachmentState(false)
                        attachmentsPickerViewModel.dismissAttachments()
                    }
                )
            }

            if (selectedMessageState != null) {
                val selectedMessage = selectedMessageState.message
                when (selectedMessageState) {
                    is SelectedMessageOptionsState -> {
                        SelectedMessageMenu(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 20.dp)
                                .wrapContentSize(),
                            shape = ChatTheme.shapes.attachment,
                            messageOptions = defaultMessageOptionsState(
                                selectedMessage = selectedMessage,
                                currentUser = user,
                                isInThread = listViewModel.isInThread,
                                ownCapabilities = selectedMessageState.ownCapabilities
                            ),
                            message = selectedMessage,
                            ownCapabilities = selectedMessageState.ownCapabilities,
                            onMessageAction = { action ->
                                composerViewModel.performMessageAction(action)
                                listViewModel.performMessageAction(action)
                            },
                            onShowMoreReactionsSelected = {
                                listViewModel.selectExtendedReactions(selectedMessage)
                            },
                            onDismiss = { listViewModel.removeOverlay() }
                        )
                    }
                    is SelectedMessageReactionsState -> {
                        SelectedReactionsMenu(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 20.dp)
                                .wrapContentSize(),
                            shape = ChatTheme.shapes.attachment,
                            message = selectedMessage,
                            currentUser = user,
                            onMessageAction = { action ->
                                composerViewModel.performMessageAction(action)
                                listViewModel.performMessageAction(action)
                            },
                            onShowMoreReactionsSelected = {
                                listViewModel.selectExtendedReactions(selectedMessage)
                            },
                            onDismiss = { listViewModel.removeOverlay() },
                            ownCapabilities = selectedMessageState.ownCapabilities
                        )
                    }
                    is SelectedMessageReactionsPickerState -> {

                        ReactionsPicker(

                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 20.dp)
                                .wrapContentSize(),
                            shape = ChatTheme.shapes.attachment,
                            message = selectedMessage,
                            onMessageAction = { action ->
                                composerViewModel.performMessageAction(action)
                                listViewModel.performMessageAction(action)
                            },
                            onDismiss = { listViewModel.removeOverlay() }
                        )
                    }
                    else -> {}
                }
            }
        }
    }
    internal object PreviewMessageData {

        @OptIn(InternalStreamChatApi::class)
        val message1: Message = Message().apply {
            id = "message-id-1"
            text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit."
            createdAt = Date()
            type = ModelType.message_regular
        }

        @OptIn(InternalStreamChatApi::class)
        val message2: Message = Message().apply {
            id = "message-id-2"
            text = "Aenean commodo ligula eget dolor."
            createdAt = Date()
            type = ModelType.message_regular
        }

        @OptIn(InternalStreamChatApi::class)
        val messageWithOwnReaction: Message = Message().apply {
            id = "message-id-3"
            text = "Pellentesque leo dui, finibus et nibh et, congue aliquam lectus"
            createdAt = Date()
            type = ModelType.message_regular
            ownReactions = mutableListOf(Reaction(messageId = this.id, type = "haha"))
        }
    }

    @ExperimentalFoundationApi
    @Preview(showBackground = true, name = "ReactionPicker Preview")
    @Composable
    fun ReactionPickerPreview() {
        ChatTheme {
            ReactionsPicker(
                message = PreviewMessageData.messageWithOwnReaction,
                onMessageAction = {}
            )
        }
    }
//private fun ReactionsPicker(cells: GridCells, modifier: Modifier, shape: Shape, message: Message, onMessageAction: (MessageAction) -> Unit, onDismiss: () -> Unit) {
    private val DefaultNumberOfReactions = 5

        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        fun ReactionsPicker(
            message: Message,
            onMessageAction: (MessageAction) -> Unit,
            modifier: Modifier = Modifier,
            shape: Shape = ChatTheme.shapes.bottomSheet,
            overlayColor: Color = ChatTheme.colors.overlay,
            cells: GridCells = GridCells.Fixed(DefaultNumberOfReactions),
            onDismiss: () -> Unit = {},
            reactionTypes: Map<String, ReactionIcon> = ChatTheme.reactionIconFactory.createReactionIcons(),
            headerContent: @Composable ColumnScope.() -> Unit = {},
            centerContent: @Composable ColumnScope.() -> Unit = {
                DefaultReactionsPickerCenterContent(
                    message = message,
                    onMessageAction = onMessageAction,
                    cells = cells,
                    reactionTypes = reactionTypes
                )
            },
        ) {
            SimpleMenu(
                modifier = modifier,
                shape = shape,
                overlayColor = overlayColor,
                headerContent = headerContent,
                centerContent = centerContent,
                onDismiss = onDismiss
            )
        }

        /**
         * The Default center content for the [ReactionsPicker]. Shows all available reactions.
         *
         * @param message The selected message.
         * @param onMessageAction Handler that propagates click events on each item.
         * @param cells Describes the way cells are formed inside [ExtendedReactionsOptions].
         * @param reactionTypes The available reactions.
         */
        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        fun DefaultReactionsPickerCenterContent(
            message: Message,
            onMessageAction: (MessageAction) -> Unit,
            cells: GridCells = GridCells.Fixed(DefaultNumberOfReactions),
            reactionTypes: Map<String, ReactionIcon> = ChatTheme.reactionIconFactory.createReactionIcons(),
        ) {
            ExtendedReactionsOptions(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
                reactionTypes = reactionTypes,
                ownReactions = message.ownReactions,
                onReactionOptionSelected = { reactionOptionItemState ->
                    onMessageAction(
                        React(
                            reaction = Reaction(
                                messageId = message.id,
                                reactionOptionItemState.type
                            ),
                            message = message
                        )
                    )
                },
                cells = cells
            )
        }

        /**
         * Preview of [ReactionsPicker] with a reaction selected.
         */



    @Composable
    fun MyCustomComposer() {
        MessageComposer(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            viewModel = composerViewModel,
            integrations = {},
            input = { inputState ->
                MessageInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(7f)
                        .padding(start = 8.dp),
                    messageComposerState = inputState,
                    onValueChange = { composerViewModel.setMessageInput(it) },
                    onAttachmentRemoved = { composerViewModel.removeSelectedAttachment(it) },
                    label = {
                        Row(
                            Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.stream_compose_ic_gallery),
                                contentDescription = null
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = "Type something",
                                color = ChatTheme.colors.textLowEmphasis
                            )
                        }
                    },
                    innerTrailingContent = {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple()
                                ) {
                                    val state = composerViewModel.messageComposerState.value

                                    composerViewModel.sendMessage(
                                        composerViewModel.buildNewMessage(
                                            state.inputValue, state.attachments
                                        )
                                    )
                                },
                            painter = painterResource(id = R.drawable.stream_compose_ic_send),
                            tint = ChatTheme.colors.primaryAccent,
                            contentDescription = null
                        )
                    },
                )
            },
            trailingContent = { Spacer(modifier = Modifier.size(8.dp)) }
        )
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channelId"

        fun createIntent(context: Context, channelId: String): Intent {
            return Intent(context, MessagesActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}

/**
 * The default maximum number of columns when showing reactions and users.
 */
private const val DefaultNumberOfColumns = 5

/**
 * Displays all available reactions a user can set on a message.
 *
 * @param ownReactions A list of user's own reactions.
 * @param onReactionOptionSelected Handler that propagates click events on each item.
 * @param modifier Modifier for styling.
 * @param cells Describes the way cells are formed inside [ExtendedReactionsOptions].
 * @param itemContent Composable that allows the user to customize the individual items shown
 * in [ExtendedReactionsOptions]. By default it shows individual reactions.
 */
@ExperimentalFoundationApi
@Composable
fun ExtendedReactionsOptions(
    ownReactions: List<Reaction>,
    onReactionOptionSelected: (ReactionOptionItemState) -> Unit,
    modifier: Modifier = Modifier,
    cells: GridCells = GridCells.Fixed(DefaultNumberOfColumns),
    reactionTypes: Map<String, ReactionIcon> = ChatTheme.reactionIconFactory.createReactionIcons(),
    itemContent: @Composable LazyGridScope.(ReactionOptionItemState) -> Unit = { option ->
        DefaultExtendedReactionsItemContent(
            option = option,
            onReactionOptionSelected = onReactionOptionSelected
        )
    },
) {
    val options = reactionTypes.entries.map { (type, reactionIcon) ->
        val isSelected = ownReactions.any { ownReaction -> ownReaction.type == type }
        ReactionOptionItemState(
            painter = reactionIcon.getPainter(isSelected),
            type = type
        )
    }

    LazyVerticalGrid(modifier = modifier, columns = cells) {
        itemsIndexed(options){ count,item ->
            key(item.type) {item.painter
            }
        }
    }
}

/**
 * The default item content inside [ExtendedReactionsOptions]. Shows an individual reaction.
 *
 * @param option Individual reaction option.
 * @param onReactionOptionSelected Handler that propagates click events on each item.
 */
@Composable
internal fun DefaultExtendedReactionsItemContent(
    option: ReactionOptionItemState,
    onReactionOptionSelected: (ReactionOptionItemState) -> Unit,
) {
    ReactionOptionItem(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = { onReactionOptionSelected(option) }
            ),
        option = option
    )
}

/**
 * Preview for [ExtendedReactionsOptions] with no reaction selected.
 */
@ExperimentalFoundationApi
@Preview(showBackground = true, name = "ExtendedReactionOptions Preview")
@Composable
internal fun ExtendedReactionOptionsPreview() {
    ChatTheme {
        ExtendedReactionsOptions(
            ownReactions = listOf(),
            onReactionOptionSelected = {}
        )
    }
}

/**
 * Preview for [ExtendedReactionsOptions] with a selected reaction.
 */
@ExperimentalFoundationApi
@Preview(showBackground = true, name = "ExtendedReactionOptions Preview (With Own Reaction)")
@Composable
internal fun ExtendedReactionOptionsWithOwnReactionPreview() {
    ChatTheme {
        ExtendedReactionsOptions(
            ownReactions = listOf(
                Reaction(
                    messageId = "messageId",
                    type = "haha"
                )
            ),
            onReactionOptionSelected = {}
        )
    }
}

/*package com.instance.dataxbranch.social.StreamChat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.common.state.MessageMode
import io.getstream.chat.android.compose.state.messages.SelectedMessageOptionsState
import io.getstream.chat.android.compose.state.messages.SelectedMessageReactionsState
import io.getstream.chat.android.compose.ui.components.messageoptions.defaultMessageOptionsState
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedMessageMenu
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedReactionsMenu
import io.getstream.chat.android.compose.ui.messages.attachments.AttachmentsPicker
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.StreamShapes
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

    class MessagesActivity : ComponentActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            // 1 - Load the ID of the selected channel
            val channelId = intent.getStringExtra(KEY_CHANNEL_ID)

            if (channelId == null) {
                finish()
                return
            }

            // 2 - Add the MessagesScreen to your UI
            setContent {
                ChatTheme(
                    shapes = StreamShapes.defaultShapes().copy(
                        avatar = RoundedCornerShape(8.dp),
                        attachment = RoundedCornerShape(16.dp),
                        myMessageBubble = RoundedCornerShape(16.dp),
                        otherMessageBubble = RoundedCornerShape(16.dp),
                        inputField = RectangleShape,
                    )
                )  {
                    MessagesScreen(
                        channelId = channelId,
                        messageLimit = 30,
                        onBackPressed = { finish() }
                    )
                }
            }
        }

        // Build the ViewModel factory
        private val factory by lazy {
            MessagesViewModelFactory(
                context = this,
                channelId = intent.getStringExtra(KEY_CHANNEL_ID) ?: "",
            )
        }

        // Build the required ViewModels, using the 'factory'
        val listViewModel: MessageListViewModel by viewModels { factory }
        val attachmentsPickerViewModel: AttachmentsPickerViewModel by viewModels { factory }
        val composerViewModel: MessageComposerViewModel by viewModels { factory }
        // 3 - Create an intent to start this Activity, with a given channelId
        companion object {
            private const val KEY_CHANNEL_ID = "channelId"

            fun getIntent(context: Context, channelId: String): Intent {
                return Intent(context, MessagesActivity::class.java).apply {
                    putExtra(KEY_CHANNEL_ID, channelId)
                }
            }
        }

        @Composable
        fun MyCustomUi() {
            // 1 - Load the data
            val isShowingAttachments = attachmentsPickerViewModel.isShowingAttachments
            val selectedMessageState = listViewModel.currentMessagesState.selectedMessageState
            val user by listViewModel.user.collectAsState()

            Box(modifier = Modifier.fillMaxSize()) { // 2 - Define the root
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MessageComposer( // 3 - Add a composer
                            composerViewModel,
                            onAttachmentsClick = {
                                attachmentsPickerViewModel.changeAttachmentState(true)
                            }
                        )
                    }
                ) {
                    MessageList( // 4 - Build the MessageList and connect the actions
                        modifier = Modifier
                            .background(ChatTheme.colors.appBackground)
                            .padding(it)
                            .fillMaxSize(),
                        viewModel = listViewModel,
                        onThreadClick = { message ->
                            composerViewModel.setMessageMode(MessageMode.MessageThread(message))
                            listViewModel.openMessageThread(message)
                        }
                    )
                }

                // 5 - Show attachments when necessary
                if (isShowingAttachments) {
                    AttachmentsPicker(
                        attachmentsPickerViewModel = attachmentsPickerViewModel,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(350.dp),
                        onAttachmentsSelected = { attachments ->
                            attachmentsPickerViewModel.changeAttachmentState(false)
                            composerViewModel.addSelectedAttachments(attachments)
                        },
                        onDismiss = {
                            attachmentsPickerViewModel.changeAttachmentState(false)
                            attachmentsPickerViewModel.dismissAttachments()
                        }
                    )
                }

                // 6 - Show the overlay if we've selected a message
                if (selectedMessageState != null) {
                    val selectedMessage = selectedMessageState.message
                    if (selectedMessageState is SelectedMessageOptionsState) {
                        SelectedMessageMenu(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 20.dp)
                                .wrapContentSize(),
                            shape = ChatTheme.shapes.attachment,
                            messageOptions = defaultMessageOptionsState(
                                selectedMessage,
                                user,
                                listViewModel.isInThread,
                                selectedMessageState.ownCapabilities
                            ),
                            message = selectedMessage,
                            onMessageAction = { action ->
                                composerViewModel.performMessageAction(action)
                                listViewModel.performMessageAction(action)
                            },
                            onShowMoreReactionsSelected = {
                                listViewModel.selectExtendedReactions(selectedMessage)
                            },
                            onDismiss = { listViewModel.removeOverlay() },
                            ownCapabilities = selectedMessageState.ownCapabilities
                        )
                    } else if (selectedMessageState is SelectedMessageReactionsState) {
                        SelectedReactionsMenu(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 20.dp)
                                .wrapContentSize(),
                            shape = ChatTheme.shapes.attachment,
                            message = selectedMessage,
                            currentUser = user,
                            onMessageAction = { action ->
                                composerViewModel.performMessageAction(action)
                                listViewModel.performMessageAction(action)
                            },
                            onShowMoreReactionsSelected = {
                                listViewModel.selectExtendedReactions(selectedMessage)
                            },
                            onDismiss = { listViewModel.removeOverlay() },
                            ownCapabilities = selectedMessageState.ownCapabilities
                        )
                    }
                }
            }
        }
    }
*/