# monte-carlo
please read this document to understand how the code works and the function of each modules in the file tree.

### How does the code works
- We use process builder to call python code base that does the computation in java. Please [read](https://www.baeldung.com/java-working-with-python) how we can call python function from java. 

### How to set up the system
- Install [Intellij](https://www.jetbrains.com/help/idea/installation-guide.html) IDEA.
- Once you install Intellij, go to ```File``` -> ```Project Structure``` -> ```Modules``` and add Python 3.9 and java 13.0.1. You can find more information about how to set up your modules here. You need to have two modules, one for python and another for java. [Check this link and follow the instruction under ```Add a library to module dependencies```﻿
](https://www.jetbrains.com/help/idea/library.html#define-a-module-library)
- Once you added both Python and Java, you should have module structures as follow. 
<img width="947" alt="Screen Shot 2021-09-26 at 8 03 17 PM" src="https://user-images.githubusercontent.com/42746765/134839522-2a4ef0fa-71e2-4325-bde4-806dcc26b5fb.png">

- Add [javaFX](https://javabook.bloomu.edu/setup.html) to intelliji. We use JavaFX to write the UI, pure java to do the simulation and Python to do the computation needed in each of the process in the simulation. 

- Once you have all modules and libraries installed, open Main.java and click ```Run```.


## Tips

- Please delete any of the .class files as they are generated when I run my codes
- Please go over my thesis to understand how the frontend, and backend works. 
- I highly recommend to keep the backend code which is the model module and use react for fronend. By making the software a web app, you can avoid waiting for four hours to finish running one simulationa and analysis. I suggest you can build the backend using the model and compuation modules and build a the UI in react. You can then deploy it on heroku. The advantage of making the software web based, you do not have to install all this packages whenever you change machines. In addtion, you can start a process on the web and turn of your local computer since the server will still be running. You can read more about [this](https://medium.com/bb-tutorials-and-thoughts/how-to-develop-and-build-react-app-with-java-backend-c1e6c5c93ae)

## How to contact me

- Please feel free to send me an email to aabebe1966@gmail.com
- give me a call on weekends to 9197717649
