package frontend;//package frontend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVReader {

    private final TableView<Record> tableView = new TableView<>();

    private final ObservableList<Record> dataList
            = FXCollections.observableArrayList();

    public CSVReader() {

    }

    private VBox defineTableViewBox() {

        TableColumn columnF1 = new TableColumn("X");
        columnF1.setCellValueFactory(
                new PropertyValueFactory<>("X"));

        TableColumn columnF2 = new TableColumn("Y");
        columnF2.setCellValueFactory(
                new PropertyValueFactory<>("Y"));

        TableColumn columnF3 = new TableColumn("Z");
        columnF3.setCellValueFactory(
                new PropertyValueFactory<>("Z"));

        TableColumn columnF4 = new TableColumn("Carrier");
        columnF4.setCellValueFactory(
                new PropertyValueFactory<>("Carrier"));

        tableView.setItems(dataList);

        tableView.getColumns().addAll(
                columnF1, columnF2, columnF3, columnF4);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().add(tableView);
        return vBox;


    }



    private void readCSV(String CsvFile) {

        String FieldDelimiter = ",";

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(CsvFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(FieldDelimiter, -1);

                Record record = new Record(fields[0], fields[1], fields[2],
                        fields[3]);
                dataList.add(record);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    public  void showSelectedCSVFile(String pathName) {
        String fileName=pathName.split("/")[pathName.split("/").length-1];
        Stage window=new Stage();
        window.initModality(Modality.NONE);// block user interaction from other windows
        window.initOwner(MainWindow.MAIN_WINDOW);
        window.setTitle(fileName);
        window.setWidth(400);
        window.setHeight(400);
        readCSV(pathName);
        VBox layout= defineTableViewBox();
        layout.setAlignment(Pos.CENTER_RIGHT);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();


    }

}
