package com.seda.a7minuteworkoutapp.history

class HistoryRepository(private  val dao: HistoryDao) {

    suspend fun insertTodo(history: HistoryEntity)= dao.insert(history)

    fun allhistoryquery() = dao.fetchALlDates()
}