package com.ernestoestrada.decidedinner

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ernestoestrada.decidedinner.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.selector
import java.io.OutputStreamWriter
import java.util.*

class MainActivity : AppCompatActivity() {

    private val FNAME = "dataStorage.txt"
    val foodList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        decideBtn.setOnClickListener {
            if (foodList.isNotEmpty()) {
                decideBtn()
            }
            else
                Toast.makeText(applicationContext, "You have no food items",
                        Toast.LENGTH_SHORT).show()
        }

        addFoodBtn.setOnClickListener {
            val newFood = addFoodTxt.text.toString()
            if (newFood.trim().length > 0){
                foodList.add(newFood)
                addFoodTxt.text.clear()
                Toast.makeText(applicationContext, "Your food item was saved",
                        Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "Cannot be empty!",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun decideBtn(){
        val random = Random()
        val randomFood = random.nextInt(foodList.count())
        SelectedFood.text = foodList[randomFood]
    }

    fun getFoodList(){
        try {
            val f = openFileInput(FNAME)
            val br = f.bufferedReader()
            br.forEachLine {
                foodList.add(it)
                println(it)
            }
            f.close()
        }
        catch (ex:Exception){
                print(ex.message)}
        }

    fun saveList() {
        try {
            val f = openFileOutput(FNAME, Context.MODE_PRIVATE)
            val Fout = OutputStreamWriter(f)
            for (element in foodList){
                Fout.append("$element\n")
                println(element)
            }

            Fout.close()
        } catch (ex: Exception) {
            print(ex.message)
        }
    }

    fun deleteItem(){
        //getFoodList()
        selector("Which one to delete?" , foodList, {dialogInterface, i ->
            foodList.removeAt(i)
            Toast.makeText(applicationContext, "It has been removed",
                Toast.LENGTH_SHORT).show()})

    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_help-> {

                Toast.makeText(applicationContext, "You dont need help",
                        Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_delete -> {
                deleteItem()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        foodList.clear()
        getFoodList()
    }

    override fun onPause() {
        super.onPause()
        saveList()
    }
}

