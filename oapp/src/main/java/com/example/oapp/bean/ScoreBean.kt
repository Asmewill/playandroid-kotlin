package com.example.oapp.bean

/**
 * Created by Owen on 2019/11/5
 */
class ScoreBean {

    /**
     * curPage : 1
     * datas : [{"coinCount":27,"date":1572918116000,"desc":"2019-11-05 09:41:56 签到 , 积分：10 + 17","id":84028,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":26,"date":1572829724000,"desc":"2019-11-04 09:08:44 签到 , 积分：10 + 16","id":82853,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":25,"date":1572677679000,"desc":"2019-11-02 14:54:39 签到 , 积分：10 + 15","id":81877,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":24,"date":1572590700000,"desc":"2019-11-01 14:45:00 签到 , 积分：10 + 14","id":81277,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":23,"date":1572503284000,"desc":"2019-10-31 14:28:04 签到 , 积分：10 + 13","id":80263,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":22,"date":1572404990000,"desc":"2019-10-30 11:09:50 签到 , 积分：10 + 12","id":79033,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":21,"date":1572328217000,"desc":"2019-10-29 13:50:17 签到 , 积分：10 + 11","id":78081,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":20,"date":1572225260000,"desc":"2019-10-28 09:14:20 签到 , 积分：10 + 10","id":76607,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":19,"date":1572060974000,"desc":"2019-10-26 11:36:14 签到 , 积分：10 + 9","id":75607,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":18,"date":1570676111000,"desc":"2019-10-10 10:55:11 签到 , 积分：10 + 8","id":61786,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":17,"date":1570592034000,"desc":"2019-10-09 11:33:54 签到 , 积分：10 + 7","id":60914,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":16,"date":1570499813000,"desc":"2019-10-08 09:56:53 签到 , 积分：10 + 6","id":59927,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":15,"date":1570230560000,"desc":"2019-10-05 07:09:20 签到 , 积分：10 + 5","id":58533,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":14,"date":1570158408000,"desc":"2019-10-04 11:06:48 签到 , 积分：10 + 4","id":58314,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":13,"date":1570059441000,"desc":"2019-10-03 07:37:21 签到 , 积分：10 + 3","id":57904,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":12,"date":1570001144000,"desc":"2019-10-02 15:25:44 签到 , 积分：10 + 2","id":57732,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":11,"date":1569892910000,"desc":"2019-10-01 09:21:50 签到 , 积分：10 + 1","id":57266,"reason":"签到","type":1,"userId":31271,"userName":"Owen"},{"coinCount":10,"date":1569806787000,"desc":"2019-09-30 09:26:27 签到 , 积分：10 + 0","id":56709,"reason":"签到","type":1,"userId":31271,"userName":"Owen"}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 18
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
         * coinCount : 27
         * date : 1572918116000
         * desc : 2019-11-05 09:41:56 签到 , 积分：10 + 17
         * id : 84028
         * reason : 签到
         * type : 1
         * userId : 31271
         * userName : Owen
         */

        var coinCount: Int = 0
        var date: Long = 0
        var desc: String? = null
        var id: Int = 0
        var reason: String? = null
        var type: Int = 0
        var userId: Int = 0
        var userName: String? = null
    }
}
