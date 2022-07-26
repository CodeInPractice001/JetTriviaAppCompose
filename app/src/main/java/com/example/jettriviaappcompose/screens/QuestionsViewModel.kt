package com.example.jettriviaappcompose.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettriviaappcompose.data.DataOrException
import com.example.jettriviaappcompose.model.QuestionItem
import com.example.jettriviaappcompose.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository):ViewModel() {

    val data : MutableState<DataOrException<ArrayList<QuestionItem>, Boolean , Exception>> =
        mutableStateOf(DataOrException(null,true,Exception("")))

    init {
        getAllQuestions()
    }

    private fun getAllQuestions(){
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestion()
            if(data.value.data.toString().isNotEmpty()){
                data.value.loading =false
            }
        }
    }

}