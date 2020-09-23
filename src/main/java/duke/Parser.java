package duke;


import duke.tasks.*;
import duke.exceptions.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static duke.Duke.listCounter;
import static duke.Storage.*;

/**
 * A class that deals with inputs by the user
 */
public class Parser {

    public final static int TASK_INTEGER = 0;
    public final static int DEADLINE_INTEGER = 1;
    public final static int EVENT_INTEGER = 2;

    /**
     * Returns different functions with respect to the user input
     * If user input does not match with any of the commands, the user
     * can input another command again.
     *
     * @param input input of the user
     * @param tasks ArrayList of task
     * @throws UnknownCommandException If user input does not match with any of the commands.
     * @throws EmptyListException If user asks for an empty list.
     * @throws InvalidTaskNumberException If the user inputs an out of bounds task number.
     * @throws InsufficientDescriptionException If user input is missing some details.
     * @throws IOException If file cannot be written onto
     * @throws InvalidDateTimeException If format of date and time inputted by user and Duke is mismatched
     */
    public static void parse(String input,TaskList tasks) throws UnknownCommandException, EmptyListException,
            InvalidTaskNumberException, InsufficientDescriptionException, IOException, InvalidDateTimeException {

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
            String[] description = command[1].split("/by ");
            description[1] = formatDateTime(description[1]);
            appendFile("tasks.txt", translateIntoText(DEADLINE_INTEGER,boolToNumber, description[0] + "/by " + description[1]));
            break;
        case "event":
            eventCommand(command[1],tasks);
            boolToNumber = tasks.get(listCounter-1).getIsDone() ? 1 : 0;
            description = command[1].split("/at ");
            description[1] = formatDateTime(description[1]);
            appendFile("tasks.txt", translateIntoText(EVENT_INTEGER,boolToNumber, description[0] + "/at " + description[1]));
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

    /**
     * Returns a string of the format specified.
     *
     * @param mode The type of the task
     * @param boolToNumber The number for boolean 'true' and 'false'
     * @param description The string that needs to be converted to the format specified
     * @return Formatted String.
     */
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

    /**
     * Prints all the tasks stored in the list
     *
     * @param tasks The list of tasks
     * @throws EmptyListException If the current list is empty.
     */
    private static void listCommand(TaskList tasks) throws EmptyListException {

        if (listCounter == 0) {         // empty list
            throw new EmptyListException();
        }

        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < listCounter; i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }

    }

    /**
     * Mark a task as done.
     *
     * @param taskNumber The number that the task is marked to.
     * @param tasks The list of tasks.
     * @throws EmptyListException If the current list is empty.
     * @throws InvalidTaskNumberException If the user inputs an out of bounds number.
     */
    private static void doneCommand(int taskNumber,TaskList tasks) throws EmptyListException, InvalidTaskNumberException {

        if (listCounter == 0) {            // empty list
            throw new EmptyListException();
        } else if ((taskNumber <= 0) || (taskNumber > (listCounter))) {       // wrong task number
            throw new InvalidTaskNumberException();
        } else {
            tasks.get(taskNumber - 1).markAsDone();
            System.out.println("Nice! I've marked this task as done:\n" +
                    tasks.get(taskNumber - 1) +
                    "\n");
        }
    }

    /**
     * Adds a task of 'todo' into the list
     *
     * @param description The details to be added to the task.
     * @param tasks The list of tasks.
     * @throws InsufficientDescriptionException If the user input has missing details.
     */
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

    /**
     * Adds a task of 'deadline' into the list
     *
     * @param input The details to be added to the task.
     * @param tasks The list of tasks.
     * @throws InsufficientDescriptionException If the user input has missing details.
     * @throws InvalidDateTimeException If the format of date/time inputted by the user is of the wrong format
     */
    private static void deadlineCommand(String input, TaskList tasks) throws InsufficientDescriptionException, InvalidDateTimeException {

        String[] description = input.split("/by ");         // divides the input into 2 parts

        if (description.length == 1 || description[1].isEmpty()) {
            throw new InsufficientDescriptionException();
        } else {
            description[1] = formatDateTime(description[1]);
            tasks.add(new Deadline(description[0], description[1]));
            System.out.println("Got it. I've added this task: \n" +
                    tasks.get(listCounter) +
                    "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
            listCounter++;
        }
    }

    /**
     * Adds a task of 'event' into the list
     *
     * @param input The details to be added to the task.
     * @param tasks The list of tasks.
     * @throws InsufficientDescriptionException If the user input has missing details.
     * @throws InvalidDateTimeException If the format of date/time inputted by the user is of the wrong format
     */
    private static void eventCommand(String input, TaskList tasks) throws InsufficientDescriptionException, InvalidDateTimeException {

        String[] description = input.split("/at ");      // divides the input into 2 parts

        if (description.length == 1 || description[1].isEmpty()) {
            throw new InsufficientDescriptionException();
        } else {
            description[1] = formatDateTime(description[1]);
            tasks.add(new Event(description[0], description[1]));
            System.out.println("Got it. I've added this task: \n" +
                    tasks.get(listCounter) +
                    "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
            listCounter++;
        }
    }

    /**
     * Removes a specified task from the list
     *
     * @param taskNumber The number assigned to the task that needs to be deleted.
     * @param tasks The list of tasks.
     * @throws EmptyListException If the current list is empty.
     * @throws InvalidTaskNumberException If the user inputs an out of bounds number.
     */
    private static void deleteCommand(int taskNumber, TaskList tasks) throws EmptyListException, InvalidTaskNumberException {


        if (listCounter == 0) {            // empty list
            throw new EmptyListException();
        } else if ((taskNumber <= 0) || (taskNumber > (listCounter))) {       // wrong task number
            throw new InvalidTaskNumberException();
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

    /**
     * Prints all tasks that matches with the search item.
     *
     * @param input The string to be searched from the list of tasks.
     * @param tasks The list of tasks.
     * @throws InsufficientDescriptionException If no search item is inputted by the user.
     */
    private static void findCommand(String input, TaskList tasks) throws InsufficientDescriptionException{
        if (input.length()<=6) {
            throw new InsufficientDescriptionException();
        }

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

    /**
     * Returns a formatted date time.
     *
     * @param input The date and time to be formatted.
     * @throws InvalidDateTimeException If there is a mismatch of input and accepted date/time format.
     */
    private static String formatDateTime(String input) throws InvalidDateTimeException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("E, MMM dd yyyy");
        SimpleDateFormat inputDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HHmm");
        SimpleDateFormat outputDateTimeFormat = new SimpleDateFormat("E, MMM dd yyyy, h:mm a");
        String[] dateTime = input.split(" ");
        String reformattedDateTime;

        try {
            if (dateTime.length == 1 || dateTime[1].isEmpty()) {        // only date is inputted by the user
                Date _date = inputDateFormat.parse(input);
                reformattedDateTime = outputDateFormat.format(_date);
            } else {
                Date _date = inputDateTimeFormat.parse(input);
                reformattedDateTime = outputDateTimeFormat.format(_date);
            }
            return reformattedDateTime;
        } catch (ParseException e) {
            throw new InvalidDateTimeException();
        }
    }

}
