package com.martianlab.drunkennavigation.domain

import androidx.lifecycle.LiveData
import com.martianlab.drunkennavigation.data.db.entities.Point
import com.martianlab.drunkennavigation.data.db.entities.User
import com.martianlab.drunkennavigation.presentation.viewmodel.QRItem
import java.lang.IllegalArgumentException

interface DrunkRepository {

    fun addPoint( text: String )

    fun getPoints() : LiveData<List<Point>>

    fun getUserByPin(pin:Int) : LiveData<User>

    fun getUser(id:Int) : LiveData<User>

    fun isTochilovs(text: String): Boolean
    fun isFinish(text: String): Boolean
    fun logout()
    fun isLogged(): Boolean
    fun getState() : LiveData<NaviState>
}

enum class Points(val num:Int, val text:String){
    START(0,"S"),
    AE(1,"AE"),
    KP(2,"KP"),
    FINISH(3,"F");

    companion object {
        fun getPointByText(text: String) = when(text){
            "S"  -> START
            "A" -> AE
            "K" -> KP
            "F"  -> FINISH
            else -> throw IllegalArgumentException("illegal point type")
        }
    }

}

enum class NaviState(val num:Int){
    WAIT(0), RUN(1)
}