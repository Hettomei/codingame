package com.equipothee.helloworld;

import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class FileSelector {
    private final MainActivity mainActivity;

    public FileSelector(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<MyFile> getCurrentMonth(String recherche) {
        long startTime = SystemClock.elapsedRealtime();
        List<MyFile> myFiles = new ArrayList<>();

        // Query MediaStore.Files to get both Images and Videos in one IPC call
        // Filter by name and ensure we only get Images or Videos
        String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + " LIKE ? AND (" +
                MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " OR " +
                MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO + ")";

        String[] selectionArgs = new String[]{recherche};
        String sortOrder = MediaStore.Files.FileColumns.DISPLAY_NAME + " ASC";

        Uri queryUri = MediaStore.Files.getContentUri("external");

        try (Cursor cursor = mainActivity.getContentResolver().query(queryUri, MyProjections.full(), selection, selectionArgs, sortOrder)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
                int nameCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
                int dateCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN);
                int typeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE);
                int sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);

                do {
                    MyFile myFile = extractFileFromCursor(cursor, idCol, nameCol, dateCol, typeCol, sizeCol);
                    myFiles.add(myFile);
                } while (cursor.moveToNext());
            } else {
                mainActivity.logMessage("ERROR : Aucun média trouvé.");
            }
        } catch (Exception e) {
            mainActivity.logException(e);
        }

        long duration = SystemClock.elapsedRealtime() - startTime;
        return myFiles;
    }

    public MyFile getFile(Uri uri) {
        String fileId = uri.getLastPathSegment();

        Uri queryUri = MediaStore.Files.getContentUri("external");
        String selection = MediaStore.Files.FileColumns._ID + " = ?";
        String[] selectionArgs = new String[]{fileId};

        // Exécuter la requête
        try (Cursor cursor = mainActivity.getContentResolver().query(queryUri, MyProjections.full(), selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                // Récupération des indices des colonnes
                int idCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
                int nameCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
                int dateCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN);
                int typeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE);
                int sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);

                return extractFileFromCursor(cursor, idCol, nameCol, dateCol, typeCol, sizeCol);
            } else {
                return null;
            }
        }
    }

    private MyFile extractFileFromCursor(Cursor cursor, int idCol, int nameCol, int dateCol, int typeCol, int sizeCol) {
        // Extraction des données
        long id = cursor.getLong(idCol);
        String displayName = cursor.getString(nameCol);
        long dateTaken = cursor.getLong(dateCol);
        int mediaType = cursor.getInt(typeCol);
        long size = cursor.getLong(sizeCol);
        return new MyFile(id, mediaType, displayName, dateTaken, size);
    }


}