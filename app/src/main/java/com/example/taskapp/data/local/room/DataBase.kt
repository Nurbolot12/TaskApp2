package com.example.taskapp.data.local.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskapp.ui.home.TaskModel

@Database(entities = [TaskModel::class], version = 1)
abstract class  DataBase : RoomDatabase() {
    abstract fun TaskDao(): TaskDao?
}