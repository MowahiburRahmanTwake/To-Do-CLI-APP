import java.io.File


data class Task(val id: Int, var description: String, var isDone: Boolean = false)

object TaskManager{
    private val tasks = mutableListOf<Task>()
    private var nextId = 1

    fun addTask(description: String){
      val task = Task(nextId++,description)
      tasks.add(task)
      println("Task added: ${task.description}")
    }

    fun showTasks(){
        if (tasks.isEmpty()){
            println("No tasks available")
        }else{
            println("------All Tasks------")
            for (task in tasks){
                val status = if (task.isDone) "✔" else "✘"
                println("ID: ${task.id} | $status ${task.description}")
            }
        }
    }
    fun completeTask(id: Int){
        val task = tasks.find { it.id == id}
        if (task != null){
            task.isDone = true
            println("Task ${task.id} marked as completed")
        }else{
            println("Task not found with ID: $id")
        }
    }
    fun saveTaskToFile(){
        val file = File("tasks.txt")
        file.writeText("")


        for (task in tasks){
            val line = "${task.id}|${task.description} | ${task.isDone}"
            file.appendText(line +"\n")
        }
    }
    fun loadTasksFromFile(){
        val file = File("tasks.txt")
        if (file.exists()){
            file.forEachLine { line ->
                val parts = line.split("|")
                if (parts.size == 3){
                    val id = parts[0].toInt()
                    val description = parts[1]
                    val isDone = parts[2].toBoolean()
                    tasks.add(Task(id,description,isDone))
                    if (id >= nextId) nextId = id + 1
                }
            }

        }
    }

    fun editTask(id: Int, newDescription: String){
        val task = tasks.find { it.id == id }
        if (task != null){
            task.description = newDescription
            println("Task ${task.id} updated successfully.")
        }else{
            println("Task not found with ID: $id")
        }
    }

    fun deleteTask(id: Int){
        val task = tasks.find { it.id == id }
        if (task != null){
            tasks.remove(task)
            println("Task ${task.id} deleted successfully.")
        }else{
            println("Task not found with ID: $id")
        }
    }
    fun filterTasks(showCompleted: Boolean) {
        val filtered = tasks.filter { it.isDone == showCompleted }
        if (filtered.isEmpty()) {
            println("No ${if (showCompleted) "completed" else "incomplete"} tasks.")
        } else {
            for (task in filtered) {
                val status = if (task.isDone) "✔" else "✘"
                println("ID: ${task.id} | $status ${task.description}")
            }
        }
    }


}


fun main() {
    TaskManager.loadTasksFromFile()
    while (true){
        println("\n===== Task Manager Menu =====")
        println("1. Add Task")
        println("2. Show Tasks")
        println("3. Complete Task")
        println("4. Exit")
        println("Choose an option: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Enter task description.")
                val desc = readLine()
                if (!desc.isNullOrBlank()) {
                    TaskManager.addTask(desc)
                } else {
                    println("Invalid description")
                }
            }

            2 -> TaskManager.showTasks()
            3 -> {
                print("Enter task ID to complete: ")
                val id = readLine()?.toIntOrNull()
                if (id != null) {
                    TaskManager.completeTask(id)
                } else {
                    println("Invalid ID.")
                }
            }
            4-> {
                println("Enter task ID to delete")
                val id = readLine()?.toIntOrNull()
                if (id != null){
                    TaskManager.deleteTask(id)
                }else{
                    println("Invalid ID.")
                }
            }

            6-> TaskManager.filterTasks(true)
            7-> TaskManager.filterTasks(false)
            8 ->{
                TaskManager.saveTaskToFile()
                println("Exiting... Tasks saved. Goodbye!")
                break
            }
        }



    }
}
















