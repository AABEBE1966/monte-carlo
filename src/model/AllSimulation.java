package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AllSimulation {
    private String folderPath;
    public AllSimulation(String folderPath) {
        this.folderPath=folderPath;

    }

    public void runAllSimulations() {
        MonteCarloMain monteCarloOne=new MonteCarloMain();
        List<String> filenames=getAllCsvFiles();
        for(int i=0; i<filenames.size(); i++) {
            String temp=filenames.get(i);
            new Thread(()-> {
                runSimulations(temp);
            }).start();
            System.out.println("Thread "+(i)+" starts");
        }
    }

    private List<String> getAllCsvFiles() {
        final File folder = new File(this.folderPath);

        List<String> filenames = new LinkedList<String>();

        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.getName().contains(".csv")) {
                filenames.add(fileEntry.getAbsolutePath());
            }
        }
        return filenames;
    }


    private void runSimulations(String filePath) {
        MonteCarloDriver simulation = null;
        System.out.println(filePath);
        try {
            simulation = new MonteCarloDriver(filePath);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            simulation.runSimulation();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Getting started with Monte Carlo simulation");
    }

}
