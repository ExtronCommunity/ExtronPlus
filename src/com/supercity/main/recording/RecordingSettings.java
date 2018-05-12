package com.supercity.main.recording;

import com.supercity.main.utils.Reference;

public class RecordingSettings {

    private boolean recording;
    private boolean showScoreboard;
    private boolean showChat;
    private boolean sendSleepAlerts;
    private boolean sendRecordAlerts;
    public int afkTimer;
    private boolean afk;

    public RecordingSettings() {
        recording = false;
        showScoreboard = true;
        showChat = true;
        sendSleepAlerts = true;
        sendRecordAlerts = true;
        afkTimer = 0;
        afk = false;
    }

    public void setRecording(boolean b) {
        this.recording = b;
    }

    public void setSendSleepAlerts(boolean sendSleepAlerts) {
        this.sendSleepAlerts = sendSleepAlerts;
    }

    public void setShowChat(boolean showChat) {
        this.showChat = showChat;
    }

    public void setShowScoreboard(boolean showScoreboard) {
        this.showScoreboard = showScoreboard;
    }

    public boolean isRecording() {
        return recording;
    }

    public boolean sendSleepAlerts() {
        return sendSleepAlerts;
    }

    public boolean showChat() {
        return showChat;
    }

    public boolean showScoreboard() {
        return showScoreboard;
    }

    public boolean sendRecordAlerts() {
        return sendRecordAlerts;
    }

    public void setSendRecordAlerts(boolean sendRecordAlerts) {
        this.sendRecordAlerts = sendRecordAlerts;
    }

    public void setAFK(boolean afk) {
        this.afk = afk;
    }

    public boolean isAFK() {
        return afk;
    }
}
