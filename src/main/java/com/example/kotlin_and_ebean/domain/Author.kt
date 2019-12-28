package com.example.kotlin_and_ebean.domain

import io.ebean.Model
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "t_author")
class Author: Model(){
    @Id
    var id: Long? = null
    var realName: String? = null
    var nickName: String? = null
}