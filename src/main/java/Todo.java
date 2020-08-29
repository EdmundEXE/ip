public class Todo extends Task{
    private boolean isDone;

    public Todo(String description){
        super(description);
        isDone = false;
    }

    public String toString(){
        return  "____________________________________________________________\n" +
                "Got it. I've added this task: \n" +
                "[T]" + "[" + getStatusIcon() + "] " + getDescription() +
                "\nNow you have " + getCounter() + " tasks in the list.\n";
    }

}
