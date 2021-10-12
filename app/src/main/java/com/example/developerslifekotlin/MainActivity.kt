package com.example.developerslifekotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import com.example.developerslifekotlin.databinding.ActivityMainBinding

var hash: ArrayList<Result?> = ArrayList<Result?>()
var hashLevel = -1
var numCategory = 0

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha)
        binding.progressBar.visibility = View.GONE
        val tab = binding.tabLayout.getTabAt(numCategory)
        tab!!.select()
        binding.back.isEnabled = hashLevel > 0

        if (hashLevel == -1)
            extract()
        else
            setTextAndGif(hash[hashLevel]!!)

        binding.back.setOnClickListener {
            setTextAndGif(hash[--hashLevel]!!)
            binding.back.startAnimation(animAlpha)
        }

        binding.next.setOnClickListener {
            binding.next.isEnabled = false
            if (hash.size - 1 > hashLevel) {
                setTextAndGif(hash[++hashLevel]!!)
            } else extract()
            binding.next.startAnimation(animAlpha)
        }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                hashLevel = -1
                hash.clear()
                binding.back.isEnabled = false
                extract()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        numCategory = binding.tabLayout.selectedTabPosition
        outState.putInt("numCategory", numCategory)
        outState.putInt("hashLevel", hashLevel)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        numCategory = savedInstanceState.getInt("numCategory")
        hashLevel = savedInstanceState.getInt("hashLevel")
    }

    private fun extract() = if (isOnline(this)) {
        val retrofit = Retrofit.Builder().baseUrl("https://developerslife.ru")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val call = retrofitAPI.getCourse(
            chooseCategory(binding.tabLayout.selectedTabPosition),
            randomNumber(2000)
        )
        call.enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {
                if (response.isSuccessful) {
                    if (response.body()!!.result!!.isNotEmpty()) {
                        binding.progressBar.visibility = View.VISIBLE
                        val modal = response.body()
                        val rsl = modal!!.result!![randomNumber(5)]
                        hashLevel++
                        hash.add(rsl)
                        setTextAndGif(rsl)
                    } else {
                        hashLevel = -1
                        binding.description.text = "Запрос недоступен"
                        binding.gif.setImageDrawable(null)
                        binding.next.isEnabled = false
                    }
                }
            }

            override fun onFailure(call: Call<Root>, t: Throwable) {}
        })
    } else {
        hashLevel = hash.size - 1
        binding.description.text = "Отсутствует подключение"
        binding.gif.setImageDrawable(null)
        binding.next.isEnabled = true
    }

    private fun setTextAndGif(result: Result) {
        binding.back.isEnabled = hashLevel != 0

        binding.description.text = result.description // выводим текст
        Glide.with(this).load(result.getGifURL())
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    binding.next.isEnabled = true
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    binding.next.isEnabled = true
                    return false
                }
            })
            .thumbnail(Glide.with(this).load(result.getPreviewURL()).centerCrop()).centerCrop()
            .into(binding.gif)
    }

    private fun randomNumber(range: Int): Int {
        val random = Random()
        return random.nextInt(range)
    }

    private fun chooseCategory(numCategory: Int): String {
        var category = ""
        when (numCategory) {
            0 -> category = "latest"
            1 -> category = "top"
            2 -> category = "hot"
            else -> {
            }
        }
        return category
    }

    @SuppressLint("NewApi")
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}


