package com.example.kotlin_and_ebean.controller;


import com.example.kotlin_and_ebean.domain.Author;
import io.ebean.EbeanServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private EbeanServer ebeanServer;

    @GetMapping("/")
    public String test() {
        Author author = new Author();
        author.setId(1L);
        System.out.println(author.getId());
        return "Hello world";
    }

    @GetMapping("/test2")
    public String test2() {
        Author author = ebeanServer.find(Author.class, 1);
        System.out.println(author.getNickName());
        return "ok";
    }
}
