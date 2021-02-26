package frontend;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MainMenuBar {
    public MainMenuBar() {
    }

    public MenuBar mainMenuBar() {
        Menu file = new Menu("File");
        MenuItem m0 = new MenuItem("Plot structure");
        m0.setOnAction(e->{
            PlotStructure ps=new PlotStructure();
            ps.setPlotStructureScene();

        });

        MenuItem m1 = new MenuItem("Generate structure");
        m1.setOnAction(e -> {
            GenerateStructureScene generateStr = new GenerateStructureScene();
            generateStr.setStructureGenerateScene();
        });
        MenuItem m2 = new MenuItem("Run simulation");
        m2.setOnAction(e->{
            RunSimulation RS=new RunSimulation();
            RS.setRunSimulationScene();
        });

        MenuItem m3 = new MenuItem("Run analysis");
        m3.setOnAction(e->{
            RunAnalysis Rs=new RunAnalysis();
            Rs.setRunSynthesisScene();
        });



        file.getItems().addAll(m0, m1, m2, m3);

        Menu help = new Menu("Help");

        MenuBar mainMenuBar = new MenuBar();
        mainMenuBar.getMenus().addAll(file, help);

        return mainMenuBar;
    }
}
