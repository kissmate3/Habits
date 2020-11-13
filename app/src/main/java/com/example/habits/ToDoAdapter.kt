package com.example.habits

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ToDoAdapter(context: Context,toDoList:MutableList<ToDoModel>) : BaseAdapter() {

    private val inflater:LayoutInflater= LayoutInflater.from(context)
    private var itemList=toDoList
    private var updateAndDelete: UpdateAndDelete = context as UpdateAndDelete


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val UID: String = itemList[p0].UID as String
        val itemTextData = itemList[p0].itemDataText as String
        val done: Boolean = itemList[p0].done as Boolean
        val done2: Boolean = itemList[p0].done2 as Boolean
        val done3: Boolean = itemList[p0].done3 as Boolean
        val done4: Boolean = itemList[p0].done4 as Boolean

        val view: View
        val viewHolder: ListViewHolder

        if (p1 == null) {
            view = inflater.inflate(R.layout.row_itemslayout, p2, false)
            viewHolder = ListViewHolder(view)
            view.tag = viewHolder
        } else {
            view = p1
            viewHolder = view.tag as ListViewHolder
        }

        viewHolder.textLabel.text = itemTextData
        viewHolder.isDone.isChecked = done
        viewHolder.isDone2.isChecked = done2
        viewHolder.isDone3.isChecked = done3
        viewHolder.isDone4.isChecked = done4

        viewHolder.isDone.setOnClickListener {
            updateAndDelete.modifyItem(UID, !done)
        }


        viewHolder.isDone2.setOnClickListener {
            updateAndDelete.modifyItem2(UID, !done2)
        }

        viewHolder.isDone3.setOnClickListener {
            updateAndDelete.modifyItem3(UID, !done3)
        }

        viewHolder.isDone4.setOnClickListener {
            updateAndDelete.modifyItem4(UID, !done4)
        }

        viewHolder.isDeleted.setOnClickListener{
            updateAndDelete.onItemDelete(UID)
        }

        return view
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(p0: Int): Any {
        return itemList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }




}

private class ListViewHolder(row:View?) {
    val textLabel: TextView=row!!.findViewById(R.id.item_textView) as TextView
    val isDone: CheckBox =row!!.findViewById(R.id.checkbox) as CheckBox
    val isDone2: CheckBox =row!!.findViewById(R.id.checkbox2) as CheckBox
    val isDone3: CheckBox =row!!.findViewById(R.id.checkbox3) as CheckBox
    val isDone4: CheckBox =row!!.findViewById(R.id.checkbox4) as CheckBox
    val isDeleted:ImageButton=row!!.findViewById(R.id.close) as ImageButton
}
