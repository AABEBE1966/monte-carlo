package frontend;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class DirectorySelector {
    private static File selectedDirectory;
    private static String directory = "";

    public static void showDirectorySelector(StringProperty directoryProperty) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);// block user interaction from other windows
        window.setTitle("Choose working directory");
        window.setWidth(400);
        window.setHeight(400);
        window.initStyle(StageStyle.TRANSPARENT); // remove close and minimize buttons

        Button runButton= new Button("Choose working directory");
        runButton.setId("directory_chooser_button");
        runButton.setOnAction(e->
        {
            selectedDirectory = directoryChooser.showDialog(window);
            directory = selectedDirectory.getAbsolutePath();
            directoryProperty.set(directory);
            window.close();
        });

        VBox layout= new VBox();
        layout.getChildren().add(runButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        layout.setId("directory_chooser");
        window.setScene(scene);
        window.showAndWait();

    }

    public String getWorkingDirectory() {
        return selectedDirectory.getAbsolutePath();
    }

}




