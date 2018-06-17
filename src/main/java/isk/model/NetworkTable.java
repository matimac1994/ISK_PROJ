package isk.model;

import javafx.beans.property.SimpleStringProperty;

public class NetworkTable {

    private final SimpleStringProperty BSSID = new SimpleStringProperty("");
    private final SimpleStringProperty channel = new SimpleStringProperty("");
    private final SimpleStringProperty privacy = new SimpleStringProperty("");
    private final SimpleStringProperty cipher = new SimpleStringProperty("");
    private final SimpleStringProperty power = new SimpleStringProperty("");
    private final SimpleStringProperty beacons = new SimpleStringProperty("");
    private final SimpleStringProperty ESSID = new SimpleStringProperty("");


    public NetworkTable() {
    }

    public NetworkTable(String BSSID, String channel, String privacy, String cipher, String power, String beacons, String ESSID) {
        this.BSSID.set(BSSID);
        this.channel.set(channel);
        this.privacy.set(privacy);
        this.cipher.set(cipher);
        this.power.set(power);
        this.beacons.set(beacons);
        this.ESSID.set(ESSID);
    }

    public String getBSSID() {
        return BSSID.get();
    }

    public SimpleStringProperty BSSIDProperty() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID.set(BSSID);
    }

    public String getChannel() {
        return channel.get();
    }

    public SimpleStringProperty channelProperty() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel.set(channel);
    }

    public String getPrivacy() {
        return privacy.get();
    }

    public SimpleStringProperty privacyProperty() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy.set(privacy);
    }

    public String getCipher() {
        return cipher.get();
    }

    public SimpleStringProperty cipherProperty() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher.set(cipher);
    }

    public String getPower() {
        return power.get();
    }

    public SimpleStringProperty powerProperty() {
        return power;
    }

    public void setPower(String power) {
        this.power.set(power);
    }

    public String getBeacons() {
        return beacons.get();
    }

    public SimpleStringProperty beaconsProperty() {
        return beacons;
    }

    public void setBeacons(String beacons) {
        this.beacons.set(beacons);
    }

    public String getESSID() {
        return ESSID.get();
    }

    public SimpleStringProperty ESSIDProperty() {
        return ESSID;
    }

    public void setESSID(String ESSID) {
        this.ESSID.set(ESSID);
    }
}
