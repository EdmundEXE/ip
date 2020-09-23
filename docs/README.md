# Duke User Guide
Duke is a Command Line Interface (CLI) which is optimised to help the user manage his/her tasks.
Type of tasks includes "todo", "deadline", "event". Duke also allows adding, deleting, mark as done
listing all saved tasks and finding key search terms.


## Features 

###  Adding of tasks
The types of tasks available are:
- todo
- deadline
- event

Adds the corresponding tasks to the list.

### Deleting tasks

Deletes the task tagged to a number on the list.

### Marking a task as done

Tag the task tagged to a number as done. A tick is shown for tasks that are done and a cross for those
that are not.

### View the list of tasks.

Shows a list of all the tasks in the list.

### Search for a keyword in the list

Shows a list of all tasks that are relevant to the search term.

### Close Duke

Closes the program.

## Usage

### "todo"

Format: todo *title*


![DukeToDo](https://user-images.githubusercontent.com/60436412/94020847-f4864600-fde5-11ea-9d7a-4c5bd948213a.PNG)

### "deadline"

Format: 
- deadline *title* /by *yyyy-mm-dd* *HHmm*
- deadline *title* /by *yyyy-mm-dd*

Expected outcome:![DukeToDo](https://user-images.githubusercontent.com/60436412/94021352-7fffd700-fde6-11ea-98c2-5581491becab.PNG)

### "event"

Format: 
- event *title* /by *yyyy-mm-dd* *HHmm*
- event *title* /by *yyyy-mm-dd*

Expected outcome:![DukeEvent](https://user-images.githubusercontent.com/60436412/94021803-00263c80-fde7-11ea-802e-0524c811fe75.PNG)

### "delete"

Format: delete *taskNumber*

Expected outcome:


![DukeDelete](https://user-images.githubusercontent.com/60436412/94022007-39f74300-fde7-11ea-9227-74d273251365.PNG)

### "list"

Format: list

Expected outcome:![DukeList](https://user-images.githubusercontent.com/60436412/94022276-7fb40b80-fde7-11ea-9541-c2a29248141d.PNG)

### "find"

Format: find *keyword*

Expected outcome:![DukeFind](https://user-images.githubusercontent.com/60436412/94023237-9149e300-fde8-11ea-8216-b80fb4f752b2.PNG)

### "bye"

Format: bye

Expected outcome:![DukeBye](https://user-images.githubusercontent.com/60436412/94023444-ca825300-fde8-11ea-9793-6974f1270fcc.PNG)
