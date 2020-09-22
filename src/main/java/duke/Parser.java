package duke;


import duke.tasks.*;
import duke.exceptions.*;
import java.io.IOException;

import static duke.Duke.listCounter;
import static duke.Storage.*;

public class Parser {

    public final static int TASK_INTEGER = 0;
    public final static int DEADLINE_INTEGER = 1;
    public final static int EVENT_INTEGER = 2;


    public static void parse(String input,TaskList tasks) throws UnknownCommandException, EmptyListException, InvalidTaskNumber, InsufficientDescriptionException, IOException {

        String[] command = input.split(" ",2);
        System.out.println("____________________________________________________________\n");

        switch (command[0]) {
        case "list":
            listCommand(tasks);
            break;
        case "done":
            int taskNumber = Integer.parseInt(command[1]);
            doneCommand(taskNumber,tasks);
            break;
        case "todo":
            todoCommand(command[1],tasks);
            int boolToNumber = tasks.get(listCounter-1).getIsDone() ? 1 : 0;
            appendFile("tasks.txt", translateIntoText(TASK_INTEGER,boolToNumber, command[1]));
            break;
        case "deadline":
            deadlineCommand(command[1],tasks);
            boolToNumber = tasks.get(listCounter-1).getIsDone() ? 1 : 0;
            appendFile("tasks.txt", translateIntoText(DEADLINE_INTEGER,boolToNumber, command[1]));
            break;
        case "event":
            eventCommand(command[1],tasks);
            boolToNumber = tasks.get(listCounter-1).getIsDone() ? 1 : 0;
            appendFile("tasks.txt", translateIntoText(EVENT_INTEGER,boolToNumber, command[1]));
            break;
        case "delete":
            taskNumber = Integer.parseInt(command[1]);
            deleteCommand(taskNumber,tasks);
            Storage.delete(tasks);
            break;
        case "find":
            findCommand(command[1],tasks);
            break;
        case "bye":
            byeCommand();
            break;
        default :
            throw new UnknownCommandException();
        }
    }

    public static String translateIntoText(int mode, int boolToNumber, String description) {

        switch (mode) {
        case 0:         // to do
            return "T | " + boolToNumber + " | " +  description;        // T | 1 | blabla
        case 1:         // deadline
            description = description.replace("/by","|");
            description = description.replace("(","");
            description = description.replace(")","");
            return "D | " + boolToNumber + " | " + description;         // D | 1 | blabla | 2222
        default:        // event
            description = description.replace("/at","|");
            description = description.replace("(","");
            description = description.replace(")","");
            return "E | " + boolToNumber + " | " + description;         // E | 1 | blablabla | 3333
        }
    }

    private static void listCommand(TaskList tasks) throws EmptyListException {   // show list

        if (listCounter == 0) {         // Error: empty list
            throw new EmptyListException();
        }

        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < listCounter; i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }

    }

    private static void doneCommand(int taskNumber,TaskList tasks) throws EmptyListException, InvalidTaskNumber {    // mark tick

        if (listCounter == 0) {            // Error: empty list
            throw new EmptyListException();
        } else if ((taskNumber <= 0) || (taskNumber > (listCounter))) {       // Error: wrong task number
            throw new InvalidTaskNumber();
        } else {
            tasks.get(taskNumber - 1).markAsDone();
            System.out.println("Nice! I've marked this task as done:\n" +
                    tasks.get(taskNumber - 1) +
                    "\n");
        }
    }

    private static void todoCommand(String description, TaskList tasks) throws InsufficientDescriptionException {

        if (description.isEmpty()) {
            throw new InsufficientDescriptionException();
        }
        tasks.add(new Todo(description));
        System.out.println("Got it. I've added this task: \n" +
                tasks.get(listCounter) +
                "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
        listCounter++;

    }

    private static void deadlineCommand(String input, TaskList tasks) throws InsufficientDescriptionException {

        String[] description = input.split("/by ");         // finds index of /by

        if (description.length == 1 || description[1].isEmpty()) {          // no /by found
            throw new InsufficientDescriptionException();
        } else {
            tasks.add(new Deadline(description[0], description[1]));
            System.out.println("Got it. I've added this task: \n" +
                    tasks.get(listCounter) +
                    "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
            listCounter++;
        }
    }

    private static void eventCommand(String input, TaskList tasks) throws InsufficientDescriptionException{

        String[] description = input.split("/at ");      // finds index of /at

        if (description.length == 1 || description[1].isEmpty()) {
            throw new InsufficientDescriptionException();
        } else {
            tasks.add(new Event(description[0], description[1]));
            System.out.println("Got it. I've added this task: \n" +
                    tasks.get(listCounter) +
                    "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
            listCounter++;
        }
    }

    private static void deleteCommand(int taskNumber, TaskList tasks) throws EmptyListException, InvalidTaskNumber {


        if (listCounter == 0) {            // Error: empty list
            throw new EmptyListException();
        } else if ((taskNumber <= 0) || (taskNumber > (listCounter))) {       // Error: wrong task number
            throw new InvalidTaskNumber();
        } else {
            System.out.println("Noted! I've removed this task:\n" +
                    tasks.get(taskNumber - 1) +
                    "\nNow you have " + (listCounter-1) + " tasks left.");
            tasks.remove(taskNumber - 1);
            listCounter--;
        }
    }

    private static void byeCommand() {
        System.out.println("____________________________________________________________\n" +
                "Bye. Hope to see you again soon!\n" +
                "____________________________________________________________");
        System.exit(0);

    }

    private static void findCommand(String input, TaskList tasks) {

        int j = 0;
        for (int i = 0; i < listCounter; i++) {
            if (tasks.get(i).toString().contains(input)) {
                System.out.println("Here are the matches found:\n");
                System.out.println((j + 1) + "." + tasks.get(i));
            } else {
                System.out.println("No matches found...\n");
            }
        }
    }

}
