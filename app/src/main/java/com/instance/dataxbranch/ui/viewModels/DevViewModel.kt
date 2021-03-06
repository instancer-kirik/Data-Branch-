package com.instance.dataxbranch.ui.viewModels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.firestore.FirestoreResponse
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.domain.use_case.UseCases

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevViewModel @Inject constructor(
    val useCases: UseCases
): ViewModel()  {

    private val _rState = mutableStateOf<Response<List<FirestoreResponse>>>(Response.Loading)
    val responseState: State<Response<List<FirestoreResponse>>> = _rState

    private val _isResponseAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isResponseAddedState: State<Response<Void?>> = _isResponseAddedState

    private val _isResponseDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isResponseDeletedState: State<Response<Void?>> = _isResponseDeletedState
    lateinit var oldContext: Context
    var openDialogState = mutableStateOf(false)

    init {
        getResponses()
    }



    private fun getResponses() {
        viewModelScope.launch {
            useCases.getResponses().collect { response ->
                _rState.value = response
            }
        }
    }

    fun addResponse(subject: String, description: String, author: String,authorid: String) {
        viewModelScope.launch {
            useCases.addResponse.invoke(subject, description, author,authorid).collect { response ->
                _isResponseAddedState.value = response
            }
        }
    }
}