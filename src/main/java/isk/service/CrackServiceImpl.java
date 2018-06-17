package isk.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import isk.model.Network;
import isk.util.CommandUtil;
import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CrackServiceImpl implements CrackService {

    private Process networkListProcess;

    public CrackServiceImpl() {
    }

    @Override
    public String listOfWebInterfaces() throws IOException, InterruptedException {
        List<String> outputLines = new ArrayList<>();
        Process p1 = Runtime.getRuntime().exec(CommandUtil.getNetworkInterfaceCommand());
        BufferedReader reader2 =
                new BufferedReader(new InputStreamReader(p1.getInputStream()));

        String line3 = "";
        while((line3 = reader2.readLine()) != null) {
            System.out.print(line3 + "\n");
            outputLines.add(line3);
        }

        p1.waitFor();

        return outputLines.get(0);
    }

    @Override
    public String scanNetworks() throws IOException, InterruptedException {
        String interfaceName = listOfWebInterfaces();
        networkListProcess = Runtime.getRuntime().exec(CommandUtil.getScanAndSaveToFileCommand(interfaceName, "isk"));
        Runnable runnable = () -> {
            try {
                networkListProcess.waitFor(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        return "cos";
    }

    @Override
    public void setCardToMonitorMode(String interfaceName) throws IOException, InterruptedException {
        Process p1 = Runtime.getRuntime().exec(CommandUtil.getSetToMonitorModeNetworkCardCommand(interfaceName, null));
        p1.waitFor();
    }

    public void setCardToMonitorModeWithChannel(String interfaceName, String channel) throws InterruptedException, IOException {
        Process p1 = Runtime.getRuntime().exec(CommandUtil.getSetToMonitorModeNetworkCardCommand(interfaceName, channel));
        p1.waitFor();
    }

    @Override
    public void stopMonitorMode() throws InterruptedException, IOException {
        String interfaceName = listOfWebInterfaces().trim();
        Process p1 = Runtime.getRuntime().exec(CommandUtil.getStopMonitorMode(interfaceName));
        p1.waitFor();
    }

    @Override
    public List<Network> showListOfNetworks() throws IOException, InterruptedException {
        if (networkListProcess != null && networkListProcess.isAlive()){
            networkListProcess.destroy();
        }
        List<File> files = getListOfFilesByName("isk", ".csv");
        files = sortFilesByNumber(files);
        List<Network> networks = new ArrayList<>();
        if (files.size() >= 1)
            networks = readDataFromFile(files.get(files.size()-1));

        return networks;
    }

    private List<File> sortFilesByNumber(List<File> files){
        if(files != null)
            files.sort((file, t1) -> {
                String fileName1 = file.getName().replaceAll("\\D+", "");
                String fileName2 = t1.getName().replaceAll("\\D+", "");
                return Integer.compare(Integer.parseInt(fileName1), Integer.parseInt(fileName2));
            });

        return files;
    }

    @Override
    public void storeToFileByBssid(Network network, Label errorMessage) throws IOException, InterruptedException {
        stopMonitorMode();
        setCardToMonitorModeWithChannel(listOfWebInterfaces().trim(), network.getChannel().trim());

        Process p1 = Runtime
                .getRuntime()
                .exec(CommandUtil.getStoreToFileByBSSID(
                        network.getChannel().trim(),
                        "cabFile",
                        network.getBSSID().trim(),
                        listOfWebInterfaces()));

        runAssociation(network, errorMessage);
    }

    private void runAssociation(Network network, Label errorMessage) throws IOException, InterruptedException {
        Process p1 = Runtime
                .getRuntime()
                .exec(CommandUtil.getRunAssociaction(
                        network.getBSSID(),
                        listOfWebInterfaces()));

        BufferedReader reader2 =
                new BufferedReader(new InputStreamReader(p1.getInputStream()));

        String line3 = "";
        StringBuilder text = new StringBuilder();
        while((line3 = reader2.readLine()) != null) {
            if (line3.trim().length() > 5){
                text.append(line3);
                text.append("\n");
                errorMessage.setText(text.toString());
            } else {
                text = new StringBuilder();
            }

            System.out.print(line3 + "\n");
        }

        p1.waitFor();
        startAttack(network);
    }

    private void startAttack(Network network) throws InterruptedException, IOException {
        AiroDumpThread airoDumpThread = new AiroDumpThread(listOfWebInterfaces(), network);
        airoDumpThread.startAttack();
    }

    @Override
    public void runAirCrack(Network network, Label resultText) throws IOException, InterruptedException {
        List<File> files = getListOfFilesByName("cabFile", ".cap");
        files = sortFilesByNumber(files);
        if (files != null && files.size() >= 1){
            AirCrackThread airCrackThread = new AirCrackThread(network);
            airCrackThread.setCapPath(files.get(files.size()-1).getAbsolutePath());

            resultText.textProperty().bind(airCrackThread.messageProperty());
            new Thread(airCrackThread).start();
        }
    }

    private List<Network> readDataFromFile(File file) throws IOException, InterruptedException {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        ObjectReader oReader = csvMapper.reader(String[].class);

        Integer properlyLineLength = 0;
        List<Network> networks = new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            MappingIterator<String[]> mi = oReader.readValues(reader);
            while (mi.hasNext()) {
                String[] line = mi.next();
                if (line.length > 2 && line[0].equals("BSSID")){
                    properlyLineLength = line.length;
                }

                if (line.length == properlyLineLength
                        && properlyLineLength != 0
                        && !line[0].equals("BSSID") && line[5].trim().equals("WEP")){
                    networks.add(new Network(
                            line[0],
                            line[3],
                            line[5],
                            line[6],
                            line[8],
                            line[9],
                            line[13]
                    ));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        networks.forEach(n -> System.out.println(n.getESSID()));

        return networks;
//        storeToFileByBssid(networks.get(0));
    }

    private File[] getListOfFiles(){
        File dir = new File(".");
        File[] fileList = dir.listFiles();
        return fileList;
    }

    private List<File> getListOfFilesByName(String name, String type){
        List<File> files = new ArrayList<>();
        File[] filesArray = getListOfFiles();
        if (filesArray != null){
            for (File f : filesArray){
                if (f.isFile()){
                    if (f.getName().contains(name) && f.getName().contains(type)){
                        files.add(f);
                    }
                }
            }
        }
        return files;
    }
}
