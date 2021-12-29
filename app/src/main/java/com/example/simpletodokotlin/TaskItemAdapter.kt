package com.example.simpletodokotlin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
* Acts as a bridge that tells the recyclerview exactly how to lay out the information or how to display the data we give it
*/
class TaskItemAdapter (val listOfItems: List<String>, val longClickListener: OnLongClickListener): RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    // Usually involves inflating a layout from XML and returning the holder
    //suppose to tell the recyclerview how to layout each specific item inside the recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    //involves populating data into the item through holder
    //it takes whatever in the list of items and use that to populate the layout inside view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
/*        // Get the data model based on position
        val contact: Contact = mContacts.get(position)
        // Set item views based on your views and data model
        val textView = viewHolder.nameTextView
        textView.setText(contact.name)
        val button = viewHolder.messageButton
        button.text = if (contact.isOnline) "Message" else "Offline"
        button.isEnabled = contact.isOnline
*/
        //Get the data model (listOfItems) based on position
        val item = listOfItems.get(position)
        //setting the textView to whatever the string item is
        holder.textView.text = item
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    //create a ViewHolder Class
    //Grab references to the views we need so that we can populate data into those views
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Store references to elements in our layout view
        //create a TextView
        val textView: TextView

        //set it by using findViewById
        init {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}