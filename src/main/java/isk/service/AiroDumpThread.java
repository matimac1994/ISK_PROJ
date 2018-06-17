package isk.service;

import isk.model.Network;
import isk.util.CommandUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AiroDumpThread implements Runnable {

    private final String interfaceName;
    private final Network network;
    private InputStream is;
    private ProcessBuilder pb;
    private Process process;
    private Thread thread;

    public AiroDumpThread(String interfaceName, Network network) {
        this.interfaceName = interfaceName;
        this.network = network;
        this.thread = new Thread(this);
    }

    public void processInputSream() throws IOException {
        InputStreamReader isr = new InputStreamReader(this.is);
        BufferedReader br = new BufferedReader(isr);
    }

    public void startAttack() throws IOException, InterruptedException {
        this.pb = new ProcessBuilder(CommandUtil.getAttack(
                network.getBSSID(),
                interfaceName));
        this.process = this.pb.start();
        this.is = this.process.getInputStream();
        this.thread.start();
        this.process.waitFor();
    }

    @Override
    public void run() {
        try {
            this.processInputSream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
