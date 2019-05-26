package com.martianlab.drunkennavigation.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.martianlab.drunkennavigation.domain.DrunkRepository
import java.util.*


class QRscanViewModel(private val repository: DrunkRepository) : ViewModel() {


    var state = AppState.INIT

    private val list : MutableSet<QRItem> = mutableSetOf()

    val items = MutableLiveData<List<QRItem>>()


    fun setScannedText(text:String){
        val item =
            QRItem(Calendar.getInstance().getTime().toString(), text, 0)
        list.add(item)

        items.value = list.toList()
    }

    fun getItems() : LiveData<List<QRItem>>{
        return  items
    }
}

data class QRItem(val time:String, val text: String, val type:Int){
    override fun equals(other: Any?): Boolean {
        return this.text == (other as QRItem).text
    }
}

enum class AppState{
    INIT, START, NAVIGATION
}