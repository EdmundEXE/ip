package duke;


import java.util.Scanner;

/**
 * A class that prints out messages and errors by Duke
 * and scans for inputs
 */
public class Ui {
    private static Scanner s = new Scanner(System.in);

    public void printStartMessage() {
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

    public void showLoadingError() {
        System.out.println("____________________________________________________________\n" +
                " Cannot load task.txt... Creating one...");
    }

    public void showUnknownCommand() {
        System.out.println("This command was not programmed in me...=O\n");
    }

    public void showInvalidDescription() {
        System.out.println("Hmmm....You didn't add any details...\n");
    }

    public void showInvalidTaskNumber() {
        System.out.println("Invalid Task Number!!!\n");
    }

    public void showEmptyList() {
        System.out.println("Empty list. Add something!\n");
    }

    public void showIO() {
        System.out.println("Unable to write to file!\n");
    }

    public void showInvalidDateTime() {
        System.out.println("Please follow this date time format:\n" +
                "yyyy-mm-dd HHmm (24H)\n");
    }

    public String inputCommand() {
        return s.nextLine();
    }
}
