import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner UserInput = new Scanner(System.in);
        int listCounter = 0;
        Task[] myTasks = new Task[100];

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("____________________________________________________________\n" +
                " Hello! I'm Duke\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n");

        String command = UserInput.nextLine();  // scan user input

        while (!command.equals("bye")) {     // prog doesnt end unless "bye"
            if (command.equals("list")){
                System.out.println("____________________________________________________________\n" +
                        "Here are the tasks in your list:");
                for (int i = 0; i<listCounter; i++) {
                    System.out.println( (i+1) + ". [" + myTasks[i].getStatusIcon() + "] " + myTasks[i].getDescription());
                }

            }
            else if (command.startsWith("done")){
                int taskNumber = Integer.parseInt(command.substring(5));

                if (listCounter==0){
                    System.out.println("____________________________________________________________\n" +
                            "List empty!");
                }
                else if (taskNumber<=0) {
                    System.out.println("____________________________________________________________\n" +
                            "Invalid Number!!");
                }
                else {
                    myTasks[taskNumber - 1].markAsDone();
                    System.out.println("____________________________________________________________\n" +
                            "Nice! I've marked this task as done:\n" +
                            "  [" + myTasks[taskNumber - 1].getStatusIcon() + "] " + myTasks[taskNumber - 1].getDescription() +
                            "\n");
                }

            }
            else {
                myTasks[listCounter] = new Task(command);
                listCounter++;
            }
            System.out.println("____________________________________________________________\n");
            command = UserInput.nextLine();
        }

        System.out.println("____________________________________________________________\n" +
                "Bye. Hope to see you again soon!\n" +
                "____________________________________________________________");
    }

}