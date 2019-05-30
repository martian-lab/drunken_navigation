package com.martianlab.drunkennavigation.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.martianlab.drunkennavigation.data.db.entities.Point
import com.martianlab.drunkennavigation.data.db.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Int): LiveData<User>

    @Query("SELECT * FROM user WHERE pin = :pin")
    fun getByPin(pin: Int): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun updateUser( user:User )
}