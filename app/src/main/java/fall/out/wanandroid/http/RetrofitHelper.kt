package fall.out.wanandroid.http

import fall.out.wanandroid.BuildConfig
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.constant.HttpConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Owen on 2019/10/7
 */
object RetrofitHelper {
    private var retrofit:Retrofit?=null
    val apiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }
     private fun getRetrofit():Retrofit{
        if(retrofit ==null){
            synchronized(RetrofitHelper::class.java){
                if(retrofit ==null){
                    retrofit =Retrofit.Builder().baseUrl(Constant.BASE_URL).client(getOKHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
        }
         return  retrofit!!
    }
   // /data/user/0/com.cxz.wanandroid/cache/cache
    fun getOKHttpClient():OkHttpClient?{
        var builder=OkHttpClient().newBuilder()
        var httpLoggingInterceptor=HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        }else{
            httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.NONE
        }
        builder.run {
           this.addInterceptor(httpLoggingInterceptor)
            this.addInterceptor(HeaderInterceptor())
            this.addInterceptor(SaveCookieInterceptor())
            this.connectTimeout(HttpConstant.DEFAULT_TIMEOUT,TimeUnit.SECONDS)
            this.readTimeout(HttpConstant.DEFAULT_TIMEOUT,TimeUnit.SECONDS)
            this.writeTimeout(HttpConstant.DEFAULT_TIMEOUT,TimeUnit.SECONDS)
            this.retryOnConnectionFailure(true)//错误重连
        }
        return  builder.build()
    }
}