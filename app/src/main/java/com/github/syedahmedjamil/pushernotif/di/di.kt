package com.github.syedahmedjamil.pushernotif.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.github.syedahmedjamil.pushernotif.data.InterestRepositoryImpl
import com.github.syedahmedjamil.pushernotif.domain.InterestDataSource
import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import com.github.syedahmedjamil.pushernotif.domain.SubscribeService
import com.github.syedahmedjamil.pushernotif.framework.InterestLocalDataSource
import com.github.syedahmedjamil.pushernotif.framework.SubscribeServiceImpl
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCaseImpl
import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCaseImpl
import com.github.syedahmedjamil.pushernotif.usecases.RemoveInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.RemoveInterestUseCaseImpl
import com.github.syedahmedjamil.pushernotif.usecases.SubscribeUseCase
import com.github.syedahmedjamil.pushernotif.usecases.SubscribeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


private const val INTEREST_DATASTORE_NAME = "interest"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = INTEREST_DATASTORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
abstract class AddInterestUseCaseModule {

    @Binds
    abstract fun bind(
        usecase: AddInterestUseCaseImpl
    ): AddInterestUseCase
}

@Module
@InstallIn(SingletonComponent::class)
abstract class GetInterestsUseCaseModule {

    @Binds
    abstract fun bind(
        usecase: GetInterestsUseCaseImpl
    ): GetInterestsUseCase
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoveInterestUseCaseModule {

    @Binds
    abstract fun bind(
        usecase: RemoveInterestUseCaseImpl
    ): RemoveInterestUseCase
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SubscribeUseCaseModule {

    @Binds
    abstract fun bind(
        usecase: SubscribeUseCaseImpl
    ): SubscribeUseCase
}

@Module
@InstallIn(SingletonComponent::class)
abstract class InterestRepositoryModule {

    @Binds
    abstract fun bind(
        repository: InterestRepositoryImpl
    ): InterestRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class InterestDataSourceModule {

    @Singleton
    @Binds
    abstract fun bind(
        dataSource: InterestLocalDataSource
    ): InterestDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SubscribeServiceModule {

    @Binds
    abstract fun bind(
        service: SubscribeServiceImpl
    ): SubscribeService
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideInterestDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore
}