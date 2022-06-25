package com.instance.dataxbranch.domain.use_case


import com.instance.dataxbranch.data.firestore.ResponseRepository

class GetResponses (private val repository: ResponseRepository

    ){
        operator fun invoke() = repository.getResponsesFromFirestore()
    }