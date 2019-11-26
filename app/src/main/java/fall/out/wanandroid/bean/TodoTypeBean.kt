package fall.out.wanandroid.bean

/**
 * Created by Owen on 2019/11/21
 */
class TodoTypeBean {
    var type: Int = 0
    var name: String? = null
    var isSelect: Boolean = false
    var playType:String=""

    constructor(type: Int, name: String?, isSelect: Boolean) {
        this.type = type
        this.name = name
        this.isSelect = isSelect
    }
}
