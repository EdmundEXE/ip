package duke;

import duke.exceptions.*;

import java.io.IOException;


public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    public static int listCounter = 0;



    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.printStartMessage();
        while(true) {
            try {
                String command = ui.inputCommand();
                Parser.parse(command,tasks);
            } catch (UnknownCommandException e) {
                System.out.println("This command was not programmed in me...=O\n");
            } catch (InsufficientDescriptionException e) {
                System.out.println("Hmmm....You didn't add any details...\n");
            } catch (InvalidTaskNumber invalidTaskNumber) {
                System.out.println("Invalid Task Number!!!\n");
            } catch (EmptyListException e) {
                System.out.println("Empty list. Add something!\n");
            } catch (IOException e) {
                System.out.println("Unable to write to file!\n");
            } catch (InvalidDateTimeException e) {
                System.out.println("Please follow this date time format:\n" +
                        "yyyy-mm-dd HHmm (24H)\n");
            } finally {
                System.out.println("____________________________________________________________\n");
            }
        }
    }

    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }

}



