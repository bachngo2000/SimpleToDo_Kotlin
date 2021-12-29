package com.example.simpletodokotlin

//handle user interactions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    //holds a list of tasks of type String
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //How activity_main.xml and MainActivity.kt are connected: when the MainActivity.kt file runs, its layout is going to be set to be whatever is in the activity_main.xml file

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //1. Remove the item from the list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }
        //1. Let's detect whenever a user clicks on the add button:
        //graph a reference to the button in activity_main.xml via its id: find a Button view by the id "button"
       /* findViewById<Button>(R.id.button).setOnClickListener {
            //Code in here is going to be executed when the user clicks on a button
            Log.i("Bach", "User clicked on button") //this log statement will print out a log line with the specified tag, along with the given message to Logcat. When the user clicks the "Add" button, this message will be printed to the logcat
        }
        */

        loadItems()

        // Look up the recyclerview in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        //recyclerview and adapter are connected here
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button and input field, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //Get a reference to the button
        //and then set an onClicklistener to it
        findViewById<Button>(R.id.button).setOnClickListener {

            //1. Grab the text the user has inputted into the @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            //2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add((userInputtedTask))

            //Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3. Reset text field (clear the text field after user input)
            inputTextField.setText("")

            saveItems()

        }
    }

    // Save the data that the user has inputted
    //Save data by writing and reading from a file

   // Get the data file we need
    fun getDataFile() : File {

        //Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    //Load the item by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    //Save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }   catch (ioException: IOException) {
                ioException.printStackTrace()
        }
    }

}