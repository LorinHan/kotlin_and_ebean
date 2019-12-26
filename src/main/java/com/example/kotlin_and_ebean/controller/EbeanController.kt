package com.example.kotlin_and_ebean.controller

import com.example.kotlin_and_ebean.domain.Author
import io.ebean.EbeanServer
import org.springframework.beans.factory.annotation.Autowired
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
        var find = ebeanServer.find(Author::class.java, 2)
        println(find!!.nickName)
        return "ebean"
    }
}