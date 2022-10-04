package com.instance.dataxbranch.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.cloud.CloudResponse

import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.domain.use_case.UseCases
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevViewModel @Inject constructor(
    val useCases: UseCases,
   // val navigator: DestinationsNavigator this requires an @Provides method
): ViewModel()  {

    private val _rState = mutableStateOf<Response<List<CloudResponse>>>(Response.Loading)
    val responseState: State<Response<List<CloudResponse>>> = _rState

    private val _isResponseAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isResponseAddedState: State<Response<Void?>> = _isResponseAddedState

    private val _isResponseDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isResponseDeletedState: State<Response<Void?>> = _isResponseDeletedState
    lateinit var navi: DestinationsNavigator
    var hasNavi: Boolean = false
    var openDialogState = mutableStateOf(false)
    var openNoteDialogState = mutableStateOf(false)
    init {
        getResponses()

    }

    private fun setNavigator(navigator: DestinationsNavigator){
        this.navi = navigator
        hasNavi=true
    }


    private fun getResponses() {
//        viewModelScope.launch {
//            useCases.getResponses().collect { response ->
//                _rState.value = response
//            }
//        }
    }

    fun addResponse(subject: String, description: String, author: String,authorid: String) {
//        viewModelScope.launch {
//            useCases.addResponse.invoke(subject, description, author,authorid).collect { response ->
//                _isResponseAddedState.value = response
//            }
//        }
    }
}