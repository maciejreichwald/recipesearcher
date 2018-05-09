package com.rudearts.recipesearcher.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rudearts.recipesearcher.domain.model.Recipe
import com.rudearts.recipesearcher.extentions.visible
import com.rudearts.recipesearcher.model.LoadingState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainActivityTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var vieModelFactory:ViewModelProvider.Factory

    @Mock lateinit var adapter: RepoItemAdapter

    @InjectMocks @Spy lateinit var activity:MainActivityMock

    private val emptyView = mock<View> {}
    private val listItems = mock<RecyclerView> {}
    private val progress = mock<ProgressBar> {}

    @Before
    fun setup() {
        whenever(activity.progressBar).thenReturn(progress)
        whenever(activity.emptyView).thenReturn(emptyView)
        whenever(activity.listItems).thenReturn(listItems)
    }


    @Test
    fun updateItems() {
        val items:List<Recipe> = mock {}
        activity.updateItems(items)

        verify(adapter, times(1)).updateItems(items)
    }

    @Test
    fun onLoadingStateChange_WhenLoading() {
        activity.onLoadingStateChange(LoadingState.LOADING)

        verify(progress, times(1)).visible = true
        verify(emptyView, times(1)).visible = false
        verify(listItems, times(1)).visible = false
    }

    @Test
    fun onLoadingStateChange_WhenNoResults() {
        activity.onLoadingStateChange(LoadingState.NO_RESULTS)

        verify(progress, times(1)).visible = false
        verify(emptyView, times(1)).visible = true
        verify(listItems, times(1)).visible = false
    }

    @Test
    fun onLoadingStateChange_WhenShowResults() {
        activity.onLoadingStateChange(LoadingState.SHOW_RESULTS)

        verify(progress, times(1)).visible = false
        verify(emptyView, times(1)).visible = false
        verify(listItems, times(1)).visible = true
    }

    @Test
    fun onLoadingStateChange_WhenError() {
        activity.onLoadingStateChange(LoadingState.ERROR)

        verify(progress, times(1)).visible = false
        verify(emptyView, times(1)).visible = false
        verify(listItems, times(1)).visible = false
    }

    /**
     * Ok, I gave up here - I had some problems with mocking activity
     * it was holiday, it was late and this "temporary" fix worked like a charm...
     */
    class MainActivityMock : MainActivity() {
        override fun <T : View?> findViewById(id: Int): T {
            return null as T
        }
    }
}