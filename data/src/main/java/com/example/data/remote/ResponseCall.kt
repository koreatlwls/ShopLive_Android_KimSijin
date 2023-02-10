package com.example.data.remote

import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ResponseCall<T> constructor(
    private val callDelegate: Call<T>
) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) = callDelegate.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let {
                if (response.isSuccessful) {
                    callback.onResponse(
                        this@ResponseCall,
                        Response.success(Result.success(it))
                    )
                } else {
                    callback.onResponse(
                        this@ResponseCall,
                        Response.success(Result.failure(Exception("something went wrong")))
                    )
                }
            }

            response.errorBody()?.let {
                callback.onResponse(
                    this@ResponseCall,
                    Response.success(Result.failure(Exception("something went wrong")))
                )
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val errorMessage = when (t) {
                is IOException -> "No Internet Connection"
                is HttpException -> "Something went wrong!"
                else -> t.localizedMessage
            }

            callback.onResponse(
                this@ResponseCall,
                Response.success(Result.failure(RuntimeException(errorMessage, t)))
            )
        }

    })

    override fun clone(): Call<Result<T>> = ResponseCall(callDelegate.clone())

    override fun execute(): Response<Result<T>> = throw UnsupportedOperationException("ResponseCall does not support execute.")

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()

}