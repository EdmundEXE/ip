
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner UserInput = new Scanner(System.in);
        int listCounter = 0;
        Task[] myTasks = new Task[100];

        printStartMessage();

        String command = UserInput.nextLine();  // scan user input

        while (!command.equals("bye")) {     // prog doesnt end unless "bye"
            System.out.println("____________________________________________________________\n");

            if (command.equals("list")){        // show list
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i<listCounter; i++) {
                    System.out.println( (i+1) + "." + myTasks[i]);
                }

            } else if (command.startsWith("done")){       // mark tick
                int taskNumber = Integer.parseInt(command.substring(5));

                if (listCounter==0){
                    System.out.println("List empty!");
                }
                else if (taskNumber<=0) {
                    System.out.println("Invalid Number!!");
                }
                else {
                    myTasks[taskNumber - 1].markAsDone();
                    System.out.println("Nice! I've marked this task as done:\n" +
                            myTasks[taskNumber - 1] +
                            "\n");
                }
            } else if (command.startsWith("todo")){   // to do command
                myTasks[listCounter] = new Todo(command.substring(5));
                System.out.println("Got it. I've added this task: \n" +
                        myTasks[listCounter] +
                        "\nNow you have " + (listCounter+1) + " tasks in the list.\n");
                listCounter++;
            } else if (command.startsWith("deadline")){       // deadline command
                int x = command.indexOf("/by");         // finds index of /by

                if (x==-1){
                    System.out.println("missing date");
                }
                else {
                    myTasks[listCounter] = new Deadline(command.substring(9, x), command.substring(x + 4));
                    System.out.println("Got it. I've added this task: \n" +
                            myTasks[listCounter] +
                            "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
                    listCounter++;
                }

            } else if (command.startsWith("event")){       // deadline command
                int y = command.indexOf("/at");         // finds index of /by

                if (y==-1){
                    System.out.println("missing date");
                }
                else {
                    myTasks[listCounter] = new Event(command.substring(6, y), command.substring(y + 4));
                    System.out.println("Got it. I've added this task: \n" +
                            myTasks[listCounter] +
                            "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
                    listCounter++;
                }

            } else {              // echo
                System.out.println(command);
            }

            System.out.println("____________________________________________________________\n");
            command = UserInput.nextLine();
        }

        System.out.println("____________________________________________________________\n" +
                "Bye. Hope to see you again soon!\n" +
                "____________________________________________________________");
    }

    public static void printStartMessage() {
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

    }

}