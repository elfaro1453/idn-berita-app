package id.idn.fahru.beritaapp.ui.detailnews

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ActivityDetailBinding
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_NEWS = "DETAIL_NEWS"
    }

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val data = intent.getParcelableExtra(DETAIL_NEWS) as ArticlesItem
        binding.run {
            setContentView(root)
            setSupportActionBar(toolBar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = data.title
            wbvDetail.apply {
                webViewClient =
                    MyWebViewClient(data.url.toString())
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
                loadUrl(data.url.toString())
            }
        }
    }

    internal class MyWebViewClient(urlDestination: String) : WebViewClient() {

        private var urlSlug = urlDestination.substringAfterLast("/")

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url.toString().substringAfterLast("/") == urlSlug) {
                urlSlug = url.toString()
            }
            if (url.toString() == urlSlug) view?.loadUrl(url)
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

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return true
    }
}