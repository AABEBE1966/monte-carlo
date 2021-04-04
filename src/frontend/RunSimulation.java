package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.AllSimulation;
import model.MonteCarloMain;

import javax.swing.*;
import java.io.*;

public class RunSimulation {
    public RunSimulation() {

    }

    public void setRunSimulationScene(){

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

        Button run= new Button("Run");
        run.setOnAction(e->{
            String folderPath=chooseStr();
            if(folderPath!=null) {

                AllSimulation all=new AllSimulation(folderPath);
                all.runAllSimulations();
                System.out.println("Getting started with Monte Carlo simulation");
            }
        });

        run.setId("run_simulation_button");
        Button pause= new Button("pause");
        pause.setId("pause_simulation_button");
        Button load= new Button("Load");
        load.setId("load_simulation_button");
        Button stop=new Button("Stop");
        stop.setId("stop_simulation_button");

        ToolBar toolbar = new ToolBar(leftSpacer,run, new Separator(),
                pause, new Separator(), load, new Separator(),
                stop);

        // menu bar
        // show tool bar
        VBox menuToolBarVBox = new VBox();
        menuToolBarVBox.getChildren().addAll(MainWindow.MAIN_MENU_BAR, toolbar);
        MainWindow.MAIN_LAYOUT.setTop(menuToolBarVBox);
        MainWindow.MAIN_LAYOUT.setLeft(null);

        // progress bar

        final Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(50);

        final ProgressBar pb = new ProgressBar(0);
        final ProgressIndicator pi = new ProgressIndicator(0);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                pb.setProgress(new_val.doubleValue()/50);
                pi.setProgress(new_val.doubleValue()/50);
            }
        });

        final HBox hboxProgressBar = new HBox();
        hboxProgressBar.setSpacing(5);
        hboxProgressBar.setAlignment(Pos.CENTER);
        hboxProgressBar.getChildren().addAll(slider, pb, pi);
        hboxProgressBar.setId("hboxProgressBar_simulation");
        MainWindow.MAIN_LAYOUT.setBottom(hboxProgressBar);

    }


    private String chooseStr() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        //Show open file dialog
        try {
            File file = directoryChooser.showDialog(MainWindow.MAIN_WINDOW);
            return  file.getAbsolutePath();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
