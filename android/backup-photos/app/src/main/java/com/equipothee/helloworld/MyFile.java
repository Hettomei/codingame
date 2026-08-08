package com.equipothee.helloworld;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MyFile {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final String displayName;
    private final long dateTaken;
    private final long size;
    private Uri uri;

    public MyFile(Uri uri, String name, long dateTaken, long size) {
        this.uri = uri;
        this.displayName = name;
        this.dateTaken = dateTaken;
        this.size = size;
    }

    public String getFileName() {
        return this.displayName;
    }

    public Uri getUri() {
        return uri;
    }

    public String debug(ContentResolver resolver) {
        StringBuilder sb = new StringBuilder();
        String dateString = (dateTaken > 0) ? dateFormat.format(new Date(dateTaken)) : "Aucune date";
        double sizeInMb = size / (1024.0 * 1024.0);

        sb.append("------- FILE -------\n")
                .append("MimeType: ").append(getMimeType(resolver)).append("\n")
                .append("Name: ").append(displayName).append("\n")
                .append("Date: ").append(dateString).append("\n")
                .append("Size: ").append(String.format(Locale.getDefault(), "%.2f", sizeInMb)).append(" MB\n")
                .append("Uri: ").append(getUri());

        return sb.toString();
    }

    public String getMimeType(ContentResolver resolver) {
        String mimeType = resolver.getType(uri);

        if (mimeType == null) {
            Log.w("MyFile", "Failed to get mime type for " + uri);
            final String extension = MimeTypeMap.getFileExtensionFromUrl(getFileName());
            if (extension != null) {
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
            }
        }

        return mimeType != null ? mimeType : "application/octet-stream";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyFile)) return false;
        MyFile other = (MyFile) o;
        return Objects.equals(getUri(), other.getUri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUri());
    }
}
