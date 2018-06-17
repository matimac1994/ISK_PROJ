package isk.controller;

import isk.model.Network;
import isk.model.NetworkTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WepPasswordController extends Controller {

    @FXML
    private TableView<NetworkTable> tableView;

    @FXML
    private Label resultText;

    @FXML
    private Label errorMessage;

    private List<Network> networks = new ArrayList<>();
    private List<NetworkTable> networkTableList = new ArrayList<>();


    public void backToMain(ActionEvent actionEvent) {
        backToMainScene();
    }

    public void setCardToMonitorMode(ActionEvent actionEvent) throws IOException, InterruptedException {
        String interfaceName = crackService.listOfWebInterfaces();
        crackService.setCardToMonitorMode(interfaceName);
    }

    public void scan(ActionEvent actionEvent) throws IOException, InterruptedException {
        crackService.scanNetworks();
    }

    public void showListOfNetworks(ActionEvent actionEvent) throws IOException, InterruptedException {
        List<Network> networkList = crackService.showListOfNetworks();
        networks.clear();
        networks.addAll(networkList);
        networks = networkList;
        ObservableList<NetworkTable> networksTable = tableView.getItems();
        networksTable.clear();
        networksTable.addAll(networkList.stream()
                .map(p -> new NetworkTable(p.getBSSID(), p.getChannel(), p.getPrivacy(), p.getCipher(), p.getPower(), p.getBeacons(), p.getESSID()))
                .collect(Collectors.toList()));

        tableView.setOnMouseClicked(event -> {
            networkTableList.clear();
            networkTableList.add(tableView.getSelectionModel().getSelectedItem());
        });
    }

    public void startAttack(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (networkTableList.size() > 0)
            crackService.storeToFileByBssid(new Network(networkTableList.get(0)), errorMessage);
        else
            errorMessage.setText("Wybierz sieć");

    }

    public void tryCrack(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (networkTableList.size() > 0)
            crackService.runAirCrack(new Network(networkTableList.get(0)),   resultText);
        else
            errorMessage.setText("Wybierz sieć");
    }

    public void turnOffMonitorMode(ActionEvent actionEvent) throws IOException, InterruptedException {
        crackService.stopMonitorMode();
    }
}
