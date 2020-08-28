import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner UserInput = new Scanner(System.in);

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
        String command = UserInput.nextLine();

        while (!command.equals("bye")){
            System.out.println("____________________________________________________________\n" +
                    command +
                    "\n____________________________________________________________\n");
            command = UserInput.nextLine();
        }

        System.out.println("____________________________________________________________\n" +
                "Bye. Hope to see you again soon!\n" +
                "____________________________________________________________");
    }
}

