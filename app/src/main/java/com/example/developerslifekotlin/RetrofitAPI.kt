package com.example.developerslifekotlin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitAPI {
    // as we are making get request
    // so we are displaying GET as annotation.
    // and inside we are passing
    // last parameter for our url.
    @GET("/{category}/{number}?json=true")
    fun  // as we are calling data from array
    // so we are calling it with json object
    // and naming that method as getCourse();
            getCourse(
        @Path("category") category: String?,
        @Path("number") number: Int
    ): Call<Root>
}