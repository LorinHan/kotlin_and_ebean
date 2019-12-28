package com.example.kotlin_and_ebean.controller

import com.example.kotlin_and_ebean.domain.Author
import com.example.kotlin_and_ebean.domain.query.QAuthor
import io.ebean.EbeanServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class EbeanController {
    @Autowired
    private lateinit var ebeanServer: EbeanServer

    @RequestMapping(value = ["/ebean"], method = [RequestMethod.GET])
    fun getAuthorList(): String {
//        新增
        val author = Author()
//        author.id = 10L
//        author.realName = "test"
//        author.nickName = "test"
//        ebeanServer.save(author)
//        查询
        var find = ebeanServer.find(Author::class.java, 1)
        println(find?.nickName)
        return "ebean"
    }

    @GetMapping("/testEbean")
    fun testEbean(): List<Author> {
//        val author = QAuthor().nickName.equalTo("Lorin").findOne()
//        val authors = QAuthor().nickName.equalTo("test").findList()
        val authors = QAuthor().setMaxRows(2).findList() // 类型是BeanList
        authors.forEach{
            println(it.id)
        }
        for(item in authors) {
            println(item.nickName)
        }
        return authors
    }

    @GetMapping("/testNative")
    fun testNative(): String {
        var findNative = ebeanServer.sqlQuery("select id, real_name from t_author where id <= 2;").findList()
        // 类型应该是List<Any>, 直接toString当做json返回就行了
        println(findNative is List<Any>)
        return findNative.toString()
    }

    @GetMapping("/testKotlin")
    fun testKotlin(): String {
        var arr = intArrayOf(1, 2, 3)
        println(arr[0])
        var arrList = arrayListOf<Int>(1, 2, 3)
        arrList.add(4)
        println(arrList)
        var str = "abc"
        str += "$str 的长度为：${str.length * 2}"
        println(str)
        arr[0] = 2
        when(arr[0]) {
            1 -> println("nice")
            else -> println("fuck")
        }
        println("==========")
        if(3 in arr) {
            println("3 in arr!")
        }
//        for((item, index) in arrList.withIndex()) {
//            println("$item 的下标为$index")
//        }
        arrList.withIndex().forEach{ print(it)}
        var obj = hashMapOf<String, Int>("a" to 1, "b" to 2)
        println(obj)
        return "test kotlin"
    }
}