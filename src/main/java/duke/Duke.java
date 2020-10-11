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
        while (true) {
            try {
                String command = ui.inputCommand();
                Parser.parse(command,tasks);
            } catch (UnknownCommandException e) {
                ui.showUnknownCommand();
            } catch (InsufficientDescriptionException e) {
                ui.showInvalidDescription();
            } catch (InvalidTaskNumberException invalidTaskNumber) {
                ui.showInvalidTaskNumber();
            } catch (EmptyListException e) {
                ui.showEmptyList();
            } catch (IOException e) {
                ui.showIO();
            } catch (InvalidDateTimeException e) {
                ui.showInvalidDateTime();
            } finally {
                System.out.println("____________________________________________________________\n");
            }
        }
    }

    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }

}



