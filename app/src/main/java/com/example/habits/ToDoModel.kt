package com.example.habits

class ToDoModel{
    companion object Factory{
        fun createList(): ToDoModel =ToDoModel()
    }

    var UID: String? = null
    var itemDataText: String?=null
    var done: Boolean?=false
    var done2: Boolean?=false
    var done3: Boolean?=false
    var done4: Boolean?=false
}