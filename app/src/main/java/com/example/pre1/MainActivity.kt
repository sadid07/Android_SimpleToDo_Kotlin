package com.example.pre1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    // variables can be reassigned but val can not be reassigned, hence the change
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the List
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        loadItems()


        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)


        // SETUP THE BUTTON THAT HELPS THE USER ADD A TASK TO THE LIST (BISMILLAH)
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //Get a reference to the Button
        //set an On Click Listener to the Button
        findViewById<Button>(R.id.button).setOnClickListener {
            //Grab the text in @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            //Add it to list of items
            listOfTasks.add(userInputtedTask)

            // notifying the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //Reset text field
            inputTextField.setText("")

            saveItems()
            //

        }

    }

    // Save the data that the user has inputted
    // Writing and reading a file

    // Get the file we need

    fun getDataFile() : File {
        // every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }


    // Save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}

