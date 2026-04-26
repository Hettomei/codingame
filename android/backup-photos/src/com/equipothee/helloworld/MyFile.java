package com.equipothee.helloworld;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyFile {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final long id;
    private final int mediaType;
    private final String displayName;
    private final long dateTaken;
    private final long size;
    private Uri uri;

    public MyFile(long id, int mediaType, String displayName, long dateTaken, long size) {
        this.uri = null;
        this.id = id;
        this.mediaType = mediaType;
        this.displayName = displayName;
        this.dateTaken = dateTaken;
        this.size = size;
    }

    public String getFileName() {
        return this.displayName;
    }

    public Uri getUri() {
        if (uri != null) return uri;
        uri = ContentUris.withAppendedId(getBaseUri(), id);
        return uri;
    }

    private Uri getBaseUri() {
        return isTypeVideo() ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    private boolean isTypeVideo() {
        return mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
    }

    public String debug() {
        StringBuilder sb = new StringBuilder();
        String dateString = (dateTaken > 0) ? dateFormat.format(new Date(dateTaken)) : "Aucune date";
        String type = isTypeVideo() ? "video" : "image";
        double sizeInMb = size / (1024.0 * 1024.0);

        sb.append("------- FILE ").append(id).append(" -------\n")
                .append("Type: ").append(type).append("\n")
                .append("MimeType: ").append(getMimeType()).append("\n")
                .append("Name: ").append(displayName).append("\n")
                .append("Date: ").append(dateString).append("\n")
                .append("Size: ").append(String.format(Locale.getDefault(), "%.2f", sizeInMb)).append(" MB");

        return sb.toString();
    }

    public String getMimeType() {
        final String extension = MimeTypeMap.getFileExtensionFromUrl(getFileName());
        String mimeType = null;
        if (extension != null) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        if (mimeType == null) mimeType = "application/octet-stream";

        return mimeType;
    }
}