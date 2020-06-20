package id.idn.fahru.beritaapp.ui.detailnews

import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.size.Scale
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ActivityDetailBinding
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_NEWS = "DETAIL_NEWS"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myWebViewClient = MyWebViewClient()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val data = intent.getParcelableExtra(DETAIL_NEWS) as ArticlesItem
        binding.run {
            setContentView(root)
            setSupportActionBar(toolBar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = data.title
            imgToolbar.apply {
                load(data.urlToImage) {
                    scale(Scale.FILL)
                }
                contentDescription = data.description
            }
            wbvDetail.apply {
                webViewClient = myWebViewClient
                settings.apply {
                    javaScriptEnabled = true
                    userAgentString = resources.getString(R.string.user_agent)
                    loadsImagesAutomatically = true
                    javaScriptCanOpenWindowsAutomatically = false
                    isVerticalScrollBarEnabled = true
                    defaultTextEncodingName = resources.getString(R.string.web_view_encode)
                }
            }.run {
                loadUrl(data.url)
            }
        }
    }

    internal class MyWebViewClient : WebViewClient() {

        private var urlDest: String? = null

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let {
                if (urlDest.isNullOrEmpty() || it == urlDest) {
                    urlDest = it
                    view?.loadUrl(it)
                }
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
                    SslError.SSL_NOTYETVALID -> res.getString(R.string.ssl_cert_not_valid)
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
                        res.getString(R.string.txt_continue).toUpperCase(Locale.getDefault())
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