package duke;

import duke.tasks.Task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {

        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }


    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(int index) {
        tasks.remove(index);
    }




}
