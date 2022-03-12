package com.ritech.calltank.Audio;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

public class AudioModelClass {

    String path,filename;
    Uri uri;
    Long date;

    public AudioModelClass(String path, String filename, Uri uri, Long date) {
        this.path = path;
        this.filename = filename;
        this.date = date;
        this.uri = uri;
    }

    public AudioModelClass(String path, String name, Uri uri, Date your) {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
