package com.example.kotlin_and_ebean.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "t_author")
class Author{
    @Id
    var id: Long? = null
    var realName: String? = null
    var nickName: String? = null
}