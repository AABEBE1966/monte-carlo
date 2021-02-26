package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.util.Callback;

import java.io.*;

public class GenerateStructureScene {
    private ComboBox layer1;
    private ComboBox layer2;
    private ComboBox layer3;
    private ComboBox layer4;
    private ComboBox layer5;
    private ComboBox layer6;
    private ComboBox layer7;
    private ComboBox layer8;
    private ComboBox layer9;
    private ComboBox layer10;
    private ComboBox layer11;
    private ComboBox layer12;

    GenerateStructureScene() {

    }

    public void setStructureGenerateScene() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        // chose acceptor:donor ratio
        layer1 = getRatioLists();
        layer1.setPromptText("L-1 acceptor:donor");
        layer2 = getRatioLists();
        layer2.setPromptText("L-2 acceptor:donor");
        layer3 = getRatioLists();
        layer3.setPromptText("L-3 acceptor:donor");
        layer4 = getRatioLists();
        layer4.setPromptText("L-4 acceptor:donor");
        layer5 = getRatioLists();
        layer5.setPromptText("L-5 acceptor:donor");
        layer6 = getRatioLists();
        layer6.setPromptText("L-6 acceptor:donor");
        layer7 = getRatioLists();
        layer7.setPromptText("L-7 acceptor:donor");
        layer8 = getRatioLists();
        layer8.setPromptText("L-8 acceptor:donor");
        layer9 = getRatioLists();
        layer9.setPromptText("L-9 acceptor:donor");
        layer10 = getRatioLists();
        layer10.setPromptText("L-10 acceptor:donor");
        layer11 = getRatioLists();
        layer11.setPromptText("L-11 acceptor:donor");
        layer12 = getRatioLists();
        layer12.setPromptText("L-12 acceptor:donor");

        Label chooseOrTypeRatio = new Label("Acceptor:Donor ratio");
        chooseOrTypeRatio.setId("choose_ratio_for_layers");
        chooseOrTypeRatio.setPadding(new Insets(10, 0, 3, 0));
        VBox layersVBox = new VBox();
        layersVBox.getChildren().addAll(chooseOrTypeRatio, layer1, layer2, layer3, layer4, layer5, layer6, layer7, layer8,
                layer9, layer10,
                layer11, layer12);
        layersVBox.setId("layersVBox");
        layersVBox.setSpacing(10);
        MainWindow.MAIN_LAYOUT.setLeft(layersVBox);


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
        run.setId("run_button");
        run.setOnAction(e-> {
            String python_script = "/Users/abebeamare/Desktop/Desktop/spring2021/ECE493/FinalProject" +
                    "/computation/GradedStructureGenerator.py";
            ProcessBuilder processBuilder=new ProcessBuilder("python3",python_script,layer1.getValue().toString()
                    ,layer2.getValue().toString(),layer3.getValue().toString(),layer4.getValue().toString(),
                    layer5.getValue().toString(),layer6.getValue().toString(),layer7.getValue().toString(),
                    layer8.getValue().toString(),layer9.getValue().toString(),layer10.getValue().toString(),
                    layer11.getValue().toString(),layer12.getValue().toString(), MainWindow.WORKING_DIRECTORY);
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

//            try {
//                System.out.println("Process started");
//                Process process = Runtime.getRuntime().exec(commandString);
//                // process.wait();
//                process.onExit();
//                System.out.println("Process completed");
//                //BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            } catch(IOException ex) {
//                System.out.println(ex.getMessage());
//            }
        });
        Button pause= new Button("pause");
        pause.setId("pause_button");
        Button load= new Button("Load");
        load.setId("load_button");
        Button stop=new Button("Stop");
        stop.setId("stop_button");

        ToolBar toolbar = new ToolBar(leftSpacer,run, new Separator(),
                pause, new Separator(), load, new Separator(),
                stop);

        // menu bar
        // show tool bar
        VBox menuToolBarVBox = new VBox();
        menuToolBarVBox.getChildren().addAll(MainWindow.MAIN_MENU_BAR, toolbar);
        MainWindow.MAIN_LAYOUT.setTop(menuToolBarVBox);


        // status bar
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
        hboxProgressBar.setId("hboxProgressBar");
        MainWindow.MAIN_LAYOUT.setBottom(hboxProgressBar);
        // progress bar ends here
        return;
    }

    private ComboBox getRatioLists() {
        String[] ratios = {"1:1", "1:2", "1:3", "1:3", "1:4", "2:1", "3:1", "4:1", "2:3", "3:2", "3:4", "4:3"};
        ComboBox ratioComboBox = new ComboBox(FXCollections
                .observableArrayList(ratios));
        ratioComboBox.setEditable(true);
        ratioComboBox.getSelectionModel().select(0);

        return ratioComboBox;
    }


}
