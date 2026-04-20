package com.equipothee.helloworld;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileSelector {
    private final MainActivity mainActivity;
    private final SimpleDateFormat dateFormat;

    public FileSelector(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    public List<Uri> getCurrentMonth(String recherche) {
        long startTime = SystemClock.elapsedRealtime(); // Performance metric
        List<Uri> uris = new ArrayList<>();
        int pics = 0;
        int vids = 0;

        // 1. Single projection for all file types
        String[] projections = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATE_TAKEN,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.SIZE
        };

        // 2. Query MediaStore.Files to get both Images and Videos in one IPC call
        // Filter by name and ensure we only get Images or Videos
        String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + " LIKE ? AND (" +
                MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " OR " +
                MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO + ")";

        String[] selectionArgs = new String[]{recherche};
        String sortOrder = MediaStore.Files.FileColumns.DISPLAY_NAME + " ASC";

        Uri queryUri = MediaStore.Files.getContentUri("external");

        StringBuilder logBatchPic = new StringBuilder();
        StringBuilder logBatchVid = new StringBuilder();

        try (Cursor cursor = mainActivity.getContentResolver().query(queryUri, projections, selection, selectionArgs, sortOrder)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
                int nameCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
                int dateCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN);
                int typeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE);
                int sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);

                do {
                    long id = cursor.getLong(idCol);
                    int mediaType = cursor.getInt(typeCol);
                    String displayName = cursor.getString(nameCol);
                    long dateTaken = cursor.getLong(dateCol);
                    long size = cursor.getLong(sizeCol);

                    // Determine correct base URI for the specific file type
                    Uri baseUri;
                    if (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                        baseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        vids++;
                        appendFileInfoToBuffer(logBatchVid, id, displayName, dateTaken, mediaType, size);
                    } else {
                        baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        pics++;
                        appendFileInfoToBuffer(logBatchPic, id, displayName, dateTaken, mediaType, size);
                    }

                    uris.add(ContentUris.withAppendedId(baseUri, id));
                } while (cursor.moveToNext());

                logBatchPic.append(logBatchVid);
                mainActivity.logMessage(logBatchPic.toString());
            } else {
                mainActivity.logMessage("ERROR : Aucun média trouvé.");
            }
        } catch (Exception e) {
            mainActivity.logMessage(logBatchPic.toString()); // on veut voir jusqu ou il a enregistré
            mainActivity.logException(e);
        }

        long duration = SystemClock.elapsedRealtime() - startTime;
        mainActivity.logMessage("Sélection : \n" +
                pics + " images\n" +
                vids + " vidéos\n" +
                "total: " + uris.size() +
                "Temps : " + duration + " ms");

        return uris;
    }

    private void appendFileInfoToBuffer(StringBuilder sb, long displayId, String displayName, long dateTaken, int mediaType, long size) {
        String dateString = (dateTaken > 0) ? dateFormat.format(new Date(dateTaken)) : "Aucune date";
        String type = (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) ? "video" : "image";
        double sizeInMb = size / (1024.0 * 1024.0);

        sb.append("------- FILE " + displayId + " -------\n")
                .append("Type: ").append(type).append("\n")
                .append("Name: ").append(displayName).append("\n")
                .append("Date: ").append(dateString).append("\n")
                .append("Size: ").append(String.format(Locale.getDefault(), "%.2f", sizeInMb)).append(" MB").append("\n");
    }
}