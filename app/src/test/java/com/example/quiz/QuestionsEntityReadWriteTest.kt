package com.example.quiz

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quiz.data.local.AppDatabase
import com.example.quiz.data.local.SearchItemDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */
@RunWith(AndroidJUnit4::class)
class WordDaoTest {
    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private var mWordDao: SearchItemDao? = null
    private var mDb: AppDatabase? = null
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDb = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ) // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        mWordDao = mDb?.questionDao()
    }

    @After
    fun closeDb() {
        mDb?.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetWord() {
        val question = Question("xxx",0,"test",arrayListOf(),"SA",10)
        mWordDao?.insert(question)
        val questionObject:Question= mWordDao!!.getQuestion(0)
        assertEquals(questionObject.id, "xxx")
    }


}
