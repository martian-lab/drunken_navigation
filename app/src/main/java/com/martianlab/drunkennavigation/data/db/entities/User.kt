package com.martianlab.drunkennavigation.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    indices = [Index(value=["pin"], unique = true)]
)
data class User(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
//    @ColumnInfo(name = "guid")
//    val guid : String?,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "pin")
    val pin : Int

)