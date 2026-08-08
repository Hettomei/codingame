package com.equipothee.helloworld;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import java.util.ArrayList;
import java.util.List;

public class FileSelector {
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_TAKEN,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.SIZE
    };
    private final MainActivity mainActivity;

    public FileSelector(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<MyFile> getCurrentMonth(String recherche) {
        String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{recherche};
        String sortOrder = MediaStore.Files.FileColumns.DISPLAY_NAME + " ASC";

        Uri queryUri = MediaStore.Files.getContentUri("external");

        try (Cursor cursor = mainActivity.getContentResolver().query(queryUri, PROJECTION, selection, selectionArgs, sortOrder)) {
            return extractFilesFromCursor(cursor);
        } catch (Exception e) {
            mainActivity.reportError("FileSelector", e);
            return new ArrayList<>();
        }
    }

    public MyFile getFile(Uri uri) {
        // 1. Tenter de récupérer via MediaStore si c'est un ID numérique
        try {
            String fileId = uri.getLastPathSegment();
            if (fileId != null) {
                if (fileId.contains(":")) {
                    fileId = fileId.split(":")[1];
                }

                try {
                    long id = Long.parseLong(fileId);
                    Uri queryUri = MediaStore.Files.getContentUri("external");
                    String selection = MediaStore.Files.FileColumns._ID + " = ?";
                    String[] selectionArgs = new String[]{String.valueOf(id)};

                    try (Cursor cursor = mainActivity.getContentResolver().query(queryUri, PROJECTION, selection, selectionArgs, null)) {
                        return extractFilesFromCursor(cursor).get(0);
                    }
                } catch (NumberFormatException e) {
                    // Pas un ID numérique MediaStore, on passera au fallback
                    mainActivity.reportError("NumberFormatException", e);
                }
            }

            // 2. Fallback : Interroger l'URI directement (cas ACTION_OPEN_DOCUMENT)
            try (Cursor cursor = mainActivity.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);

                    String name = (nameIndex != -1) ? cursor.getString(nameIndex) : "unknown";
                    long size = (sizeIndex != -1) ? cursor.getLong(sizeIndex) : 0;
                    long dateTaken = 0;

                    return new MyFile(uri, name, dateTaken, size);
                }
            }
        } catch (Exception e) {
            mainActivity.reportError("FileSelector", e);
        }

        return null;
    }

    private MyFile extractFileFromCursor(Cursor cursor, int idCol, int nameCol, int dateCol, int typeCol, int sizeCol) {
        long id = cursor.getLong(idCol);
        String displayName = cursor.getString(nameCol);
        long dateTaken = cursor.getLong(dateCol);
        int mediaType = cursor.getInt(typeCol);
        Uri baseUri = mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uri = ContentUris.withAppendedId(baseUri, id);
        long size = cursor.getLong(sizeCol);

        return new MyFile(uri, displayName, dateTaken, size);
    }

    private List<MyFile> extractFilesFromCursor(Cursor cursor) {
        List<MyFile> myFiles = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            int idCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
            int nameCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
            int dateCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN);
            int typeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE);
            int sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);

            do {
                myFiles.add(extractFileFromCursor(cursor, idCol, nameCol, dateCol, typeCol, sizeCol));
            } while (cursor.moveToNext());
        } else {
            mainActivity.logMessage("ERROR : Aucun média trouvé.");
        }
        return myFiles;
    }
}
