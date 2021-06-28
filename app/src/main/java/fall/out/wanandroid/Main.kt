package fall.out.wanandroid

import java.io.File

/**
 * Created by jsxiaoshui on 2021/6/24
 */
fun main(){
    var file=File("D:\\AndroidRel\\MyCode\\playandroid-kotlin\\app\\src\\main\\java\\fall\\out\\wanandroid\\Main.kt")
        .inputStream().reader().buffered().use {
            it.readLines().forEach(::println)
            55
        }
    println("返回值："+file+"")
    var chars=File("D:\\AndroidRel\\MyCode\\playandroid-kotlin\\app\\src\\main\\java\\fall\\out\\wanandroid\\Main.kt")
        .readText().toCharArray().filterNot {
            it.isWhitespace()
        }.groupBy {
            it
        }.map {
            it.key+"出现次数"+it.value.size
           //it
        }.let {
            println(it)
        }
    println(chars)

}