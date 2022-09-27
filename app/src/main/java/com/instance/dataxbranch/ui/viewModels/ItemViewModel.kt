package com.instance.dataxbranch.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.daos.ItemDao
import com.instance.dataxbranch.data.entities.ItemEntity
import com.instance.dataxbranch.data.local.CharacterWithStuff
import com.instance.dataxbranch.data.repository.GeneralRepository
import com.instance.dataxbranch.domain.use_case.UseCases
import com.instance.dataxbranch.quests.QuestWithObjectives
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    val appDatabase: AppDatabase,
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle,
    val generalRepository: GeneralRepository,
    val dao: ItemDao

    ): ViewModel() {
    lateinit var selectedItem: ItemEntity
    var selectedCharacterWithStuff: CharacterWithStuff=getSelectedCharacter()
   /* private val downloadQueue: MutableMap<Int, Flow<Int>> = mutableMapOf()
    var openDialogState = mutableStateOf(false)
    var openDialogState2 = mutableStateOf(false)
    var openDialogState3 = mutableStateOf(false)
    var termsDialogState = mutableStateOf(false)
    var refreshWebview = mutableStateOf(false)
    var downloadCloudDialog = mutableStateOf(false)
    var singleConditionsDialog = mutableStateOf(true)
    var characterDialogState = mutableStateOf(false)
    var allabilities = mutableStateOf(false)*/

    val handyString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        refresh()
        selectedItem = if(getItems().isNotEmpty()){
            getItems()[0]
        } else{
            ItemEntity()
        }
    }
    fun refresh(){
        generalRepository.itemRepository.refresh()
    }
    fun getItems():Array<ItemEntity>{
        return generalRepository.itemRepository.getitems()
    }
    fun addItemEntity(name:String){
        generalRepository.itemRepository.insertItemEntity(ItemEntity(name=name))
    }
    fun addItemToInventory(item:ItemEntity=selectedItem){
        generalRepository.putItemOnCharacter(item)
    }
    fun getSelectedCharacter():CharacterWithStuff{
        return generalRepository.selectedCharacterWithStuff
    }


    fun onCheckboxChecked(quest: QuestWithObjectives, checked: Boolean,):String? {
        //Log.d(TAG, "$quest is is $checked   "+quest.quest.completed.toString())
        //dateLastDone = getNow()
        viewModelScope.launch {

            generalRepository.questsRepository.update(quest.quest.copy(completed = checked, ))
        }
        if(checked){
            return "HUH @ItemViewModel" //onQuestCompleted(quest)
        }
        return null
    }
    fun save(i:ItemEntity) {
        generalRepository.itemRepository.updateitemEntity(i)
    }
}

