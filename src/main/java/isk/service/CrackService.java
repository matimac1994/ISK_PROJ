package isk.service;

import isk.model.Network;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public interface CrackService {
    String listOfWebInterfaces() throws IOException, InterruptedException;
    String scanNetworks() throws IOException, InterruptedException;
    void setCardToMonitorMode(String interfaceName) throws IOException, InterruptedException;
    List<Network> showListOfNetworks() throws IOException, InterruptedException;
    void storeToFileByBssid(Network network, Label errorMessage) throws IOException, InterruptedException;
    void runAirCrack(Network network, Label resultText) throws IOException, InterruptedException;
    void stopMonitorMode() throws InterruptedException, IOException;
}
