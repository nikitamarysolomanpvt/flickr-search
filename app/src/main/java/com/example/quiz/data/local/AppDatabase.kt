package com.example.quiz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quiz.data.entities.QuestionAnswere
import com.example.quiz.data.entities.SearchItem

@Database(entities = [SearchItem::class,QuestionAnswere::class], version = 7, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): SearchItemsDao
    abstract fun questionAnswerDao(): ItemDetailsDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "searchitem")
                .fallbackToDestructiveMigration()
                .build()
    }

}