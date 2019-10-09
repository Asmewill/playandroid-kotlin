package fall.out.wanandroid.http

import fall.out.wanandroid.constant.HttpConstant
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Owen on 2019/10/8
 * 添加cookies
 */
class HeaderInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response ?{
        var request=chain.request()
        var builder=request.newBuilder()
        builder.addHeader("Content-type", "application/json; charset=utf-8")
        var domain=request.url().host()
        var url=request.url().toString()
        //此处暂时先不加cookie
        if(domain.isNotEmpty()&&url.contains(HttpConstant.COLLECTION_WEBSITE)){

        }
        return chain.proceed(builder.build())
    }
}