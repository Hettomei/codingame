package com.equipothee.helloworld;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileSelector {
    private final MainActivity mainActivity;

    public FileSelector(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<Uri> getCurrentMonth() {
        List<Uri> uris = new ArrayList<>();

        // 1. Calculer le timestamp du 1er jour du mois actuel à 00:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 2. Préparer la requête sur le MediaStore
        Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projections = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DISPLAY_NAME,
        };

        // On filtre par date de capture et on exclut les images qui n'ont pas de date
        String selection = MediaStore.Images.Media.DATE_TAKEN + " >= ?";
        long startTime = calendar.getTimeInMillis();
        String[] selectionArgs = new String[]{String.valueOf(startTime)};
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " ASC";

        try (Cursor cursor = mainActivity.getContentResolver().query(collection, projections, selection, selectionArgs, sortOrder)) {
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

        int nameIdx = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
        String displayName = (nameIdx != -1) ? cursor.getString(nameIdx) : "Inconnu";

        int dateIdx = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
        long dateTaken = (dateIdx != -1) ? cursor.getLong(dateIdx) : 0;
        String dateString = "Aucune date";
        if (dateTaken > 0) {
            dateString = new SimpleDateFormat("yyyyMMdd HHmmss", Locale.getDefault())
                    .format(new Date(dateTaken));
        }

        String debugInfo = "------- FILE -------\n" +
                "Uri  : " + uri.getLastPathSegment() + "\n" +
                "id   : " + displayId + "\n" +
                "Nom  : " + displayName + "\n" +
                "Date :     " + dateString + "\n";

        // Affichage dans le logView sur le thread principal
        mainActivity.logMessage(debugInfo);
    }

}
