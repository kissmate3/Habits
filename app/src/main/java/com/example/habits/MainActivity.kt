package com.example.habits

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.time.LocalDate
import kotlin.collections.HashMap
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() ,UpdateAndDelete {
    lateinit var database: DatabaseReference
    var toDOList:MutableList<ToDoModel>?=null
    lateinit var adapter: ToDoAdapter
    private var listViewItem:ListView?=null


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.today)
        val today = LocalDate.now()
        val form="MM.dd"
        val formattedToday = today.format(DateTimeFormatter.ofPattern(form))
        textView.text = formattedToday



        val textView2: TextView = findViewById(R.id.yesterday)
        val tomorrow = LocalDate.now().minusDays(1)
        val formattedTomorrow = tomorrow.format(DateTimeFormatter.ofPattern(form))
        textView2.text = formattedTomorrow

        val textView3: TextView = findViewById(R.id.before_yesterday)
        val beTomorrow = LocalDate.now().minusDays(2)
        val formattedbeTomorrow = beTomorrow.format(DateTimeFormatter.ofPattern(form))
        textView3.text = formattedbeTomorrow


        val fab=findViewById<View>(R.id.fab) as FloatingActionButton
        listViewItem=findViewById<ListView>(R.id.item_listView)


        database=FirebaseDatabase.getInstance().reference

        fab.setOnClickListener{view ->
            val alertDialog=AlertDialog.Builder(this)
            val textEditText=EditText(this)
            alertDialog.setMessage("Add TODO item")
            alertDialog.setTitle("Enter To Do item")
            alertDialog.setView(textEditText)
            alertDialog.setPositiveButton("Add"){dialog, i ->
                val todoItemData=ToDoModel.createList()
                todoItemData.itemDataText=textEditText.text.toString()
                todoItemData.done=false
                todoItemData.done2=false
                todoItemData.done3=false

                val newItemData=database.child("todo").push()
                todoItemData.UID=newItemData.key

                newItemData.setValue(todoItemData)
                dialog.dismiss()
                Toast.makeText(this,"item saved",Toast.LENGTH_LONG).show()
            }
            alertDialog.show()
        }
        toDOList= mutableListOf<ToDoModel>()
        adapter=ToDoAdapter(this,toDOList!!)
        listViewItem!!.adapter=adapter
        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"No itemAdded",Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                toDOList!!.clear()
                addItemToList(snapshot)
            }

        })
    }

    private fun addItemToList(snapshot: DataSnapshot) {

        val items=snapshot.children.iterator()

        if(items.hasNext()){
            val toDoIndexedValue=items.next()
            val itemsIterator=toDoIndexedValue.children.iterator()

            while(itemsIterator.hasNext()){
                val currentItem=itemsIterator.next()
                val toDoItemData=ToDoModel.createList()
                val map=currentItem.getValue() as HashMap<String, Any>

                toDoItemData.UID=currentItem.key
                toDoItemData.done=map.get("done") as Boolean?
                toDoItemData.done2=map.get("done2") as Boolean?
                toDoItemData.done3=map.get("done3") as Boolean?
                toDoItemData.itemDataText= map["itemDataText"] as String?
                toDOList!!.add(toDoItemData)
            }

        }

        adapter.notifyDataSetChanged()

    }

    override fun modifyItem(itemUID: String, isDone: Boolean) {
        val itemReference=database.child("todo").child(itemUID)
        itemReference.child("done").setValue(isDone)
    }

    override fun modifyItem2(itemUID: String, isDone2: Boolean) {
        val itemReference=database.child("todo").child(itemUID)
        itemReference.child("done2").setValue(isDone2)
    }

    override fun modifyItem3(itemUID: String, isDone3: Boolean) {
        val itemReference=database.child("todo").child(itemUID)
        itemReference.child("done3").setValue(isDone3)
    }


    override fun onItemDelete(itemUID: String) {
        val itemReference=database.child("todo").child(itemUID)
        itemReference.removeValue()
        adapter.notifyDataSetChanged()
    }


}