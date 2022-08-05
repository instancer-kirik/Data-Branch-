package com.instance.dataxbranch.social.StreamChat/*
package com.instance.dataxbranch.social.StreamChat


    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import io.getstream.chat.android.client.api.models.FilterObject

    import io.getstream.chat.android.client.api.models.querysort.QuerySortByReflection

    import io.getstream.chat.android.client.models.Channel
    import io.getstream.chat.android.client.models.Filters

 //   import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
    import com.instance.dataxbranch.social.StreamChat.ChannelListViewModel

    */
/**
     * Creates a channels view model factory
     *
     * @param filter how to filter the channels
     * @param sort how to sort the channels, defaults to last_updated
     * @param limit how many channels to return
     * @param messageLimit the number of messages to fetch for each channel
     *
     * @see Filters
     * @see QuerySort
     *//*


    public class ChannelListViewModelFactory @JvmOverloads constructor(
        private val filter: FilterObject = Filters.and(
            Filters.eq("type", "messaging"),

            Filters.or(Filters.notExists("draft"), Filters.ne("draft", true)),
        ),
        private val sort: QuerySortByReflection<Channel> = ChannelListViewModel.DEFAULT_SORT,
        private val limit: Int = 30,
        private val messageLimit: Int = 1,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ChannelListViewModel::class.java) {
                "ChannelListViewModelFactory can only create instances of ChannelListViewModel"
            }

            @Suppress("UNCHECKED_CAST")

            return ChannelListViewModel() as T
        }
    }
*/
