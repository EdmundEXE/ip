
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner UserInput = new Scanner(System.in);
        String[] myList = new String[100];
        int ListCounter = 0;

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

        while (!command.equals("bye")){     // prog doesnt end unless "bye"
            if (!command.equals("list")) {
                AddtoList(command,myList,ListCounter);
                ListCounter++;              // one more item was added to list
            }
            else {
                ShowList(myList,ListCounter);
            }
            command = UserInput.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!\n" +
                "____________________________________________________________");

    }

    public static void AddtoList(String command, String[] myList, int ListCounter){         // function to add to list
        myList[ListCounter] = command;
        System.out.println("____________________________________________________________\n" +
                "added: " + command +
                "\n____________________________________________________________\n");
    }

    public static void ShowList(String[] myList, int ListCounter){
        System.out.println("____________________________________________________________\n");
        for (int i=0; i<ListCounter; i++){
            System.out.println((i+1) + ". " + myList[i]);
        }
        System.out.println("____________________________________________________________\n");
    }

}

