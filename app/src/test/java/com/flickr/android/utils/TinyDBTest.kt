package com.flickr.android.utils

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TinyDBTest {
    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.flickr.android", appContext.packageName)
    }
    @get:Test
    val string: Unit
        get() {}

    @get:Test
    val listString: Unit
        get() {}

    @Test
    fun putListString() {
    }

    @Test
    fun putString() {
    }
}