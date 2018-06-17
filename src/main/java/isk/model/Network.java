package isk.model;

public class Network {

    private String BSSID = "";
    private String channel = "";
    private String privacy = "";
    private String cipher = "";
    private String power = "";
    private String beacons = "";
    private String ESSID = "";

    public Network() {
    }

    public Network(String BSSID, String channel, String privacy, String cipher, String power, String beacons, String ESSID) {
        this.BSSID = BSSID;
        this.channel = channel;
        this.privacy = privacy;
        this.cipher = cipher;
        this.power = power;
        this.beacons = beacons;
        this.ESSID = ESSID;
    }

    public Network(NetworkTable networkTable){
        this.BSSID = networkTable.getBSSID();
        this.channel = networkTable.getChannel();
        this.privacy = networkTable.getPrivacy();
        this.cipher = networkTable.getCipher();
        this.power = networkTable.getPower();
        this.beacons = networkTable.getBeacons();
        this.ESSID = networkTable.getESSID();
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getBeacons() {
        return beacons;
    }

    public void setBeacons(String beacons) {
        this.beacons = beacons;
    }

    public String getESSID() {
        return ESSID;
    }

    public void setESSID(String ESSID) {
        this.ESSID = ESSID;
    }
}
