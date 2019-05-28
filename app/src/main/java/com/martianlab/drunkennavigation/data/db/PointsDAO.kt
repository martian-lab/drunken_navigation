package com.martianlab.drunkennavigation.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.martianlab.drunkennavigation.data.db.entities.Point

@Dao
interface PointsDao {

    @Query("SELECT * FROM point")
    fun getAll(): LiveData<List<Point>>

    @Query("SELECT * FROM point WHERE run_guid = :id")
    fun getAllBySessId(id: String): LiveData<List<Point>>

    @Query("SELECT * FROM point WHERE run_guid = :id and sent = 0")
    fun getAllUnsentBySessId(id: String): List<Point>

    @Query("SELECT * FROM point WHERE sent = 0")
    fun getAllUnsent(): List<Point>

    @Query("SELECT * FROM point WHERE id = :id")
    fun getById(id: Long): LiveData<Point>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg points: Point)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(points: Point)

    @Query("UPDATE point SET sent = 1 WHERE id = :id" )
    fun setSent(id : Long )
}