package com.seda.a7minuteworkoutapp.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seda.a7minuteworkoutapp.history.HistoryRepository

class HistroyFactory(private val repository: HistoryRepository): ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryMvvm(repository) as T
    }
}