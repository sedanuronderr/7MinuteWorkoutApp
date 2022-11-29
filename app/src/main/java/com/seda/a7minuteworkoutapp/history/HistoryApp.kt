package com.seda.a7minuteworkoutapp.history

import android.app.Application
class HistoryApp:Application() {

    val db by lazy {
        HistoryDatabase.getInstance(this)
    }

}