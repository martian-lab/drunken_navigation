package com.martianlab.drunkennavigation.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.martianlab.drunkennavigation.domain.DrunkRepository
import java.util.*


class QRscanViewModel(private val repository: DrunkRepository ) : ViewModel() {


    var state = AppState.WAIT

    private val _param = MutableLiveData<String>()

    private val list : MutableSet<QRItem> = mutableSetOf()

    val items : LiveData<List<QRItem>> = Transformations.switchMap(_param ){
        Transformations.map( repository.getPoints(), { it.map { p->QRItem(p.time, p.text, p.type )} } )
    }

    fun setScannedText(text:String){
        val item =
            QRItem(Calendar.getInstance().getTime().toString(), text, 0)
        list.add(item)
        repository.addPoint(item)

        retry()
    }

    fun retry(){
        _param.value?.let { _param.value = it }
    }

}

data class QRItem(val time:String, val text: String, val type:Int){
    override fun equals(other: Any?): Boolean {
        return this.text == (other as QRItem).text
    }
}

enum class AppState{
    WAIT, START, NAVIGATION
}