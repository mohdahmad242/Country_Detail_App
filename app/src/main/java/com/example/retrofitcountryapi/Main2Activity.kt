package com.example.retrofitcountryapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.retrofitcountryapi.adaptor.DetailViewAdaptor
import com.example.retrofitcountryapi.adaptor.CountryNameAdapter
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Main2Activity : AppCompatActivity() {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var fullname =  intent.getStringExtra(CountryNameAdapter.CustomViewHolder.country_name)

        supportActionBar?.title = "Detail"

//        Toast.makeText(this, fullname, Toast.LENGTH_SHORT).show()
        var retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://restcountries.eu/rest/v2/name/").build()

        var postsApi = retrofit.create(INetworkAPI_detail::class.java)

        var response: Observable<List<Country>> = postsApi.getAllCountryByName(fullname,"true")


        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            layoutManager = LinearLayoutManager(this)
            detail_view.layoutManager = layoutManager
            detail_view.adapter = DetailViewAdaptor(this,it)

        }


    }




}
