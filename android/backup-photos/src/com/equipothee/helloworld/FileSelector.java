package com.equipothee.helloworld;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileSelector {
    private final MainActivity mainActivity;

    public FileSelector(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<Uri> getCurrentMonth(String recherche) {
        List<Uri> uris = new ArrayList<>();

        // 2. Préparer la requête sur le MediaStore
        String[] projections = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DISPLAY_NAME,
        };

        String[] selectionArgs = new String[]{recherche};
        String selection = MediaStore.Images.Media.DISPLAY_NAME + " like ?";
        String sortOrder = MediaStore.Images.Media.DISPLAY_NAME + " ASC";

        try (Cursor cursor = mainActivity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projections, selection, selectionArgs, sortOrder)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                do {
                    long id = cursor.getLong(idColumn);
                    Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    uris.add(contentUri);
                    fileInfo(contentUri, cursor);
                } while (cursor.moveToNext());
            } else {
                mainActivity.logMessage("ERROR : probleme avec cursor");
            }
        } catch (Exception e) {
            mainActivity.logException(e);
        }

        // 3. Informer l'utilisateur et envoyer
        mainActivity.logMessage("Auto-sélection : " + uris.size() + " photos trouvées pour ce mois.");
        return uris;
    }

    private void fileInfo(Uri uri, Cursor cursor) {
        String displayId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));

        int dateIdx = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
        long dateTaken = (dateIdx != -1) ? cursor.getLong(dateIdx) : 0;
        String dateString = "Aucune date";
        if (dateTaken > 0) {
            dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(dateTaken));
        }

        String debugInfo = "------- FILE -------\n" +
                "id   : " + displayId + "\n" +
                "Nom  : " + displayName + "\n" +
                "Date : " + dateString + "\n";

        // Affichage dans le logView sur le thread principal
        mainActivity.logMessage(debugInfo);
    }

}
