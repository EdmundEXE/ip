package duke;

import duke.tasks.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import static duke.Duke.listCounter;


public class Storage {
    private String pathName;

    public Storage(String pathName) {
        this.pathName = pathName;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File f = new File("tasks.txt");

            if (!f.exists()) {
                System.out.println("____________________________________________________________\n" +
                        "Cannot find file..... Creating one...\n" +
                        "____________________________________________________________\n");
                f.createNewFile();
            } else {
                Scanner s = new Scanner(f);

                System.out.println("Here are the tasks on your list:\n");

                while (s.hasNextLine()) {
                    String fileOutputLine = s.nextLine();
                    System.out.println(fileOutputLine);
                    String[] textDescription = fileOutputLine.split(" \\| ", -1);

                    switch (textDescription[0]) {       // take in the first letter
                    case "T":
                        tasks.add(new Todo(textDescription[2]));
                        if (textDescription[1].equals("1")) {
                            tasks.get(listCounter).markAsDone();
                        }
                        listCounter++;
                        break;
                    case "D":
                        tasks.add(new Deadline(textDescription[2], textDescription[3]));
                        if (textDescription[1].equals("1")) {
                            tasks.get(listCounter).markAsDone();
                        }
                        listCounter++;
                        break;
                    case "E":
                        tasks.add(new Event(textDescription[2], textDescription[3]));
                        if (textDescription[1].equals("1")) {
                            tasks.get(listCounter).markAsDone();
                        }
                        listCounter++;
                        break;
                    default:
                        break;
                    }
                }

                System.out.println("____________________________________________________________\n");
            }
        }  catch (IOException e) {
            System.out.println("Cannot create file!!!");
        }
        return tasks;
    }


    public static void appendFile(String pathName, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(pathName, true);
        fw.write("\n" + textToAdd);
        fw.close();
    }



}
