package fall.out.wanandroid.http

import fall.out.wanandroid.constant.HttpConstant
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Owen on 2019/10/8
 */
class SaveCookieInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response ?{
      val request=chain.request()
      val response=chain.proceed(request)
      val requestUrl=request.url().toString()
      val domain=request.url().host()
      if(requestUrl.contains(HttpConstant.SAVE_USER_LOGIN_KEY)||
          requestUrl.contains(HttpConstant.SAVE_USER_REGISTER_KEY)
          &&!response.headers(HttpConstant.SET_COOKIE_KEY).isEmpty()){
          val cookies=response.headers(HttpConstant.SET_COOKIE_KEY)
        //  val cookie=HttpConstant.encodeCookie(cookies)
          val cookie=filterListCookie(cookies)
          HttpConstant.saveCookie(requestUrl,domain,cookie)
      }
       return response
    }

    private fun  filterListCookie(list:List<String>): String {
        val hashSet=HashSet<String>()////去除重复
        list.forEach {
            it.split(";").forEach {
                hashSet.add(it)
            }
        }
        val cookieStr=StringBuffer()
        hashSet.forEach {
            if(it.contains("=")){
                cookieStr.append(it)
                cookieStr.append(";")//添加;
            }
        }
        return cookieStr.toString()
    }
}

