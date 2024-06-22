package com.example.tugasm4_6958

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasm4_6958.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Dummy
        DB.addAccount("User1")
        DB.addAccount("User2")

        DB.addTask("User1", Task("Task 1", "21/05/2024", "This is Task 1", true))
        DB.addTask("User1", Task("Task 2", "20/05/2024", "This is Task 2", false))

        DB.addTask("User2", Task("Task 3", "19/05/2024", "This is Task 3", true))
        DB.addTask("User2", Task("Task 4", "18/05/2024", "This is Task 4", false))

        binding.etMainName.text.clear()

        binding.btnMainEnter.setOnClickListener {
            val name = binding.etMainName.text.toString()

            if (name.isEmpty()) {
                binding.etMainName.error = "Name is required"
                Toast.makeText(this, "Field is Empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (DB.tasks[name] == null) {
                DB.addAccount(name)
            }

            Log.i("List of Account", DB.tasks.keys.toString())

            Toast.makeText(this, "Welcome, $name!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("account", name.toString())
            startActivity(intent)
        }
    }
}