package com.example.habits

interface UpdateAndDelete{

    fun modifyItem(itemUID :String, isDone :Boolean )
    fun modifyItem2(itemUID :String, isDone :Boolean )
    fun modifyItem3(itemUID :String, isDone :Boolean )
    fun modifyItem4(itemUID :String, isDone :Boolean )
    fun onItemDelete(itemUID: String)

}