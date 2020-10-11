package duke;

import duke.tasks.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import static duke.Duke.listCounter;
import static duke.Parser.translateIntoText;

/**
 * A class that stores tasks into a text file and loads the tasks from a text file
 */
public class Storage {

    private String filePath;

    public Storage(String filePath) {

        this.filePath = filePath;
    }

    /**
     * Returns the list of tasks loaded from the text file
     *
     * @return list of tasks converted from the text file
     */
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

                System.out.println("Here are the tasks loaded to your list:\n");

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
                s.close();
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

    /**
     * Replaces the original text file with new text file that has a task deleted
     *
     * @param tasks The current list of tasks.
     */
    public static void delete(TaskList tasks) throws IOException {
        File temp = new File("temp.txt");
        temp.createNewFile();
        File original = new File("tasks.txt");
        String s2;
        int mode;

        for (int i = 0; i < listCounter; i++) {
            String s1 = String.valueOf(tasks.get(i)).substring(1, 2);
            int boolToNumber = tasks.get(i).getIsDone() ? 1 : 0;

            switch (s1) {
            case "T":       // [T][?] blabla
                s2 = tasks.get(i).toString().substring(6);
                mode = 0;
                break;
            case "D":        // E or D
                s2 = tasks.get(i).toString().substring(6).replace("by:", "/by");
                mode = 1;
                break;
            default:
                s2 = tasks.get(i).toString().substring(6).replace("at:", "/at");
                mode = 2;
                break;
            }

            appendFile("temp.txt", translateIntoText(mode,boolToNumber,s2));
        }
        original.delete();
        temp.renameTo(original);

    }

}
