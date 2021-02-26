package frontend;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import jdk.jfr.StackTrace;

import java.io.*;
import java.util.logging.Logger;

public class PlotStructure {
   public PlotStructure() {
    }

    public void setPlotStructureScene() {

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

        Button plot= new Button("Plot");
        plot.setOnAction(e->{
           String str_file=chooseStr();
           if(str_file!=null) {
               String python_script = "/Users/abebeamare/Desktop/Desktop/spring2021/ECE493/FinalProject" +
                       "/computation/PlotGraph.py";
               ProcessBuilder processBuilder=new ProcessBuilder("python3",python_script,str_file);
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
        plot.setId("Plot_button");
        Button pause= new Button("Pause");
        pause.setId("Pause_plot_button");
        Button stop=new Button("Cancel");
        stop.setId("Cancel_Button");

        ToolBar toolbar = new ToolBar(leftSpacer,plot, new Separator(),
                pause, new Separator(), stop, new Separator());

        // menu bar
        // show tool bar
        VBox menuToolBarVBox = new VBox();
        menuToolBarVBox.getChildren().addAll(MainWindow.MAIN_MENU_BAR, toolbar);
        MainWindow.MAIN_LAYOUT.setTop(menuToolBarVBox);
        MainWindow.MAIN_LAYOUT.setLeft(null);
        MainWindow.MAIN_LAYOUT.setBottom(null);

//        Image image1 = new Image("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.dualdove.com%2Fphysicists-notice-a-link-between-quantum-criticality-and-entanglement%2F6602%2F&psig=AOvVaw3DtOq-uS5cVU-eeSFPzrbC&ust=1612849282562000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIiSpILK2e4CFQAAAAAdAAAAABAF");
//        Image image2 = new Image("https://cdn.sstatic.net/Sites/stackoverflow/company/img/logos/so/so-logo.png?v=9c558ec15d8a");
//
//        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
//
//        Background background2 = new Background(new BackgroundImage(image2,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                bSize));
//
//        MainWindow.MAIN_LAYOUT.setCenter(new Background(new BackgroundImage(image1,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                bSize)));

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
