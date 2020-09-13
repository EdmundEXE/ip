package duke.tasks;

public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone(){
        this.isDone = true;
    }

    public String getStatusIcon() {
        return (this.isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public String toString(){
        return "[" + getStatusIcon() + "] " + description;
    }

}