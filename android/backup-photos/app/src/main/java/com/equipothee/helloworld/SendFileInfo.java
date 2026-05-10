package com.equipothee.helloworld;

import android.net.Uri;

import java.util.Objects;

public class SendFileInfo {
    private final State state;
    private MyFile myFile;
    private StringBuilder logs;

    public SendFileInfo(MyFile myFile) {
        this.myFile = myFile;
        this.state = State.A_ENVOYER;
        this.logs = new StringBuilder();
    }

    public String getFileName() {
        return myFile.getFileName();
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendFileInfo)) return false;
        SendFileInfo other = (SendFileInfo) o;
        return Objects.equals(getUri(), other.getUri());
    }

    public Uri getUri() {
        return this.myFile.getUri();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUri());
    }


    public enum State {
        A_ENVOYER,
        NE_PAS_ENVOYER,
        ENVOI_OK,
        ENVOI_KO,
    }
}
