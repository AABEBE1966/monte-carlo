package frontend;

import com.sun.tools.javac.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Separator;
import javafx.util.Callback;
import javafx.scene.layout.HBox;


import java.io.File;

public class MainWindow {
    public static Stage MAIN_WINDOW;
    public static BorderPane MAIN_LAYOUT;
    public static Scene MAIN_SCENE;
    public static MenuBar MAIN_MENU_BAR;
    public static String WORKING_DIRECTORY = "";
    private StringProperty directoryProperty = new SimpleStringProperty("");


    public MainWindow(Stage window) {
        MAIN_WINDOW = window;
        MAIN_LAYOUT = new BorderPane();
        MAIN_SCENE = new Scene(MAIN_LAYOUT, 1000, 800);
        MAIN_MENU_BAR = (new MainMenuBar()).mainMenuBar();
    }

    public void run() {
        MAIN_WINDOW.setTitle("Monte Carlo Simulation");

        // Add change listener
        DirectorySelector directorySelector = new DirectorySelector();
        directoryProperty.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newDirectory) {
                System.out.println("changed " + oldValue + "->" + newDirectory);
                WORKING_DIRECTORY = newDirectory;
                TreeView<File> fileView = getFileTreeView();
                MAIN_LAYOUT.setRight(fileView);

            }
        });

        // TreeView<File> fileView = getFileTreeView();

        MAIN_LAYOUT.setPadding(new Insets(10, 10, 10, 10));
        MAIN_LAYOUT.setTop(MAIN_MENU_BAR);
        // MAIN_LAYOUT.setRight(fileView);
        MAIN_SCENE.getStylesheets().add("style.css");
        MAIN_WINDOW.setScene(MAIN_SCENE);
        MAIN_WINDOW.show();
        DirectorySelector.showDirectorySelector(directoryProperty);

    }

    private TreeView<File> getFileTreeView() {
        // TODO the folder path should be the working directory that the user selects at the begining of the program

        TreeView<File> fileView = new TreeView<File>(
                new SimpleFileTreeItem(new File(WORKING_DIRECTORY)));
        fileView.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {

            public TreeCell<File> call(TreeView<File> tv) {
                TreeCell newCellWithShortName= new TreeCell<File>() {

                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);

                        setText((empty || item == null) ? "" : item.getName());
                    }

                };
                newCellWithShortName.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent click) {

                        if (click.getClickCount() == 2 && newCellWithShortName.getText().contains(".csv")) {
                            CSVReader reader=new CSVReader();
                            reader.showSelectedCSVFile(tv.getSelectionModel().getSelectedItem().getValue().getAbsolutePath());
                        }
                    }
                });
                return newCellWithShortName;
            }
        });
        return fileView;
    }

}
