package com.example.oapp.bean;

import java.util.List;

/**
 * Created by Owen on 2019/11/5
 */
public class CollectArticle {

    /**
     * curPage : 1
     * datas : [{"author":"xiaoyang","chapterId":360,"chapterName":"小编发布","courseId":13,"desc":"","envelopePic":"","id":97417,"link":"https://wanandroid.com/blog/show/2701","niceDate":"28分钟前","origin":"","originId":9988,"publishTime":1572918158000,"title":"不好意思哈，很多群可能要被迫解散了，建立了一个新家园！","userId":31271,"visible":0,"zan":0},{"author":"","chapterId":502,"chapterName":"自助","courseId":13,"desc":"","envelopePic":"","id":97283,"link":"https://mp.weixin.qq.com/s/cQKKX7vtdkARasDAGzMFEw","niceDate":"16小时前","origin":"","originId":10047,"publishTime":1572860155000,"title":"Android架构师成长路线图","userId":31271,"visible":0,"zan":0},{"author":"yukilzw","chapterId":402,"chapterName":"跨平台应用","courseId":13,"desc":"包含功能： - 开播列表上拉加载、下拉刷新 - 渐进式导航头部 - 封装HTTP、IO缓存操作 - 页面路由传值 - bloc全局状态管理 - 礼物横幅动画队列 - 弹幕消息滚动 - 静态视频流 - 九宫格抽奖游戏 - 照片选择 - 全屏、窗口webView - ...（持续增加中）","envelopePic":"https://www.wanandroid.com/blogimgs/ffb41ede-7f1d-4368-beb8-e9ccc0422902.png","id":97199,"link":"http://www.wanandroid.com/blog/show/2670","niceDate":"22小时前","origin":"","originId":9101,"publishTime":1572838930000,"title":"使用flutter重构斗鱼APP - dy_flutter","userId":31271,"visible":0,"zan":0},{"author":"kukyxs","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"该项目基于「玩 Android 接口」接口，整体采用 MVVM, Android Jectpack, Retrofit, Kotlin 协程进行编写。 是由 kukyxs 和 Taonce 一起编写完成，目前已完成所有的开发功能，细节有待调整。","envelopePic":"https://wanandroid.com/blogimgs/60462c4c-0d82-41aa-b76d-0406c80fce31.png","id":97194,"link":"http://www.wanandroid.com/blog/show/2641","niceDate":"22小时前","origin":"","originId":8883,"publishTime":1572838363000,"title":"Jetpack + 协程的WanAndroid客户端","userId":31271,"visible":0,"zan":0},{"author":"malonecoder","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"项目采用 Kotlin 语言，自学习项目，使用MVVM架构，RxJava + Retrofit + Glide + LiveDataBus等开源组件，UI漂亮，代码和逻辑简单易懂，适合新手学习Kotlin语言和Jetpack等一系列开发组件","envelopePic":"https://wanandroid.com/blogimgs/f4ab1a6a-6a2c-49be-bae0-f423c24e7d9e.png","id":97193,"link":"http://www.wanandroid.com/blog/show/2645","niceDate":"22小时前","origin":"","originId":8887,"publishTime":1572838361000,"title":"WanAndroid-Kotlin 学习项目","userId":31271,"visible":0,"zan":0},{"author":"asjqkkkk","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"完全使用Flutter编写的TodoList app，是一个小巧、简洁而又漂亮的app，它可以帮你随手记录日常的各项计划,如果你恰好有写任务计划的习惯，那么它一定非常适合你。 ","envelopePic":"https://wanandroid.com/blogimgs/6718c2cd-695c-4eb7-b24a-ccbb10d4dd47.png","id":97191,"link":"http://www.wanandroid.com/blog/show/2640","niceDate":"22小时前","origin":"","originId":8882,"publishTime":1572838358000,"title":"一个非常精美的Flutter Todo-List项目","userId":31271,"visible":0,"zan":0},{"author":"maoqitian","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"这是一款Android原生的开源玩Android客户端App，提供更丰富的功能，更好体验，旨在更好的浏览www.wanandroid.com网站内容，更好的在手机上进行学习，做项目的同时也能提升自我。项目使用Retrofit2 + RxJava2 + Dagger2 +MVP+RxBus架构。","envelopePic":"https://wanandroid.com/blogimgs/0a606e9b-45a2-4800-a4d5-3a1abc25cf67.png","id":97190,"link":"http://www.wanandroid.com/blog/show/2650","niceDate":"22小时前","origin":"","originId":8977,"publishTime":1572838357000,"title":"一款有较好体验的WanAndroid客户端","userId":31271,"visible":0,"zan":0},{"author":"binaryshao","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"玩安卓客户端 Flutter 版，几乎对接了玩安卓的所有 API，内容比较完整 ","envelopePic":"https://www.wanandroid.com/blogimgs/f381b3aa-932e-47d6-af54-3abd7b4c48c8.png","id":97189,"link":"http://www.wanandroid.com/blog/show/2657","niceDate":"22小时前","origin":"","originId":8995,"publishTime":1572838356000,"title":"玩安卓客户端 Flutter 版","userId":31271,"visible":0,"zan":0},{"author":"binaryshao","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"玩 Android 客户端，采用 kotlin 语言，Material Design 风格，根据 MVVM 架构使用 Jetpack 架构组件搭建了整套框架 ","envelopePic":"https://www.wanandroid.com/blogimgs/10491b74-b534-48b1-a5fe-d2ac00e20d2d.png","id":97188,"link":"http://www.wanandroid.com/blog/show/2658","niceDate":"22小时前","origin":"","originId":8996,"publishTime":1572838350000,"title":"玩 Android 客户端  MVVM 架构使用 Jetpack 架构组件 ","userId":31271,"visible":0,"zan":0},{"author":"goweii","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"开发初期主要是为了试水一些自己开发的开源框架，但是后面发现本人对这个APP的使用频率还是挺高的，在坐地铁的时候都会拿出来刷一刷文章。所以决定把这个APP做好看，做好用，不至于影响刷文章的心情。  如果你也觉得好用，欢迎给个star，谢谢。","envelopePic":"https://wanandroid.com/blogimgs/eb948f06-8895-4b67-8bf9-1aa41dea75cb.png","id":97187,"link":"http://www.wanandroid.com/blog/show/2577","niceDate":"22小时前","origin":"","originId":8501,"publishTime":1572838349000,"title":"简洁美观的WanAndroid客户端","userId":31271,"visible":0,"zan":0},{"author":"iamyours","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"适配掘金/简书/CSDN/公众号/玩Android这些站点文章的黑夜模式，去除广告，专注文章内容，给你不一样的阅读体验。","envelopePic":"https://www.wanandroid.com/blogimgs/fad5f9dd-31b5-4328-80b2-8d1fef3dd11d.png","id":97185,"link":"http://www.wanandroid.com/blog/show/2669","niceDate":"22小时前","origin":"","originId":9100,"publishTime":1572838347000,"title":"一款全黑夜玩Android客户端，给你不一样的阅读体验","userId":31271,"visible":0,"zan":0},{"author":"hegaojian","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"一位练习长达两年半的安卓练习生根据鸿神提供的WanAndroid开放Api来制作的产品级App,基本实现了所有的功能，采用Kotlin语言，基于Material Design + AndroidX + MVP + RxJava + Retrofit等优秀的开源框架开发","envelopePic":"https://wanandroid.com/blogimgs/39749be0-f875-48c8-a1b6-c349d286d594.png","id":97186,"link":"http://www.wanandroid.com/blog/show/2666","niceDate":"22小时前","origin":"","originId":9093,"publishTime":1572838347000,"title":"一位练习长达两年半的安卓练习生根据鸿神提供的WanAndroid开放Api来制作的产品级App","userId":31271,"visible":0,"zan":0},{"author":"xiangcman","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"仿苹果版小黄车菜单弹出效果","envelopePic":"https://www.wanandroid.com/blogimgs/8f82a5e5-57e9-4d62-9c94-f1ff2c277802.png","id":97184,"link":"http://www.wanandroid.com/blog/show/2668","niceDate":"22小时前","origin":"","originId":9099,"publishTime":1572838345000,"title":"苹果版小黄车(ofo)app主页菜单效果","userId":31271,"visible":0,"zan":0},{"author":"kbz066","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"时光序 Flutter 版是仿照Android版本的时光序，从开始自学dart到基本完成开发历时一个多月,项目中基本使用到了flutter大部分基础widget，完成了大部分炫酷的特效交互,项目使用flutter 官方推荐的provider进行状态管理.  ","envelopePic":"https://www.wanandroid.com/blogimgs/1c73d4b3-9cff-4c8e-818a-e14e44b00988.png","id":97183,"link":"http://www.wanandroid.com/blog/show/2675","niceDate":"22小时前","origin":"","originId":9111,"publishTime":1572838344000,"title":"Flutter 时光序 新鲜出炉 欢迎大家品尝","userId":31271,"visible":0,"zan":0},{"author":"xuexiangjys","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"一个简洁而又优雅的Android原生UI框架，解放你的双手！还不赶紧点击使用说明文档，体验一下吧！","envelopePic":"https://wanandroid.com/blogimgs/df78f4d6-94c6-4ed8-8b96-999f57a84262.png","id":97182,"link":"http://www.wanandroid.com/blog/show/2680","niceDate":"22小时前","origin":"","originId":9494,"publishTime":1572838343000,"title":"XUI 一个简洁而优雅的Android原生UI框架，解放你的双手！","userId":31271,"visible":0,"zan":0},{"author":"xing16","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"WanAndroid-Kotlin是基于 Kotlin + MVP + RxJava + OkHttp 实现好用好看的学习阅读类客户端, 包括首页，项目，体系，干货，搜索，收藏，妹子等功能","envelopePic":"https://www.wanandroid.com/blogimgs/b4714e97-deb4-4cd4-bf63-e365afe60189.png","id":97181,"link":"http://www.wanandroid.com/blog/show/2684","niceDate":"22小时前","origin":"","originId":9656,"publishTime":1572838342000,"title":"Kotlin 实现美观好用的 WanAndroid 客户端","userId":31271,"visible":0,"zan":0},{"author":"laishujie","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"开源漫画项目，部分界面仿有妖气，Kotlin+MVVM+LiveData+协程+Retrofit。一起加油努力 ","envelopePic":"https://wanandroid.com/blogimgs/e2270cce-cc0b-4971-b382-b1c9f359bec3.png","id":97180,"link":"http://www.wanandroid.com/blog/show/2691","niceDate":"22小时前","origin":"","originId":9841,"publishTime":1572838341000,"title":"开源漫画项目，部分界面仿有妖气","userId":31271,"visible":0,"zan":0},{"author":"ShowMeThe","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"利用wanandroid ApI实现，基于AAC的玩安卓客户端 网络请求利用Retrofit2 + Coroutines，抛弃了RxJava的怀抱完成网络请求部分 已完成 #主页，公众号，知识体系，项目，收藏，登录注册等基本功能 另外为了实现ROOM的应用，本地添加数据，完成基本的登录和数据展示功能 #剩余功能尚未完善，开发中","envelopePic":"https://www.wanandroid.com/blogimgs/1534e4e3-456e-40dd-8f77-2328ce72a388.png","id":97179,"link":"http://www.wanandroid.com/blog/show/2694","niceDate":"22小时前","origin":"","originId":9863,"publishTime":1572838340000,"title":"基于AAC架构玩安卓客户端","userId":31271,"visible":0,"zan":0},{"author":"jsyjst","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"随心音乐，是一款基于MVP+Retrofit+EventBus+Glide的应用，有兴趣的盆友欢迎Star,Fork!","envelopePic":"https://www.wanandroid.com/blogimgs/025c4573-38d9-4cdc-a591-685a73ac7163.png","id":97178,"link":"https://www.wanandroid.com/blog/show/2698","niceDate":"22小时前","origin":"","originId":9867,"publishTime":1572838336000,"title":"随心音乐，让心跟着跳动起来","userId":31271,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":97165,"link":"https://mp.weixin.qq.com/s/m2gQ_OTMs1d47uMquRjI9w","niceDate":"23小时前","origin":"","originId":10055,"publishTime":1572837013000,"title":"为什么要学习跨平台？ Flutter 跨平台框架应用实战","userId":31271,"visible":0,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 2
     * size : 20
     * total : 26
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * author : xiaoyang
         * chapterId : 360
         * chapterName : 小编发布
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 97417
         * link : https://wanandroid.com/blog/show/2701
         * niceDate : 28分钟前
         * origin :
         * originId : 9988
         * publishTime : 1572918158000
         * title : 不好意思哈，很多群可能要被迫解散了，建立了一个新家园！
         * userId : 31271
         * visible : 0
         * zan : 0
         */

        private String author;
        private int chapterId;
        private String chapterName;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private int originId;
        private long publishTime;
        private String title;
        private int userId;
        private int visible;
        private int zan;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getOriginId() {
            return originId;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}
