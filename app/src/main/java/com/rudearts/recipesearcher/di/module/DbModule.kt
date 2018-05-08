package com.rudearts.recipesearcher.di.module

import android.content.Context
import com.rudearts.recipesearcher.data.database.DatabaseStorage
import com.rudearts.recipesearcher.data.model.greendao.DaoMaster
import com.rudearts.recipesearcher.data.model.greendao.DaoSession
import com.rudearts.recipesearcher.data.util.RecipeMapper
import com.rudearts.recipesearcher.domain.remote.DatabaseCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    companion object {
        private val DATABASE_NAME = "recipesearcher.db"
    }

    @Provides
    @Singleton
    fun providesHelper(context:Context) = DaoMaster.DevOpenHelper(context, DATABASE_NAME)

    @Provides
    @Singleton
    fun providesDaoSession(helper:DaoMaster.DevOpenHelper) = DaoMaster(helper.writableDatabase).newSession()

    @Provides
    fun providesDatabaseCache(
            session:DaoSession,
            mapper: RecipeMapper) = DatabaseStorage(session, mapper) as DatabaseCache

}