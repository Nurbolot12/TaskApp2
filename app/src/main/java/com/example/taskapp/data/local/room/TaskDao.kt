package com.example.taskapp.data.local.room

import androidx.room.*
import com.example.taskapp.ui.home.TaskModel


@Dao
interface TaskDao {

    @Insert
    fun insert(task: TaskModel?)

    @Query("SELECT * FROM TaskMode")
    fun getAllTask(): List<TaskModel>?

    @Query("SELECT * FROM TaskMode ORDER BY title DESC")
    fun getAllTaskByAlphabetAz(): List<TaskModel?>?

    @Query("SELECT * FROM TaskMode ORDER BY title ASC")
    fun getAllTaskByAlphabetZa(): List<TaskModel?>?

    @Query("SELECT * FROM TaskMode ORDER BY id DESC")
    fun getAllTaskByDate(): List<TaskModel?>?

    @Delete
    fun delete(task: TaskModel?)

    @Update
    fun update(task: TaskModel?)
}