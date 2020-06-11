package id.idn.fahru.newsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.idn.fahru.newsapp.databinding.ActivityMainBinding
import id.idn.fahru.newsapp.model.ResponseNews
import id.idn.fahru.newsapp.service.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterRv: CdvNewsHeadlineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        adapterRv = CdvNewsHeadlineAdapter()
        binding.run {
            setContentView(root)
            setSupportActionBar(toolBar)
            mainRv.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = adapterRv
            }
            val call = RetrofitBuilder.getService().fetchHeadlines()
            call.enqueue(object : Callback<ResponseNews> {
                override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                    Timber.e(t)
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<ResponseNews>,
                    response: Response<ResponseNews>
                ) {
                    response.body()?.articles?.let {
                        adapterRv.addData(it)
                    }
                }
            })
        }
    }
}