/*
package com.instance.dataxbranch.social.StreamChat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*

import io.getstream.chat.android.core.internal.exhaustive

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MediatorLiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.Transformations
    import androidx.lifecycle.ViewModel

import com.getstream.sdk.chat.utils.extensions.defaultChannelListFilter
import com.instance.dataxbranch.core.Constants.TAG
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.ChatClient.Companion.DEFAULT_SORT
import io.getstream.chat.android.client.api.models.FilterObject
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.api.models.querysort.QuerySortByReflection

import io.getstream.chat.android.client.api.models.querysort.QuerySorter
import io.getstream.chat.android.client.call.enqueue
import io.getstream.chat.android.client.call.toUnitCall
import io.getstream.chat.android.client.errors.ChatError
import io.getstream.chat.android.client.logger.ChatLogger

import io.getstream.chat.android.client.models.*
import io.getstream.chat.android.compose.state.QueryConfig
import io.getstream.chat.android.compose.state.channels.list.Cancel
import io.getstream.chat.android.compose.state.channels.list.ChannelAction
import io.getstream.chat.android.compose.state.channels.list.ChannelItemState
import io.getstream.chat.android.compose.state.channels.list.ChannelsState
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.core.internal.InternalStreamChatApi
import io.getstream.chat.android.core.internal.exhaustive
    import io.getstream.chat.android.livedata.utils.Event
import io.getstream.chat.android.offline.extensions.globalState
import io.getstream.chat.android.offline.extensions.queryChannelsAsState
import io.getstream.chat.android.offline.plugin.state.querychannels.ChannelsStateData
import io.getstream.chat.android.offline.plugin.state.querychannels.QueryChannelsState

import io.getstream.logging.StreamLog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

*/
/**
     * ViewModel class for [io.getstream.chat.android.ui.channel.list.ChannelListView].
     * Responsible for keeping the channels list up to date.
     * Can be bound to the view using [ChannelListViewModel.bindView] function.
     * @param chatDomain entry point for all livedata & offline operations
     * @param filter filter for querying channels, should never be empty
     * @param sort defines the ordering of the channels
     * @param limit the maximum number of channels to fetch
     * @param messageLimit the number of messages to fetch for each channel
     *//*


    //ChannelListViewModel class  {
    public class ChannelListViewModel(
        //private val chatDomain: ChatDomain = ChatDomain.instance(),
    private val filter: FilterObject = Filters.and(
            Filters.eq("type", "messaging"),
           // FilterUtils.userFilter(chatDomain),
            Filters.or(Filters.notExists("draft"), Filters.ne("draft", true)),
        ),
    private val sort: QuerySorter<Member> = DEFAULT_SORT,
    private val limit: Int = 30,

    public val chatClient: ChatClient,
    initialSort: QuerySorter<Channel>,
    initialFilters: FilterObject?,
    private val channelLimit: Int = ChannelListViewModel.DEFAULT_CHANNEL_LIMIT,
    private val memberLimit: Int = ChannelListViewModel.DEFAULT_MEMBER_LIMIT,
    private val messageLimit: Int = ChannelListViewModel.DEFAULT_MESSAGE_LIMIT,
    ) : ViewModel() {
        private val stateMerger = MediatorLiveData<State>()
        public val state: LiveData<State> = stateMerger
        public val typingEvents: LiveData<TypingEvent>
            get() // chatDomain.typingUpdates.asLiveData()

        private val paginationStateMerger = MediatorLiveData<PaginationState>()
        public val paginationState: LiveData<PaginationState> = Transformations.distinctUntilChanged(paginationStateMerger)
        private val _errorEvents: MutableLiveData<Event<ErrorEvent>> = MutableLiveData()
        public val errorEvents: LiveData<Event<ErrorEvent>> = _errorEvents

        init {
            stateMerger.value = INITIAL_STATE

            queryChannels(filter, sort, limit, messageLimit).enqueue { queryChannelsControllerResult ->
                if (queryChannelsControllerResult.isSuccess) {
                    val queryChannelsController = queryChannelsControllerResult.data()

                    val channelState = chatDomain.user.filterNotNull().flatMapConcat { currentUser ->
                        queryChannelsController.channelsState.map { channelState ->
                            channelState to currentUser
                        }
                    }.map { (channelState, currentUser) ->
                        handleChannelState(channelState, currentUser)
                    }.asLiveData()

                    stateMerger.addSource(channelState) { state -> stateMerger.value = state }

                    stateMerger.addSource(queryChannelsController.mutedChannelIds.asLiveData()) { mutedChannels ->
                        val state = stateMerger.value

                        if (state?.channels?.isNotEmpty() == true) {
                            stateMerger.value = state.copy(channels = parseMutedChannels(state.channels, mutedChannels))
                        } else {
                            stateMerger.value = state?.copy()
                        }
                    }

                    paginationStateMerger.addSource(queryChannelsController.loadingMore.asLiveData()) { loadingMore ->
                        setPaginationState { copy(loadingMore = loadingMore) }
                    }
                    paginationStateMerger.addSource(queryChannelsController.endOfChannels.asLiveData()) { endOfChannels ->
                        setPaginationState { copy(endOfChannels = endOfChannels) }
                    }
                }
            }
        }

        */
/**
         * State flow that keeps the value of the current [FilterObject] for channels.
         *//*

        private val filterFlow: MutableStateFlow<FilterObject?> = MutableStateFlow(initialFilters)

        private val logger = StreamLog.getLogger("ChannelListVM")

        */
/**
         * State flow that keeps the value of the current [QuerySorter] for channels.
         *//*

        private val querySortFlow: MutableStateFlow<QuerySorter<Channel>> = MutableStateFlow(initialSort)

        */
/**
         * The currently active query configuration, stored in a [MutableStateFlow]. It's created using
         * the `initialFilters` parameter and the initial sort, but can be changed.
         *//*

        private val queryConfigFlow = filterFlow.filterNotNull().combine(querySortFlow) { filters, sort ->
            QueryConfig(filters = filters, querySort = sort)
        }

        */
/**
         * The current state of the search input. When changed, it emits a new value in a flow, which
         * queries and loads new data.
         *//*

        private val searchQuery = MutableStateFlow("")

        */
/**
         * The current state of the channels screen. It holds all the information required to render the UI.
         *//*

        public var channelsState: ChannelsState by mutableStateOf(ChannelsState())
            private set

        */
/**
         * Currently selected channel, if any. Used to show the bottom drawer information when long
         * tapping on a list item.
         *//*

        public var selectedChannel: MutableState<Channel?> = mutableStateOf(null)
            private set

        */
/**
         * Currently active channel action, if any. Used to show a dialog for deleting or leaving a
         * channel/conversation.
         *//*

        public var activeChannelAction: ChannelAction? by mutableStateOf(null)
            private set

        */
/**
         * The state of our network connection - if we're online, connecting or offline.
         *//*

        public val connectionState: StateFlow<ConnectionState> = chatClient.clientState.connectionState

        */
/**
         * The state of the currently logged in user.
         *//*

        public val user: StateFlow<User?> = chatClient.clientState.user

        */
/**
         * Gives us the information about the list of channels mutes by the current user.
         *//*

        public val channelMutes: StateFlow<List<ChannelMute>> = chatClient.globalState.channelMutes

        */
/**
         * Builds the default channel filter, which represents "messaging" channels that the current user is a part of.
         *//*

        @OptIn(InternalStreamChatApi::class)
        private fun buildDefaultFilter(): Flow<FilterObject> {
            return chatClient.clientState.user.map(Filters::defaultChannelListFilter).filterNotNull()
        }

        */
/**
         * Checks if the channel is muted for the current user.
         *
         * @param cid The CID of the channel that needs to be checked.
         * @return True if the channel is muted for the current user.
         *//*

        public fun isChannelMuted(cid: String): Boolean {
            return channelMutes.value.any { cid == it.channel.cid }
        }

        */
/**
         * Current query channels state that contains filter, sort and other states related to channels query.
         *//*

        private var queryChannelsState: StateFlow<QueryChannelsState?> = MutableStateFlow(null)

        */
/**
         * Combines the latest search query and filter to fetch channels and emit them to the UI.
         *//*

        init {
            if (initialFilters == null) {
                viewModelScope.launch {
                    val filter = buildDefaultFilter().first()

                    this@ChannelListViewModel.filterFlow.value = filter
                }
            }

            viewModelScope.launch {
                init()
            }
        }

        */
/**
         * Makes the initial query to request channels and starts observing state changes.
         *//*

        private suspend fun init() {
            logger.d { "Initializing ChannelListViewModel" }

            searchQuery.combine(queryConfigFlow) { query, config -> query to config }
                .collectLatest { (query, config) ->
                    val queryChannelsRequest = QueryChannelsRequest(
                        filter = createQueryChannelsFilter(config.filters, query),
                        querySort = config.querySort,
                        limit = channelLimit,
                        messageLimit = messageLimit,
                        memberLimit = memberLimit,
                    )

                    logger.d { "Querying channels as state" }
                    queryChannelsState = chatClient.queryChannelsAsState(queryChannelsRequest, viewModelScope)
                    observeChannels(searchQuery = query)
                }
        }

        */
/**
         * Creates a filter that is used to query channels.
         *
         * If the [searchQuery] is empty, then returns the original [filter] provided by the user.
         * Otherwise, returns a wrapped [filter] that also checks that the channel name match the
         * [searchQuery].
         *
         * @param filter The filter that was passed by the user.
         * @param searchQuery The search query used to filter the channels.
         *
         * @return The filter that will be used to query channels.
         *//*

        private fun createQueryChannelsFilter(filter: FilterObject, searchQuery: String): FilterObject {
            return if (searchQuery.isNotEmpty()) {
                Filters.and(
                    filter,
                    Filters.or(
                        Filters.and(
                            Filters.autocomplete("member.user.name", searchQuery),
                            Filters.notExists("name")
                        ),
                        Filters.autocomplete("name", searchQuery)
                    )
                )
            } else {
                filter
            }
        }

        */
/**
         * Kicks off operations required to combine and build the [ChannelsState] object for the UI.
         *
         * It connects the 'loadingMore', 'channelsState' and 'endOfChannels' properties from the [queryChannelsState].
         * @param searchQuery The search query string used to search channels.
         *//*

        private suspend fun observeChannels(searchQuery: String) {
            logger.d { "ViewModel is observing channels. When state is available, it will be notified" }
            queryChannelsState.filterNotNull().collectLatest { queryChannelsState ->
                channelMutes.combine(queryChannelsState.channelsStateData, ::Pair)
                    .map { (channelMutes, state) ->
                        when (state) {
                            ChannelsStateData.NoQueryActive,
                            ChannelsStateData.Loading,
                            -> channelsState.copy(
                                isLoading = true,
                                searchQuery = searchQuery
                            ).also {
                                logger.d { "Loading state for query" }
                            }
                            ChannelsStateData.OfflineNoResults -> {
                                logger.d { "No offline results. Channels are empty" }
                                channelsState.copy(
                                    isLoading = false,
                                    channelItems = emptyList(),
                                    searchQuery = searchQuery
                                )
                            }
                            is ChannelsStateData.Result -> {
                                logger.d { "Received result for state of channels" }
                                channelsState.copy(
                                    isLoading = false,
                                    channelItems = createChannelItems(state.channels, channelMutes),
                                    isLoadingMore = false,
                                    endOfChannels = queryChannelsState.endOfChannels.value,
                                    searchQuery = searchQuery
                                )
                            }
                        }
                    }.collectLatest { newState -> channelsState = newState }
            }
        }

        */
/**
         * Changes the currently selected channel state. This updates the UI state and allows us to observe
         * the state change.
         *//*

        public fun selectChannel(channel: Channel?) {
            this.selectedChannel.value = channel
        }

        */
/**
         * Changes the current query state. This updates the data flow and triggers another query operation.
         *
         * The new operation will hold the channels that match the new query.
         *//*

        public fun setSearchQuery(newQuery: String) {
            this.searchQuery.value = newQuery
        }

        */
/**
         * Allows for the change of filters used for channel queries.
         *
         * Use this if you need to support runtime filter changes, through custom filters UI.
         *
         * Warning: The filter that's applied will override the `initialFilters` set through the constructor.
         *
         * @param newFilters The new filters to be used as a baseline for filtering channels.
         *//*

        public fun setFilters(newFilters: FilterObject) {
            this.filterFlow.tryEmit(value = newFilters)
        }

        */
/**
         * Allows for the change of the query sort used for channel queries.
         *
         * Use this if you need to support runtime sort changes, through custom sort UI.
         *//*

        public fun setQuerySort(querySort: QuerySorter<Channel>) {
            this.querySortFlow.tryEmit(value = querySort)
        }

        */
/**
         * Loads more data when the user reaches the end of the channels list.
         *//*

        public fun loadMore() {
            logger.d { "Loading more channels" }

            if (chatClient.clientState.isOffline) return
            val currentConfig = QueryConfig(
                filters = filterFlow.value ?: return,
                querySort = querySortFlow.value
            )

            channelsState = channelsState.copy(isLoadingMore = true)

            val currentQuery = queryChannelsState.value?.nextPageRequest?.value

            currentQuery?.copy(
                filter = createQueryChannelsFilter(currentConfig.filters, searchQuery.value),
                querySort = currentConfig.querySort
            )?.let { queryChannelsRequest ->
                chatClient.queryChannels(queryChannelsRequest).enqueue()
            }
        }

        */
/**
         * Clears the active action if we've chosen [Cancel], otherwise, stores the selected action as
         * the currently active action, in [activeChannelAction].
         *
         * It also removes the [selectedChannel] if the action is [Cancel].
         *
         * @param channelAction The selected action.
         *//*

        public fun performChannelAction(channelAction: ChannelAction) {
            if (channelAction is Cancel) {
                selectedChannel.value = null
            }

            activeChannelAction = if (channelAction == Cancel) {
                null
            } else {
                channelAction
            }
        }

        */
/**
         * Mutes a channel.
         *
         * @param channel The channel to mute.
         *//*

        public fun muteChannel(channel: Channel) {
            dismissChannelAction()

            chatClient.muteChannel(channel.type, channel.id).enqueue()
        }

        */
/**
         * Unmutes a channel.
         *
         * @param channel The channel to unmute.
         *//*

        public fun unmuteChannel(channel: Channel) {
            dismissChannelAction()

            chatClient.unmuteChannel(channel.type, channel.id).enqueue()
        }

        */
/**
         * Deletes a channel, after the user chooses the delete [ChannelAction]. It also removes the
         * [activeChannelAction], to remove the dialog from the UI.
         *
         * @param channel The channel to delete.
         *//*

        @OptIn(InternalStreamChatApi::class)
        public fun deleteConversation(channel: Channel) {
            dismissChannelAction()

            chatClient.channel(channel.cid).delete().toUnitCall().enqueue()
        }

        */
/**
         * Leaves a channel, after the user chooses the leave [ChannelAction]. It also removes the
         * [activeChannelAction], to remove the dialog from the UI.
         *
         * @param channel The channel to leave.
         *//*

        public fun leaveGroup(channel: Channel) {
            dismissChannelAction()

            chatClient.getCurrentUser()?.let { user ->
                chatClient.channel(channel.type, channel.id).removeMembers(listOf(user.id)).enqueue()
            }
        }

        */
/**
         * Dismisses the [activeChannelAction] and removes it from the UI.
         *//*

        public fun dismissChannelAction() {
            activeChannelAction = null
            selectedChannel.value = null
        }

        */
/**
         * Creates a list of [ChannelItemState] that represents channel items we show in the list of channels.
         *
         * @param channels The channels to show.
         * @param channelMutes The list of channels muted for the current user.
         *
         *//*

        private fun createChannelItems(channels: List<Channel>, channelMutes: List<ChannelMute>): List<ChannelItemState> {
            val mutedChannelIds = channelMutes.map { channelMute -> channelMute.channel.cid }.toSet()
            return channels.map { ChannelItemState(it, it.cid in mutedChannelIds) }
        }

        public companion object {



            */
/**
             * Default value of number of channels to return when querying channels.
             *//*

            internal const val DEFAULT_CHANNEL_LIMIT = 30

            */
/**
             * Default value of the number of messages to include in each channel when querying channels.
             *//*

            internal const val DEFAULT_MESSAGE_LIMIT = 1

            */
/**
             * Default value of the number of members to include in each channel when querying channels.
             *//*

            internal const val DEFAULT_MEMBER_LIMIT = 30

                private val INITIAL_STATE: State = State(isLoading = true, channels = emptyList())

        }
        private fun handleChannelState(
            channelState: QueryChannelsController.ChannelsState,
            currentUser: User,
        ): State {
            return when (channelState) {
                is QueryChannelsController.ChannelsState.NoQueryActive,
                is QueryChannelsController.ChannelsState.Loading,
                -> State(isLoading = true, emptyList())
                is QueryChannelsController.ChannelsState.OfflineNoResults -> State(
                    isLoading = false,
                    channels = emptyList(),
                )
                is QueryChannelsController.ChannelsState.Result ->
                    State(
                        isLoading = false,
                        channels = parseMutedChannels(
                            channelState.channels,
                            currentUser.channelMutes.map { channelMute -> channelMute.channel.id }
                        ),
                    )
            }
        }

    */
/*

    public fun leaveChannel(channel: Channel) {
        chatDomain.leaveChannel(channel.cid).enqueue(
            onError = { _errorEvents.postValue(Event(ErrorEvent.LeaveChannelError(it))) }
        )
    }

    public fun deleteChannel(channel: Channel) {
        chatDomain.deleteChannel(channel.cid).enqueue(
            onError = { _errorEvents.postValue(Event(ErrorEvent.DeleteChannelError(it))) }
        )
    }

    public fun hideChannel(channel: Channel) {
        chatDomain.hideChannel(channel.cid, true).enqueue(
            onError = { _errorEvents.postValue(Event(ErrorEvent.HideChannelError(it))) }
        )
    }

    public fun markAllRead() {
        chatDomain.markAllRead().enqueue()
    }

    private fun requestMoreChannels() {
        chatDomain.queryChannelsLoadMore(filter, sort).enqueue()
    }
*//*


        private fun setPaginationState(reducer: PaginationState.() -> PaginationState) {
            paginationStateMerger.value = reducer(paginationStateMerger.value ?: PaginationState())
        }

        public data class State(val isLoading: Boolean, val channels: List<Channel>)

        private fun parseMutedChannels(
            channelsMap: List<Channel>,
            channelMutesIds: List<String>?,
        ): List<Channel> {
            Log.d(TAG,"IN CHANNEL LIST VIEWMODEL")
            return channelsMap.map { channel ->
                channel.copy().apply {
                   // muted = channelMutesIds?.contains(channel.id) ?: false
                }
            }
        }

        public data class PaginationState(
            val loadingMore: Boolean = false,
            val endOfChannels: Boolean = false,
        )

        public sealed class Action {
            public object ReachedEndOfList : Action()
        }

        public sealed class ErrorEvent(public open val chatError: ChatError) {
            public data class LeaveChannelError(override val chatError: ChatError) : ErrorEvent(chatError)
            public data class DeleteChannelError(override val chatError: ChatError) : ErrorEvent(chatError)
            public data class HideChannelError(override val chatError: ChatError) : ErrorEvent(chatError)
        }


    }
*/
/*
    private fun userFilter(n): FilterObject {
        return chatDomain.user.value?.id?.let { id ->
            Filters.`in`("members", id)
        } ?: Filters.neutral().also {
            ChatLogger.get("ChannelListViewModel")
                .logE("User is not set in ChatDomain, default filter for ChannelListViewModel won't work")
        }*//*


*/
