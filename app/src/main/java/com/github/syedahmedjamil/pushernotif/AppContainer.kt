package com.github.syedahmedjamil.pushernotif

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import com.github.syedahmedjamil.pushernotif.data.InterestRepositoryImpl
import com.github.syedahmedjamil.pushernotif.framework.InterestLocalDataSource
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCaseImpl
import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCaseImpl
import com.github.syedahmedjamil.pushernotif.usecases.RemoveInterestUseCaseImpl
import kotlinx.coroutines.runBlocking

private const val INTEREST_DATASTORE_NAME = "interest"

class AppContainer(context: Context) {

    private val interestDataStore by lazy {
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(INTEREST_DATASTORE_NAME) }
        )
    }
    private val interestLocalDataSource by lazy { InterestLocalDataSource(interestDataStore) }
    private val interestRepository by lazy { InterestRepositoryImpl(interestLocalDataSource) }


    val addInterestUseCase by lazy { AddInterestUseCaseImpl(interestRepository) }
    val getInterestsUseCase by lazy { GetInterestsUseCaseImpl(interestRepository) }
    val removeInterestUseCase by lazy { RemoveInterestUseCaseImpl(interestRepository) }

    // For testing
    @VisibleForTesting
    fun reset() {
        runBlocking {
            interestDataStore.edit { it.clear() }
        }
    }
}