package com.github.syedahmedjamil.pushernotif

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.github.syedahmedjamil.pushernotif.domain.InterestDataSource
import com.github.syedahmedjamil.pushernotif.framework.InterestLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

private const val TEST_DATASTORE_NAME: String = "test"

@MediumTest
class InterestLocalDataSourceTest {
    private lateinit var interestLocalDataSource: InterestDataSource
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())
    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(scope = testScope,
            produceFile = {
                testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME)
            }
        )

    private fun createTestDataStoreFile(fileName: String): File {
        val context = InstrumentationRegistry.getInstrumentation().context
        val testDataStoreFile = context.assets.open(fileName)
        val file = File(testContext.filesDir, "datastore/test.preferences_pb")
        file.parentFile?.mkdir()
        file.createNewFile()
        file.outputStream().use {
            testDataStoreFile.copyTo(it)
        }
        return file
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        interestLocalDataSource = InterestLocalDataSource(testDataStore)
    }

    @After
    fun tearDown() {
        File(testContext.filesDir, "datastore").deleteRecursively()
    }

//    private fun runTestAndCleanup(block: suspend () -> Unit) = testScope.runTest {
//        block()
//        testDataStore.edit {
//            it.clear()
//        }
//    }

    @Test
    fun should_get_empty_list_when_interests_not_exists() = testScope.runTest {
        // given
        val expected = emptyList<String>()
        // when
        val actual = interestLocalDataSource.getInterests().first()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun should_get_interests_when_interests_exists() = testScope.runTest {
        // given
        createTestDataStoreFile("test.preferences_pb")
        val expected = listOf("interest1", "interest2", "interest3")
        // when
        val actual = interestLocalDataSource.getInterests().first()
        // then
        assertEquals(expected, actual)
    }


    @Test
    fun should_add_interest_when_interest_not_exists() = testScope.runTest {
        // given
        val interest = "interest1"
        val expected = listOf("interest1")
        // when
        interestLocalDataSource.addInterest(interest)
        val actual = interestLocalDataSource.getInterests().first()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun should_add_interest_when_interest_exists() = testScope.runTest {
        // given
        createTestDataStoreFile("test.preferences_pb")
        val interest = "interest4"
        val expected = listOf("interest1", "interest2", "interest3", "interest4")
        // when
        interestLocalDataSource.addInterest(interest)
        val actual = interestLocalDataSource.getInterests().first()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun should_remove_first_interest_when_interest_exists() = testScope.runTest {
        // given
        createTestDataStoreFile("test.preferences_pb")
        val interest = "interest1"
        val expected = listOf("interest2", "interest3")
        // when
        interestLocalDataSource.removeInterest(interest)
        val actual = interestLocalDataSource.getInterests().first()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun should_remove_middle_interest_when_interest_exists() = testScope.runTest {
        // given
        createTestDataStoreFile("test.preferences_pb")
        val interest = "interest2"
        val expected = listOf("interest1", "interest3")
        // when
        interestLocalDataSource.removeInterest(interest)
        val actual = interestLocalDataSource.getInterests().first()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun should_remove_last_interest_when_interest_exists() = testScope.runTest {
        // given
        createTestDataStoreFile("test.preferences_pb")
        val interest = "interest3"
        val expected = listOf("interest1", "interest2")
        // when
        interestLocalDataSource.removeInterest(interest)
        val actual = interestLocalDataSource.getInterests().first()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun should_remove_all_interest_when_interest_exists() = testScope.runTest {
        // given
        createTestDataStoreFile("test.preferences_pb")
        val interest1 = "interest1"
        val interest2 = "interest2"
        val interest3 = "interest3"
        val expected = listOf<String>()
        // when
        interestLocalDataSource.removeInterest(interest1)
        interestLocalDataSource.removeInterest(interest2)
        interestLocalDataSource.removeInterest(interest3)
        val actual = interestLocalDataSource.getInterests().first()
        // then
        assertEquals(expected, actual)
    }


}