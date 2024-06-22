package com.example.tugasm4_6958

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugasm4_6958.databinding.ActivityTaskBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityTaskBinding
    lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val account = intent.getStringExtra("account")

        binding.tvTaskTitle.text = "$account's Tasks"

        val layoutManager = GridLayoutManager(this, 2)

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val inialData = DB.tasks[account]!!
        inialData.sortWith(compareByDescending<Task> { it.isUrgent }.thenBy { format.parse(it.deadline) })
        taskAdapter = TaskAdapter(inialData)

        binding.rvTask.apply {
            this.layoutManager = layoutManager
            this.adapter = taskAdapter
        }

        binding.rbTaskDeadline.setOnClickListener {
            val temp = DB.tasks[account]!!
            temp.sortBy { format.parse(it.deadline) }

            taskAdapter = TaskAdapter(temp)
            binding.rvTask.adapter = taskAdapter
        }

        binding.rbTaskUrgent.setOnClickListener {
            val temp = DB.tasks[account]!!
            temp.sortWith(compareByDescending<Task> { it.isUrgent }.thenBy { format.parse(it.deadline) })

            taskAdapter = TaskAdapter(temp)
            binding.rvTask.adapter = taskAdapter
        }

        binding.btnTaskAdd.setOnClickListener {

            val taskName = binding.etTaskName.text.toString()
            val taskDate = binding.etTaskDeadline.text.toString()
            val taskDesc = binding.etTaskDescription.text.toString()
            val taskStatus = binding.cbTaskUrgent.isChecked

            if (taskName.isEmpty() || taskDate.isEmpty() || taskDesc.isEmpty()) {
                Toast.makeText(this, "Field is Empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Date
            val date = taskDate.split("/")

            if (date.size != 3) {
                Toast.makeText(this, "Invalid Date!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!date[0].isDigitsOnly() || !date[1].isDigitsOnly() || !date[2].isDigitsOnly()) {
                Toast.makeText(this, "Invalid Date!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (date[0].length != 2 || date[1].length != 2 || date[2].length != 4) {
                Toast.makeText(this, "Invalid Date!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val day = date[0].toInt()
            val month = date[1].toInt()
            val year = date[2].toInt()

            if (day < 1 || day > 30 || month < 1 || month > 12 || year < 1000 || year > 9999) {
                Toast.makeText(this, "Invalid Date!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            DB.addTask(account!!, Task(taskName, taskDate, taskDesc, taskStatus))

            if (binding.rbTaskDeadline.isChecked){
                val temp = DB.tasks[account]!!
                temp.sortBy { format.parse(it.deadline) }

                taskAdapter = TaskAdapter(temp)
                binding.rvTask.adapter = taskAdapter
            } else {
                val temp = DB.tasks[account]!!
                temp.sortWith(compareByDescending<Task> { it.isUrgent }.thenBy { format.parse(it.deadline) })

                taskAdapter = TaskAdapter(temp)
                binding.rvTask.adapter = taskAdapter
            }

            taskAdapter.notifyItemChanged(DB.tasks[account]!!.size - 1)

            Toast.makeText(this, "Task Added!", Toast.LENGTH_SHORT).show()
        }

        binding.btnTaskQuote.setOnClickListener {
            val intent = Intent(this, QuoteActivity::class.java)
            startActivity(intent)
        }

        binding.btnTaskExit.setOnClickListener {
            finish()
        }
    }
}