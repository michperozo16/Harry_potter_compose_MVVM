package com.mich.simpleres.di


import android.content.Context
import androidx.room.Room
import com.mich.simpleres.datasource.DbDataSource
import com.mich.simpleres.datasource.RestDataSource
import com.mich.simpleres.model.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

@Singleton
@Provides
@Named("BaseUrl")
    fun provideBaseUrl() = "https://randomuser.me/api/"

@Singleton
@Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit {
     return Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(baseUrl)
         .build()
    }

    fun restDataSource(retrofit: Retrofit): RestDataSource =
        retrofit.create(RestDataSource::class.java)

    @Singleton
    @Provides
    fun dbDataSource(@ApplicationContext context: Context): DbDataSource{
        return Room.databaseBuilder(context, DbDataSource::class.java, "user_database")
            .fallbackToDestructiveMigration()
            .build()

    }

    @Singleton
    @Provides
    fun userDao(db: DbDataSource): UserDao = db.userDao()

}