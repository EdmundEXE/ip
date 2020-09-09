
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner UserInput = new Scanner(System.in);
        int listCounter = 0;
        Task[] myTasks = new Task[100];

        printStartMessage();

        while (true) {
            String command = UserInput.nextLine();  // scan user input
            try {
                while (!command.equals("bye")) {     // prog doesnt end unless "bye"
                    System.out.println("____________________________________________________________\n");

                    if (command.equals("list")) {        // show list
                        if (listCounter == 0) {         // Error: empty list
                            throw new EmptyListException();
                        }
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < listCounter; i++) {
                            System.out.println((i + 1) + "." + myTasks[i]);
                        }

                    } else if (command.startsWith("done")) {       // mark tick
                        int taskNumber = Integer.parseInt(command.substring(5));

                        if (listCounter == 0) {            // Error: empty list
                            throw new EmptyListException();
                        } else if ((taskNumber <= 0) || (taskNumber > (listCounter))) {       // Error: wrong task number
                            throw new InvalidTaskNumber();
                        } else {
                            myTasks[taskNumber - 1].markAsDone();
                            System.out.println("Nice! I've marked this task as done:\n" +
                                    myTasks[taskNumber - 1] +
                                    "\n");
                        }
                    } else if (command.startsWith("todo")) {   // to do command
                        if (command.length() <= 5) {       // Error: missing details
                            throw new InsufficientDescriptionException();
                        }
                        myTasks[listCounter] = new Todo(command.substring(5));
                        System.out.println("Got it. I've added this task: \n" +
                                myTasks[listCounter] +
                                "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
                        listCounter++;
                    } else if (command.startsWith("deadline")) {       // deadline command
                        int x = command.indexOf("/by");         // finds index of /by

                        if ((x == -1) || (command.length() <= 9)) {
                            throw new InsufficientDescriptionException();
                        } else {
                            myTasks[listCounter] = new Deadline(command.substring(9, x), command.substring(x + 4));
                            System.out.println("Got it. I've added this task: \n" +
                                    myTasks[listCounter] +
                                    "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
                            listCounter++;
                        }

                    } else if (command.startsWith("event")) {       // event command
                        int y = command.indexOf("/at");         // finds index of /at

                        if ((y == -1) || (command.length() <= 6)) {
                            throw new InsufficientDescriptionException();
                        } else {
                            myTasks[listCounter] = new Event(command.substring(6, y), command.substring(y + 4));
                            System.out.println("Got it. I've added this task: \n" +
                                    myTasks[listCounter] +
                                    "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
                            listCounter++;
                        }

                    } else {              // Error Unknown Command
                        throw new UnknownCommandException();
                    }

                    System.out.println("____________________________________________________________\n");
                    command = UserInput.nextLine();
                }

                System.out.println("____________________________________________________________\n" +
                        "Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________");
                break;

            } catch (EmptyListException e) {
                System.out.println("Empty list. Add something!\n" +
                        "____________________________________________________________\n");


            } catch (InvalidTaskNumber e) {
                System.out.println("Invalid Task Number!!!\n" +
                        "____________________________________________________________\n");

            } catch (InsufficientDescriptionException e) {
                System.out.println("Hmmm....You didn't add any details...\n" +
                        "____________________________________________________________\n");

            } catch (UnknownCommandException e) {
                System.out.println("This command was not programmed in me...=O\n" +
                        "____________________________________________________________\n");
            }
        }
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