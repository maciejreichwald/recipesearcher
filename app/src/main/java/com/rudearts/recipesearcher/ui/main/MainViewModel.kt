package com.rudearts.recipesearcher.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rudearts.recipesearcher.domain.model.Recipe
import com.rudearts.recipesearcher.domain.usecase.LoadRecipesUseCase
import com.rudearts.recipesearcher.extentions.threadToAndroid
import com.rudearts.recipesearcher.model.LoadingState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val loadRecipesUseCase: LoadRecipesUseCase) : ViewModel() {

    internal val loadingState = MutableLiveData<LoadingState>()
    internal val recipes = MutableLiveData<List<Recipe>>()
    internal val errorMessage = MutableLiveData<String>()

    private val compositeDisposable = CompositeDisposable()

    fun loadRecipes() {
        loadingState.postValue(LoadingState.LOADING)
        compositeDisposable.add(loadRecipesUseCase.load()
                .threadToAndroid()
                .subscribe({ items ->
                    onLoadSuccess(items)
                }, { error ->
                    onLoadError(error) }))
    }

    private fun onLoadError(error: Throwable?) {
        loadingState.postValue(LoadingState.NO_RESULTS)
        errorMessage.postValue(error?.toString())
    }

    private fun onLoadSuccess(items: List<Recipe>?) {
        recipes.postValue(items)
        updateLoadingState(items)
    }

    private fun updateLoadingState(items: List<Recipe>?) {
        when(items?.isNotEmpty()) {
            true -> loadingState.postValue(LoadingState.SHOW_RESULTS)
            else -> loadingState.postValue(LoadingState.NO_RESULTS)
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}