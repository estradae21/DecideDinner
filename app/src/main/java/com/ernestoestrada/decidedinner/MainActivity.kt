package com.ernestoestrada.decidedinner

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.OutputStreamWriter
import java.util.*

class MainActivity : AppCompatActivity() {

    val foodList = arrayListOf<CharSequence>("Sushi", "Chinese")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getFoodList()

        var actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        decideBtn.setOnClickListener {
            val random = Random()
            val randomFood = random.nextInt(foodList.count())
            SelectedFood.text = foodList[randomFood]
        }

        addFoodBtn.setOnClickListener {
            val newFood = addFoodTxt.text.toString()
            if (newFood.trim().length > 0){
                foodList.add(newFood)
                addFood(newFood)
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

    fun getFoodList(){
        try {
            val f = openFileInput("dataStorage.txt")
            val br = f.bufferedReader()
            br.forEachLine {
                foodList.add(it)
            }
            f.close()
        }
        catch (ex:Exception){
                print(ex.message)}
        }

    fun addFood(str: String) {
        try {
            val f = openFileOutput("dataStorage.txt", Context.MODE_APPEND)
            val Fout = OutputStreamWriter(f)
            Fout.append(str + "\n")
            Fout.close()
            println(foodList)
        } catch (ex: Exception) {
            print(ex.message)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_help-> {

                Toast.makeText(applicationContext, "You dont need help",
                        Toast.LENGTH_SHORT).show()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

