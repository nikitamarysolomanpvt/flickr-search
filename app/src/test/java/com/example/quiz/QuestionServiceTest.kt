package com.example.quiz

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.quiz.data.remote.QuestionService

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class QuestionServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: QuestionService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(QuestionService::class.java)
    }


    @Test
    fun `read Sample Success Json File `() {
        val reader = MockResponseFileReader("questions.json")
        assertNotNull(reader.content)
    }
    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getMoviesListTest() = runBlocking {
        enqueueResponse("questions.json")
//        val question = service.getAllQuestions().body()?.questions

//        assertThat(question, IsNull.notNullValue())
//        assertThat(question!!.size, CoreMatchers.`is`(6))
//        assertThat(question[0].text, CoreMatchers.`is`("<p>Which one of these foods is likely to contain the most bacteria?</p>"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}
