package com.seda.a7minuteworkoutapp.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seda.a7minuteworkoutapp.history.HistoryEntity
import com.seda.a7minuteworkoutapp.history.HistoryRepository
import kotlinx.coroutines.launch

class HistoryMvvm(private val repository: HistoryRepository):ViewModel() {

    fun insertTodo(history: HistoryEntity)=viewModelScope.launch {

        repository.insertTodo(history)
    }
    val allhistory =  repository.allhistoryquery()
}