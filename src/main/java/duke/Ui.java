package duke;


import java.util.Scanner;

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

    public String inputCommand() {
        return s.nextLine();
    }
}
