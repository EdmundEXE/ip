public class Todo extends Task{
    private boolean isDone;

    public Todo(String description){
        super(description);
        isDone = false;
    }

    @Override
    public String getDescription() {
        return "[T][" + getStatusIcon() + "] " + super.getDescription();
    }

    public String toString(){
        return  "____________________________________________________________\n" +
                "Got it. I've added this task: \n" +
                getDescription() +
                "\nNow you have " + getCounter() + " tasks in the list.\n";
    }

}
