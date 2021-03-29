package frontend;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;

public class OpticalGenerationMicroscale {

        public OpticalGenerationMicroscale() {

        }

    public void setOpticalGenerationMicroScaleScene() {

        // spacers

        final Pane leftSpacer = new Pane();
        HBox.setHgrow(
                leftSpacer,
                Priority.SOMETIMES
        );

        final Pane rightSpacer = new Pane();
        HBox.setHgrow(
                rightSpacer,
                Priority.SOMETIMES
        );

        Button plot= new Button("Run");
        plot.setOnAction(e->{
            String str_file=chooseStr();
            if(str_file!=null) {
                String python_script = System.getProperty("user.dir") +
                        "/computation/OpticalGenerationMicroscale.py";
                ProcessBuilder processBuilder=new ProcessBuilder("python3",python_script,str_file,MainWindow.WORKING_DIRECTORY);
                System.out.println("starting processBuilder");
                processBuilder.redirectErrorStream(true);
                try {
                    Process proc=processBuilder.start();
                    Reader reader = new InputStreamReader(proc.getInputStream());
                    BufferedReader bf = new BufferedReader(reader);
                    String s;
                    while ((s = bf.readLine()) != null) {
                        System.out.println(s);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }


        });
        plot.setId("Run_Microscale");
        Button pause= new Button("Pause");
        pause.setId("Pause_Run_Microscale_button");
        Button stop=new Button("Cancel");
        stop.setId("Cancel_Run_Microscale_Button");

        ToolBar toolbar = new ToolBar(leftSpacer,plot, new Separator(),
                pause, new Separator(), stop, new Separator());

        // menu bar
        // show tool bar
        VBox menuToolBarVBox = new VBox();
        menuToolBarVBox.getChildren().addAll(MainWindow.MAIN_MENU_BAR, toolbar);
        MainWindow.MAIN_LAYOUT.setTop(menuToolBarVBox);
        MainWindow.MAIN_LAYOUT.setLeft(null);
        MainWindow.MAIN_LAYOUT.setBottom(null);

    }

    private String chooseStr() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new
                FileChooser.ExtensionFilter("ALL files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show open file dialog
        try {
            File file = fileChooser.showOpenDialog(MainWindow.MAIN_WINDOW);

            String pathsInfo = "";
            //pathsInfo += "getPath(): " + file.getPath() + "\n";
            pathsInfo +=file.getAbsolutePath();

            return  pathsInfo;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
