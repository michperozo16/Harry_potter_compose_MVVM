package com.n1rocket.harrypotterapp.di

import com.n1rocket.harrypotterapp.datasource.RestDataSource
import com.n1rocket.harrypotterapp.model.CharacterDao
import com.n1rocket.harrypotterapp.repository.HarryPotterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun harryPotterRepository(dataSource: RestDataSource, characterDao: CharacterDao): HarryPotterRepository{
        return HarryPotterRepository(dataSource, characterDao)
    }
}








