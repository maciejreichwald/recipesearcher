package com.rudearts.recipesearcher.ui.location

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rudearts.recipesearcher.R
import com.rudearts.recipesearcher.extentions.bind
import com.rudearts.recipesearcher.extentions.getAppInjector
import com.rudearts.recipesearcher.ui.ToolbarActivity

class DetailsActivity : ToolbarActivity() {

    companion object {
        const val CONTENT_URL = "ContentUrl"
    }

    private val progressBar: View by bind(R.id.progress_bar)
    private val webView: WebView by bind(R.id.web_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupTitle()
        setupWebView()
    }

    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
        webView.loadUrl(intent.getStringExtra(CONTENT_URL))
    }

    override fun provideSubContentViewId() = R.layout.activity_details

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }

        super.onBackPressed()
    }

    internal fun setupTitle() {
        title = getString(R.string.details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    internal fun inject() {
        getAppInjector().inject(this@DetailsActivity)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }
}
