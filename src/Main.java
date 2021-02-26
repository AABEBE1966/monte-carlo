import frontend.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main  extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainWindow mainWindow = new MainWindow(primaryStage);
        mainWindow.run();
    }
}
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
///**
// * @web http://java-buddy.blogspot.com/
// */
//public class Main extends Application {
//
//
//    private final TableView<Record> tableView = new TableView<>();
//
//    private final ObservableList<Record> dataList
//            = FXCollections.observableArrayList();
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("java-buddy.blogspot.com");
//
//        Group root = new Group();
//
//        TableColumn columnF1 = new TableColumn("f1");
//        columnF1.setCellValueFactory(
//                new PropertyValueFactory<>("f1"));
//
//        TableColumn columnF2 = new TableColumn("f2");
//        columnF2.setCellValueFactory(
//                new PropertyValueFactory<>("f2"));
//
//        TableColumn columnF3 = new TableColumn("f3");
//        columnF3.setCellValueFactory(
//                new PropertyValueFactory<>("f3"));
//
//        TableColumn columnF4 = new TableColumn("f4");
//        columnF4.setCellValueFactory(
//                new PropertyValueFactory<>("f4"));
//
//        tableView.setItems(dataList);
//        tableView.getColumns().addAll(
//                columnF1, columnF2, columnF3, columnF4);
//
//        VBox vBox = new VBox();
//        vBox.setSpacing(10);
//        vBox.getChildren().add(tableView);
//
//        root.getChildren().add(vBox);
//
//        primaryStage.setScene(new Scene(root, 700, 250));
//        primaryStage.show();
//
//        readCSV();
//    }
//
//    private void readCSV() {
//
//        String CsvFile = "/Users/abebeamare/Desktop/Desktop/spring2021/ECE493/FinalProject/src/data/import.csv";
//        String FieldDelimiter = ",";
//
//        BufferedReader br;
//
//        try {
//            br = new BufferedReader(new FileReader(CsvFile));
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] fields = line.split(FieldDelimiter, -1);
//
//                Record record = new Record(fields[0], fields[1], fields[2],
//                        fields[3]);
//                dataList.add(record);
//
//            }
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Main.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}
