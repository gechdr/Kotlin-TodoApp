package com.example.tugasm4_6958

class DB {
    companion object {
        val tasks: MutableMap<String,ArrayList<Task>> = mutableMapOf()

        fun addAccount(name: String){
            tasks[name] = arrayListOf()
        }

        fun addTask(name: String, task: Task) {
            tasks[name]!!.add(task)
        }
    }
}