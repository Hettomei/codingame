package com.equipothee.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.provider.MediaStore;
import android.content.ContentUris;
import java.util.Calendar;

public class MainActivity extends Activity {

    private static final int PICK_IMAGES = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    // Constantes pour la sauvegarde
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_URL = "target_url";
    private static final String DEFAULT_URL = "http://192.168.1.15:8000/sendpics/send-photo";
    private EditText urlField;
    private TextView logView;
    private ScrollView scrollView;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new Handler(Looper.getMainLooper());
        logView = findViewById(R.id.logView);
        scrollView = findViewById(R.id.scrollView);
        urlField = findViewById(R.id.urlField);

        // 1. Charger l'URL sauvegardée au démarrage
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedUrl = prefs.getString(KEY_URL, DEFAULT_URL);
        urlField.setText(savedUrl);

        // 2. Logique du bouton Reset
        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(v -> {
            urlField.setText(DEFAULT_URL);
            saveUrl(DEFAULT_URL); // On remet aussi à zéro la sauvegarde
        });

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> pickImages());
        findViewById(R.id.btnMonth).setOnClickListener(v -> checkPermissionAndProceed());
    }

    private void checkPermissionAndProceed() {
        // Déterminer quelle permission demander selon la version d'Android
        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        // Vérifier si la permission est déjà accordée
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            // Déjà autorisé, on lance le scan
            selectCurrentMonthPhotos();
        } else {
            // Non autorisé, on demande à l'utilisateur
            requestPermissions(new String[]{permission}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // L'utilisateur a dit OUI
                logView.append("Permission accordée.\n");
                selectCurrentMonthPhotos();
            } else {
                // L'utilisateur a dit NON
                logView.append("Erreur : Permission refusée. Impossible de scanner les photos.\n");
            }
        }
    }


    // Méthode pour sauvegarder l'URL
    private void saveUrl(String url) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(KEY_URL, url).apply();
    }

    private void pickImages() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Choisir des photos"), PICK_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_IMAGES || resultCode != RESULT_OK || data == null)
            return;

        String currentUrl = urlField.getText().toString().trim();
        saveUrl(currentUrl);

        List<Uri> uris = new ArrayList<>();
        if (data.getClipData() != null) {
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                uris.add(data.getClipData().getItemAt(i).getUri());
                String line = "photo ajoutée " + data.getClipData().getItemAt(i).getUri() + "\n";
                mainHandler.post(() -> {
                    logView.append(line);
                    scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                });
            }
        } else if (data.getData() != null) {
            uris.add(data.getData());
        }

        String targetUrl = urlField.getText().toString().trim();
        String line = "sent to '" + targetUrl + "'\n";

        mainHandler.post(() -> {
            logView.append(line);
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });

        new Thread(() -> sendAll(uris, targetUrl)).start();
    }

    private void sendAll(List<Uri> uris, String targetUrl) {
        for (Uri uri : uris) {
            String name = getFileName(uri);
            boolean ok = sendPhoto(uri, name, targetUrl);
            String date = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            String line = "[" + date + "] " + name + " : " + (ok ? "OK" : "KO") + "\n";
            mainHandler.post(() -> {
                logView.append(line);
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
            });
        }
    }

    private boolean sendPhoto(Uri uri, String filename, String targetUrl) {
        String boundary = "----Boundary" + System.currentTimeMillis();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(targetUrl).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(30000);

            OutputStream out = conn.getOutputStream();

            // Part header
            String partHeader = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"photo\"; filename=\""
                    + filename + "\"\r\n" + "Content-Type: image/jpeg\r\n\r\n";
            out.write(partHeader.getBytes("UTF-8"));

            // Données image
            InputStream in = getContentResolver().openInputStream(uri);
            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) != -1)
                out.write(buf, 0, len);
            in.close();

            // Part footer
            out.write(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            conn.disconnect();
            return code >= 200 && code < 300;

        } catch (Exception e) {
            logException(e);
            return false;
        }
    }

    private String getFileNameOld(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (idx >= 0)
                        result = cursor.getString(idx);
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result != null ? result : "photo.jpg";
    }

    private String getFileName(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATE_TAKEN};
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                long dateTaken = cursor.getLong(0);
                if (dateTaken > 0) {
                    String formatted = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                            .format(new Date(dateTaken));
                    return "IMG_" + formatted + ".jpg";
                }
            }
        } catch (Exception e) {
            logException(e);
            throw e;
        }

        // Fallback : date actuelle
        String formatted = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return "IMG_" + formatted + ".jpg";
    }

    private void debugChooseFileName(Uri uri) {
        // On définit les colonnes que l'on veut récupérer
        String[] projection = {
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DISPLAY_NAME
        };

        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                // 1. Récupération de la date de capture
                int dateIdx = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                long dateTaken = (dateIdx != -1) ? cursor.getLong(dateIdx) : 0;

                // 2. Récupération du nom affiché (nom de fichier réel)
                int nameIdx = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                String displayName = (nameIdx != -1) ? cursor.getString(nameIdx) : "Inconnu";

                // Formatage de la date pour le log
                String dateString = "Aucune date";
                if (dateTaken > 0) {
                    dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                            .format(new Date(dateTaken));
                }

                // Construction du message de debug
                String debugInfo = "[DEBUG FILE]\n" +
                        "ID/Uri : " + uri.getLastPathSegment() + "\n" +
                        "Nom réel : " + displayName + "\n" +
                        "Date de capture : " + dateString + "\n";

                // Affichage dans le logView sur le thread principal
                mainHandler.post(() -> {
                    logView.append(debugInfo);
                    scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                });
            } else {
                mainHandler.post(() -> logView.append("DEBUG : Impossible de lire les métadonnées pour cet URI.\n"));
            }
        } catch (Exception e) {
            logException(e);
        }
    }

    private void logException(Exception e){
        String errorMsg = "ERROR: " + e.getClass().getSimpleName() + " " + e.getMessage() + "\n";
        mainHandler.post(() -> {
            logView.append(errorMsg);
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    private String getFileNameFonctionnepas(Uri uri) {
        // Essai 1 : via MediaStore (donne le vrai nom PXL_xxxx.jpg)
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(0);
                String a = " *** " + name + " ***\n";
                mainHandler.post(() -> {
                    logView.append(a);
                    scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                });
                if (name != null && name.contains("."))
                    return name;
            }
        } catch (Exception e) {
            logException(e);
        }

        // Essai 2 : via OpenableColumns
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (idx >= 0) {
                    String name = cursor.getString(idx);
                    String a = " *** " + name + " ***\n";
                    mainHandler.post(() -> {
                        logView.append(a);
                        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                    });
                    if (name != null && name.contains("."))
                        return name;
                }
            }
        } catch (Exception e) {
            logException(e);
        }

        // Essai 3 : dernier segment de l'Uri
        String last = uri.getLastPathSegment();
        return (last != null) ? last : "photo.jpg";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void selectCurrentMonthPhotos() {
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
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_TAKEN
        };

        // On filtre par date de capture et on exclut les images qui n'ont pas de date
        String selection = MediaStore.Images.Media.DATE_TAKEN + " >= ?";
        long startTime = calendar.getTimeInMillis();
        String[] selectionArgs = new String[]{String.valueOf(startTime)};
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " ASC";

        try (Cursor cursor = getContentResolver().query(collection, projection, selection, selectionArgs, sortOrder)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

                do {
                    long id = cursor.getLong(idColumn);
                    Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    uris.add(contentUri);
                    debugChooseFileName(contentUri);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            logException(e);
        }

        // 3. Informer l'utilisateur et envoyer
        String targetUrl = urlField.getText().toString().trim();
        String msg = "Auto-sélection : " + uris.size() + " photos trouvées pour ce mois.\n";

        mainHandler.post(() -> {
            logView.append(msg);
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });

//        if (!uris.isEmpty()) {
//            new Thread(() -> sendAll(uris, targetUrl)).start();
//        }
    }

}
