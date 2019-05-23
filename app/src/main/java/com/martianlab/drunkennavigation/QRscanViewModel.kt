package com.martianlab.drunkennavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.time.format.DateTimeFormatter
import java.util.*


class QRscanViewModel : ViewModel() {


    private val list : MutableSet<QRItem> = mutableSetOf()

    val items = MutableLiveData<List<QRItem>>()


    fun setScannedText(text:String){
        val item = QRItem(Calendar.getInstance().getTime().toString(), text, 0)
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