package isk.service;

import isk.model.Network;
import isk.util.CommandUtil;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AirCrackThread extends Task<String> {

    private final Network network;
    private ProcessBuilder pb;
    private Process process;
    private Matcher m;
    private InputStream is;
    private String status = "N/A";
    private String currentPassphrase = "N/A";
    private String keyFound = "N/A";
    private String capPath;



    public AirCrackThread(Network network) {
        super();
        this.network = network;
    }

    @Override
    protected String call() throws Exception {
        this.pb = new ProcessBuilder(CommandUtil.getAircrack( this.capPath, network.getBSSID()));
        this.process = this.pb.start();
        this.is = this.process.getInputStream();

        try {
            this.processInputSream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.process.waitFor();

        return status;
    }

    public void processInputSream() throws IOException {
        InputStreamReader isr = new InputStreamReader(this.is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            this.processText(line);
        }
    }

    private void processText(String text) {
        String regex = "(?i)\\QReading packets, please wait...\\E";
        Matcher m = Pattern.compile(regex).matcher(text);

        if (text.contains("KEY FOUND!")){
            updateMessage(text);
            System.out.println(text);
        }

        if (m.find()) {
            updateMessage("PROCESSING");
        }


        regex = "(?i)Current\\spassphrase:\\s(?<passphrase>[\\w]+)";
        m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
            this.currentPassphrase = m.group("passphrase");
            System.out.println(m.group("passphrase"));
        }

        regex = "(?i)KEY FOUND!\\s\\Q[\\E\\s(?<key>[\\w]+)\\s\\Q]\\E";
        m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
            this.keyFound = m.group("key");
            System.out.println(m.group("key"));
            this.status = "KEY_FOUND";
        }

        regex = "(?i)\\bPassphrase not in dictionary\\b";
        m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
            this.status = "KEY_NOT_FOUND";
            System.out.println("KEY_NOT_FOUND");
        }

        regex = "(?i)\\bNo networks found\\Q,\\E exiting\\b";
        m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
            this.status = "ERROR";
            System.out.println("ERROR");
        }
    }

    public ProcessBuilder getPb() {
        return pb;
    }

    public void setPb(ProcessBuilder pb) {
        this.pb = pb;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Matcher getM() {
        return m;
    }

    public void setM(Matcher m) {
        this.m = m;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentPassphrase() {
        return currentPassphrase;
    }

    public void setCurrentPassphrase(String currentPassphrase) {
        this.currentPassphrase = currentPassphrase;
    }

    public String getKeyFound() {
        return keyFound;
    }

    public void setKeyFound(String keyFound) {
        this.keyFound = keyFound;
    }

    public String getCapPath() {
        return capPath;
    }

    public void setCapPath(String capPath) {
        this.capPath = capPath;
    }
}
