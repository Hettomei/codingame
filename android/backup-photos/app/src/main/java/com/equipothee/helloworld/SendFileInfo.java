package com.equipothee.helloworld;

public class SendFileInfo {
    private final State state;
    private MyFile myFile;
    private StringBuilder logs;

    public String getFileName() {
        return myFile.getFileName();
    }

    public State getState() {
        return state;
    }

    public enum State {
        A_ENVOYER,
        NE_PAS_ENVOYER,
        ENVOI_OK,
        ENVOI_KO,
    }

    public SendFileInfo(MyFile myFile) {
        this.myFile = myFile;
        this.state = State.A_ENVOYER;
        this.logs = new StringBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendFileInfo)) return false;
        SendFileInfo other = (SendFileInfo) o;
        return this.getId() == other.getId();
    }

    public long getId() {
        return this.myFile.getId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.getId());
    }
}