package com.example.oapp.bean

/**
 * Created by Owen on 2019/11/15
 */
class PointBean {

    /**
     * curPage : 1
     * datas : [{"coinCount":3484,"level":35,"rank":1,"userId":20382,"username":"g**eii"},{"coinCount":3441,"level":35,"rank":2,"userId":27535,"username":"1**08491840"},{"coinCount":3178,"level":32,"rank":3,"userId":3559,"username":"A**ilEyon"},{"coinCount":2656,"level":27,"rank":4,"userId":1260,"username":"于**家的吴蜀黍"},{"coinCount":2494,"level":25,"rank":5,"userId":833,"username":"w**lwaywang6"},{"coinCount":2467,"level":25,"rank":6,"userId":9621,"username":"S**24n"},{"coinCount":2463,"level":25,"rank":7,"userId":1534,"username":"j**gbin"},{"coinCount":2453,"level":25,"rank":8,"userId":863,"username":"m**qitian"},{"coinCount":2403,"level":25,"rank":9,"userId":15603,"username":"r**eryxx"},{"coinCount":2353,"level":24,"rank":10,"userId":2068,"username":"i**Cola"},{"coinCount":2347,"level":24,"rank":11,"userId":23244,"username":"a**ian"},{"coinCount":2343,"level":24,"rank":12,"userId":1871,"username":"l**shifu"},{"coinCount":2337,"level":24,"rank":13,"userId":1440,"username":"w**.wanandroid.com"},{"coinCount":2336,"level":24,"rank":14,"userId":7541,"username":"l**64301766"},{"coinCount":2332,"level":24,"rank":15,"userId":3753,"username":"S**phenCurry"},{"coinCount":2332,"level":24,"rank":16,"userId":2,"username":"x**oyang"},{"coinCount":2329,"level":24,"rank":17,"userId":7809,"username":"1**23822235"},{"coinCount":2322,"level":24,"rank":18,"userId":27596,"username":"1**5915093@qq.com"},{"coinCount":2318,"level":24,"rank":19,"userId":7590,"username":"陈**啦啦啦"},{"coinCount":2295,"level":23,"rank":20,"userId":6142,"username":"c**huah"},{"coinCount":2295,"level":23,"rank":21,"userId":10010,"username":"c**01220122"},{"coinCount":2254,"level":23,"rank":22,"userId":27602,"username":"l**hehan"},{"coinCount":2254,"level":23,"rank":23,"userId":7710,"username":"i**Cola7"},{"coinCount":2235,"level":23,"rank":24,"userId":22832,"username":"7**502274@qq.com"},{"coinCount":2214,"level":23,"rank":25,"userId":14829,"username":"l**changwen"},{"coinCount":2211,"level":23,"rank":26,"userId":2199,"username":"M**459"},{"coinCount":2196,"level":22,"rank":27,"userId":28454,"username":"c**xzxzc"},{"coinCount":2175,"level":22,"rank":28,"userId":14032,"username":"M**eor"},{"coinCount":2175,"level":22,"rank":29,"userId":8152,"username":"t**g111"},{"coinCount":2160,"level":22,"rank":30,"userId":28694,"username":"c**ng0218"}]
     * offset : 0
     * over : false
     * pageCount : 288
     * size : 30
     * total : 8624
     */

    var curPage: Int = 0
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var datas: List<DatasBean>? = null

    class DatasBean {
        /**
         * coinCount : 3484
         * level : 35
         * rank : 1
         * userId : 20382
         * username : g**eii
         */

        var coinCount: Int = 0
        var level: Int = 0
        var rank: Int = 0
        var userId: Int = 0
        var username: String? = null
    }
}
