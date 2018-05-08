package com.rudearts.recipesearcher.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.rudearts.recipesearcher.R
import com.rudearts.recipesearcher.domain.model.Recipe
import com.rudearts.recipesearcher.extentions.*
import com.rudearts.recipesearcher.model.LoadingState
import com.rudearts.recipesearcher.ui.ToolbarActivity
import com.rudearts.recipesearcher.ui.location.DetailsActivity
import javax.inject.Inject


/**
 * Yes, it is open, you can see in MainActivityTest bottom comment why
 */
open class MainActivity : ToolbarActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    internal lateinit var viewModel: MainViewModel

    @Inject
    internal lateinit var adapter: RepoItemAdapter

    internal val refreshLayout: SwipeRefreshLayout by bind(R.id.swipe_container)
    internal val progressBar: View by bind(R.id.progress_bar)
    internal val listItems: RecyclerView by bind(R.id.items_list)
    internal val emptyView: View by bind(R.id.empty_view)

    override fun provideSubContentViewId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    internal fun setup() {
        inject()
        setupTitle()
        setupRefresh()
        setupList()
        setupViewModel()
        loadItems()
    }

    private fun loadItems() {
        viewModel.loadRecipes()
    }

    internal fun setupViewModel() {
        viewModel = getViewModel<MainViewModel>(viewModelFactory).apply {
            observe(recipes, ::updateItems)
            observe(loadingState, ::onLoadingStateChange)
            observe(errorMessage, ::onErrorMessage)
        }
    }

    private fun onErrorMessage(message: String?) {
        message?.let {
            showSnackMessage(it)
        }
    }

    internal fun onLoadingStateChange(loadingState: LoadingState?) {
        progressBar.visible = loadingState == LoadingState.LOADING
        emptyView.visible = loadingState == LoadingState.NO_RESULTS
        listItems.visible = loadingState == LoadingState.SHOW_RESULTS
    }

    internal fun updateItems(items: List<Recipe>?) {
        when (items) {
            null -> adapter.updateItems(emptyList())
            else -> adapter.updateItems(items)
        }
    }

    internal fun setupList() {
        adapter.listener = { item -> onRepoItemClick(item) }
        listItems.adapter = adapter
        listItems.layoutManager = LinearLayoutManager(this)
    }

    private fun onRepoItemClick(item: Recipe?) {
        item?.let { repoItem ->
            Intent(this@MainActivity, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.CONTENT_URL, repoItem.contentUrl)
                startActivity(this@apply)
            }
        }

    }

    internal fun setupRefresh() {
        refreshLayout.setOnRefreshListener { onRefresh() }
    }

    internal fun onRefresh() {
        refreshLayout.isRefreshing = false
        loadItems()
    }

    internal fun inject() {
        getAppInjector().inject(this)
    }

    internal fun setupTitle() {
        title = getString(R.string.app_name)
    }
}
