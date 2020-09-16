package duke;
import duke.tasks.*;
import duke.exceptions.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Duke {

    public static int listCounter = 0;
    public static ArrayList<Task> myTasks = new ArrayList<>();
    public final static int TASK_INTEGER = 0;
    public final static int DEADLINE_INTEGER = 1;
    public final static int EVENT_INTEGER = 2;

    public static void main(String[] args) {

        printStartMessage();

        try {
            File f = new File("tasks.txt");

            if (!f.exists()) {
                System.out.println("Cannot find file..... Creating one...\n" +
                        "____________________________________________________________\n");
                f.createNewFile();
            } else {
                System.out.println("Here are the tasks on your list:\n");
                readFile("tasks.txt");
                System.out.println("____________________________________________________________\n");

            }

        }  catch (IOException e) {
            System.out.println("Cannot create file!!! Exception Occured: ");
            e.printStackTrace();
        }

        Scanner UserInput = new Scanner(System.in);

        while (true) {
            String input = UserInput.nextLine();  // scan user input
            String[] command = input.split(" ",2);
            System.out.println("____________________________________________________________\n");
            try {
                switch (command[0]) {
                case "list":
                    listCommand();
                    break;
                case "done":
                    int taskNumber = Integer.parseInt(command[1]);
                    doneCommand(taskNumber);
                    break;
                case "todo":
                    todoCommand(command[1]);
                    int boolToNumber = myTasks.get(listCounter-1).getIsDone() ? 1 : 0;
                    appendFile("tasks.txt", translateIntoText(TASK_INTEGER,boolToNumber, command[1]));
                    break;
                case "deadline":
                    deadlineCommand(command[1]);
                    boolToNumber = myTasks.get(listCounter-1).getIsDone() ? 1 : 0;
                    appendFile("tasks.txt", translateIntoText(DEADLINE_INTEGER,boolToNumber, command[1]));
                    break;
                case "event":
                    eventCommand(command[1]);
                    boolToNumber = myTasks.get(listCounter-1).getIsDone() ? 1 : 0;
                    appendFile("tasks.txt", translateIntoText(EVENT_INTEGER,boolToNumber, command[1]));
                    break;
                case "delete":
                    taskNumber = Integer.parseInt(command[1]);
                    deleteCommand(taskNumber);
                    File temp = new File("temp.txt");
                    temp.createNewFile();
                    File original = new File("tasks.txt");
                    String s2;
                    int mode;

                    for (int i = 0; i < listCounter; i++) {
                        String s1 = String.valueOf(myTasks.get(i)).substring(1, 2);
                        boolToNumber = myTasks.get(i).getIsDone() ? 1 : 0;


                        switch (s1) {
                        case "T":       // [T][?] blabla
                            s2 = myTasks.get(i).toString().substring(6);
                            mode = 0;
                            break;
                        case "D":        // E or D
                            s2 = myTasks.get(i).toString().substring(6).replace("by:", "/by");
                            mode = 1;
                            break;
                        default:
                            s2 = myTasks.get(i).toString().substring(6).replace("at:", "/at");
                            mode = 2;
                            break;
                        }

                        appendFile("temp.txt", translateIntoText(mode,boolToNumber,s2));
                    }
                    original.delete();
                    temp.renameTo(original);

                    break;
                case "bye":
                    byeCommand();
                    break;
                default :
                    throw new UnknownCommandException();
                }
            } catch (EmptyListException e) {
                System.out.println("Empty list. Add something!\n");

            } catch (InvalidTaskNumber e) {
                System.out.println("Invalid Task Number!!!\n");

            } catch (InsufficientDescriptionException e) {
                System.out.println("Hmmm....You didn't add any details...\n");

            } catch (UnknownCommandException e) {
                System.out.println("This command was not programmed in me...=O\n");

            } catch (IOException e) {
                System.out.println("Error writing to file!\n");

            } finally {
                System.out.println("____________________________________________________________\n");
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

    private static void listCommand() throws EmptyListException {   // show list
        if (listCounter == 0) {         // Error: empty list
            throw new EmptyListException();
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < listCounter; i++) {
            System.out.println((i + 1) + "." + myTasks.get(i));
        }
    }

    private static void doneCommand(int taskNumber) throws EmptyListException,InvalidTaskNumber {    // mark tick

        if (listCounter == 0) {            // Error: empty list
            throw new EmptyListException();
        } else if ((taskNumber <= 0) || (taskNumber > (listCounter))) {       // Error: wrong task number
            throw new InvalidTaskNumber();
        } else {
            myTasks.get(taskNumber - 1).markAsDone();
            System.out.println("Nice! I've marked this task as done:\n" +
                                myTasks.get(taskNumber - 1) +
                                "\n");
        }
    }

    private static void todoCommand(String description) throws InsufficientDescriptionException {

        if (description.isEmpty()) {
            throw new InsufficientDescriptionException();
        }
        myTasks.add(new Todo(description));
        System.out.println("Got it. I've added this task: \n" +
                            myTasks.get(listCounter) +
                            "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
        listCounter++;

    }

    private static void deadlineCommand(String description) throws InsufficientDescriptionException {
        int x = description.indexOf("/by");         // finds index of /by

        if (x == -1) {          // no /by found
            throw new InsufficientDescriptionException();
        } else {
            myTasks.add(new Deadline(description.substring(0, x), description.substring(x + 4)));
            System.out.println("Got it. I've added this task: \n" +
                                myTasks.get(listCounter) +
                                "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
            listCounter++;
        }
    }

    private static void eventCommand(String description) throws InsufficientDescriptionException{
        int y = description.indexOf("/at");         // finds index of /at

        if (y == -1) {
            throw new InsufficientDescriptionException();
        } else {
            myTasks.add(new Event(description.substring(0, y), description.substring(y + 4)));
            System.out.println("Got it. I've added this task: \n" +
                                myTasks.get(listCounter) +
                                "\nNow you have " + (listCounter + 1) + " tasks in the list.\n");
            listCounter++;
        }
    }

    private static void deleteCommand(int taskNumber) throws EmptyListException, InvalidTaskNumber {

        if (listCounter == 0) {            // Error: empty list
            throw new EmptyListException();
        } else if ((taskNumber <= 0) || (taskNumber > (listCounter))) {       // Error: wrong task number
            throw new InvalidTaskNumber();
        } else {
            System.out.println("Noted! I've removed this task:\n" +
            myTasks.get(taskNumber - 1) +
            "\nNow you have " + (listCounter-1) + " tasks left.");
            myTasks.remove(taskNumber - 1);
            listCounter--;
        }
    }

    private static void byeCommand() {
        System.out.println("____________________________________________________________\n" +
        "Bye. Hope to see you again soon!\n" +
        "____________________________________________________________");
        System.exit(0);

    }

    private static void readFile(String pathName) throws FileNotFoundException {    // read and add to current list in Duke
        File f = new File(pathName);
        Scanner s = new Scanner(f);

        while (s.hasNext()) {
            String fileOutputLine = s.nextLine();
            System.out.println(fileOutputLine);
            String[] textDescription = fileOutputLine.split(" \\| ",-1);

            switch (textDescription[0]) {       // take in the first letter
            case "T":
                myTasks.add(new Todo(textDescription[2]));
                if (textDescription[1].equals("1")) {
                    myTasks.get(listCounter).markAsDone();
                }
                listCounter++;
                break;
            case "D":
                myTasks.add(new Deadline(textDescription[2], textDescription[3]));
                if (textDescription[1].equals("1")) {
                    myTasks.get(listCounter).markAsDone();
                }
                listCounter++;
                break;
            case "E":
                myTasks.add(new Event(textDescription[2], textDescription[3]));
                if (textDescription[1].equals("1")) {
                    myTasks.get(listCounter).markAsDone();
                }
                listCounter++;
                break;
            default:
                break;
            }


        }

    }

    private static String translateIntoText(int mode, int boolToNumber, String description) {

        switch (mode) {
        case 0:         // to do
            return "T | " + boolToNumber + " | " +  description;        // T | 1 | blabla
        case 1:         // deadline
            description = description.replace("/by","|");
            return "D | " + boolToNumber + " | " + description;         // D | 1 | blabla | 2222
        default:        // event
            description = description.replace("/at","|");
            return "E | " + boolToNumber + " | " + description;         // E | 1 | blablabla | 3333
        }
    }

    private static void appendFile(String pathName, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(pathName, true);
        fw.write("\n" + textToAdd);
        fw.close();
    }


}