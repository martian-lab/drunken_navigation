package com.martianlab.drunkennavigation.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.martianlab.drunkennavigation.domain.DrunkRepository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val START = 0
const val KP = 1
const val FINISH = 2

class QRscanViewModel(private val repository: DrunkRepository ) : ViewModel() {


    var state = AppState.WAIT

    val _param = MutableLiveData<String>()


    val items : LiveData<List<QRItem>> = Transformations.switchMap(_param ){
        Transformations.map( repository.getPoints(), { it.map { p->QRItem( SimpleDateFormat("hh:m:ss").format(Date(p.time)), p.text, p.type )} } )
    }

    fun setScannedText(text:String){

        repository.addPoint( QRItem(Calendar.getInstance().getTime().toString(), text, 0) )

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