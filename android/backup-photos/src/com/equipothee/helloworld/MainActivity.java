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
import android.provider.MediaStore;
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

public class MainActivity extends Activity {

    private static final int PICK_IMAGES = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    // Constantes pour la sauvegarde
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_URL = "target_url";
    private static final String DEFAULT_URL = "http://192.168.1.15:8000/sendpics/send-photo";
    private static final String KEY_SEARCH = "target_search";
    public Handler mainHandler;
    private EditText urlField;
    private EditText searchField;
    private TextView logView;
    private ScrollView scrollView;
    private FileSelector fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new Handler(Looper.getMainLooper());
        fs = new FileSelector(this);
        logView = findViewById(R.id.logView);
        scrollView = findViewById(R.id.scrollView);
        urlField = findViewById(R.id.urlField);
        searchField = findViewById(R.id.searchField);

        // 1. Charger l'URL sauvegardée au démarrage
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedUrl = prefs.getString(KEY_URL, DEFAULT_URL);
        urlField.setText(savedUrl);

        String dateString = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date());
        String selectionArgs = "PXL_" + dateString + "%";
        String savedSearch = prefs.getString(KEY_SEARCH, selectionArgs);
        searchField.setText(savedSearch);

        findViewById(R.id.btnReset).setOnClickListener(v -> {
            urlField.setText(DEFAULT_URL);
            savePref(KEY_URL, DEFAULT_URL); // On remet aussi à zéro la sauvegarde
        });

        findViewById(R.id.btnResetSearch).setOnClickListener(v -> {
            searchField.setText(selectionArgs);
            savePref(KEY_SEARCH, selectionArgs); // On remet aussi à zéro la sauvegarde
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
            // autorisé
            getCurrentSearch();
        } else {
            // Non autorisé
            requestPermissions(new String[]{permission}, PERMISSION_REQUEST_CODE);
        }
    }

    private void getCurrentSearch() {
        List<Uri> uris = fs.getCurrentMonth();
        savePref(KEY_URL, urlField.getText().toString().trim());

//            if (!uris.isEmpty()) {
//                String targetUrl = urlField.getText().toString().trim();
//                new Thread(() -> sendAll(uris, targetUrl)).start();
//            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // L'utilisateur a dit OUI
                getCurrentSearch();
            } else {
                // L'utilisateur a dit NON
                logView.append("Erreur : Permission refusée. Impossible de scanner les photos.\n");
            }
        }
    }


    // Méthode pour sauvegarder l'URL
    private void savePref(String key, String url) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(key, url).apply();
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
        savePref(KEY_URL, currentUrl);

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
            out.write(partHeader.getBytes(StandardCharsets.UTF_8));

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

    public void logException(Exception e) {
        String errorMsg = "ERROR: " + e.getClass().getSimpleName() + " " + e.getMessage() + "\n";
        mainHandler.post(() -> {
            logView.append(errorMsg);
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    public void logMessage(String str) {
        mainHandler.post(() -> {
            logView.append(str + "\n");
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
