package id.idn.fahru.beritaapp.ui.detailnews

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ActivityDetailBinding
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import java.util.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val DETAIL_NEWS = "DETAIL_NEWS"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailActivityViewModel: DetailActivityViewModel
    private var statusBookmark: Boolean = false
    private var data: ArticlesItem? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        data = intent.getParcelableExtra(DETAIL_NEWS)
        binding.run {
            setContentView(root)
            setSupportActionBar(toolBar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = data?.title
            wbvDetail.apply {
                webChromeClient = MyChromeClient()
                webViewClient =
                    MyWebViewClient(data?.url.toString())
                settings.apply {
                    javaScriptEnabled = true
                    userAgentString = resources.getString(R.string.user_agent)
                    loadsImagesAutomatically = true
                    javaScriptCanOpenWindowsAutomatically = false
                    isVerticalScrollBarEnabled = true
                    defaultTextEncodingName = resources.getString(R.string.web_view_encode)
                    loadWithOverviewMode = true
                }
            }.run {
                loadUrl(data?.url.toString())
            }
        }
        detailActivityViewModel = ViewModelProvider(this).get(DetailActivityViewModel::class.java)
        data?.url?.let { url ->
            detailActivityViewModel.isArticleSaved(url).observe(this, Observer {
                statusBookmark = it > 0
                invalidateOptionsMenu()
            })
        }
    }

    internal class MyChromeClient : WebChromeClient()

    internal class MyWebViewClient(urlDestination: String) : WebViewClient() {

        private var urlSlug = urlDestination.substringAfterLast("/")

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url!!.substringAfterLast("/") == urlSlug) {
                view?.loadUrl(url)
            }
            return true
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            view?.let {
                val res = it.resources
                val message = when (error?.primaryError) {
                    SslError.SSL_UNTRUSTED -> res.getString(R.string.ssl_cert_not_trusted)
                    SslError.SSL_EXPIRED -> res.getString(R.string.ssl_cert_expired)
                    SslError.SSL_IDMISMATCH -> res.getString(R.string.ssl_cert_hostname_miss_match)
                    SslError.SSL_NOTYETVALID -> res.getString(R.string.ssl_cert_not_yet_valid)
                    SslError.SSL_INVALID -> res.getString(R.string.ssl_cert_not_valid)
                    SslError.SSL_DATE_INVALID -> res.getString(R.string.ssl_cert_date_invalid)
                    else -> res.getString(R.string.ssl_cert_error)
                }
                message.plus(res.getString(R.string.ssl_cert_error))
                val dialogBuilder = AlertDialog.Builder(it.context)
                dialogBuilder.apply {
                    setTitle(
                        res.getString(R.string.ssl_cert_error).toUpperCase(Locale.getDefault())
                    )
                    setMessage(message)
                    setPositiveButton(
                        res.getString(R.string.txt_continue).toUpperCase(Locale.getDefault())
                    ) { _, _ ->
                        handler?.proceed()
                    }
                    setNegativeButton(
                        res.getString(R.string.txt_cancel).toUpperCase(Locale.getDefault())
                    ) { _, _ ->
                        handler?.cancel()
                    }
                }.show()
            }
            super.onReceivedSslError(view, handler, error)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val bookmarkButton: MenuItem? = menu?.findItem(R.id.action_bookmark)
        var tintColor = Color.DKGRAY
        if (statusBookmark) tintColor = Color.GREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bookmarkButton?.icon?.setTint(tintColor)
        } else {
            @Suppress("DEPRECATION")
            bookmarkButton?.icon?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            data?.let {
                when (statusBookmark) {
                    true -> {
                        detailActivityViewModel.deleteArticle(it)
                    }
                    else -> {
                        detailActivityViewModel.insertArticle(it)
                    }
                }
            }
        }
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return true
    }
}