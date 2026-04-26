package com.equipothee.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
    private static final SimpleDateFormat classicTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
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

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        urlField.setText(prefs.getString(KEY_URL, DEFAULT_URL));

        String dateString = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date());
        String selectionArgs = "PXL_" + dateString + "%";
        searchField.setText(prefs.getString(KEY_SEARCH, selectionArgs));

        findViewById(R.id.btnReset).setOnClickListener(v -> {
            urlField.setText(DEFAULT_URL);
            savePref(KEY_URL, DEFAULT_URL); // On remet aussi à zéro la sauvegarde
        });

        findViewById(R.id.btnResetSearch).setOnClickListener(v -> {
            searchField.setText(selectionArgs);
            savePref(KEY_SEARCH, selectionArgs); // On remet aussi à zéro la sauvegarde
        });

        findViewById(R.id.btnSend).setOnClickListener(v -> pickImages());
        findViewById(R.id.btnMonth).setOnClickListener(v -> checkPermissionAndProceed());
    }

    private void checkPermissionAndProceed() {
        // Déterminer quelle permission demander selon la version d'Android
        String permissionImg;
        String permissionVid;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionImg = Manifest.permission.READ_MEDIA_IMAGES;
            permissionVid = Manifest.permission.READ_MEDIA_VIDEO;
        } else {
            permissionImg = Manifest.permission.READ_EXTERNAL_STORAGE;
            permissionVid = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        // Vérifier si la permissionImg est déjà accordée
        if (checkSelfPermission(permissionImg) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(permissionVid) == PackageManager.PERMISSION_GRANTED) {
            // autorisé
            getCurrentSearch();
        } else {
            // Non autorisé
            requestPermissions(new String[]{permissionImg, permissionVid}, PERMISSION_REQUEST_CODE);
        }
    }

    private void getCurrentSearch() {
        savePref(KEY_URL, urlField.getText().toString().trim());
        savePref(KEY_SEARCH, searchField.getText().toString());

        List<MyFile> myFiles = fs.getCurrentMonth(searchField.getText().toString());

        // TODO : afficher un mini recap
//        logBatchPic.append(myFiles.size()).append(" fichiers : ")
//                .append(pics).append(" images, ")
//                .append(vids).append(" vidéos\n")
//                .append("Temps : ").append(duration).append(" ms");
//        mainActivity.logMessage(logBatchPic);


        new Thread(() -> sendAll(myFiles)).start();
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
                logMessage("Erreur : Permission refusée. Impossible de scanner les photos.");
            }
        }
    }

    private void savePref(String key, String url) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(key, url).apply();
    }

    private void pickImages() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Choisir photos et vidéos"), PICK_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_IMAGES || resultCode != RESULT_OK || data == null) {
            logMessage("Rien d interessant");
            return;
        }

        String currentUrl = urlField.getText().toString().trim();
        savePref(KEY_URL, currentUrl);

        List<MyFile> myFiles = new ArrayList<>();

        Uri uri;
        if (data.getClipData() != null) {
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                uri = data.getClipData().getItemAt(i).getUri();
                myFiles.add(fs.getFile(uri));
            }
        } else if (data.getData() != null) {
            uri = data.getData();
            myFiles.add(fs.getFile(uri));
        } else {
            logMessage("Normalement vous ne devriez pas voir ce message.");
        }
        new Thread(() -> sendAll(myFiles)).start();
    }

    private void sendAll(List<MyFile> myFiles) {
        String targetUrl = urlField.getText().toString().trim();
        logMessage("sent to " + targetUrl);
        for (MyFile myFile : myFiles) {
            logMessage(myFile.debug());
            boolean ok = sendPhoto(myFile, targetUrl);
            String line = myFile.getFileName() + " : " + (ok ? "OK" : "KO");
            logMessage(line);
        }
    }

    private boolean sendPhoto(MyFile myFile, String targetUrl) {
        String boundary = "----Boundary" + System.currentTimeMillis();
        HttpURLConnection conn = null;

        try {
            // --- MIME type dynamique --- TODO a changer
            String mimeType = getContentResolver().getType(myFile.getUri());
            if (mimeType == null) mimeType = "application/octet-stream";

            conn = (HttpURLConnection) new URL(targetUrl).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setConnectTimeout(10_000);
            conn.setReadTimeout(30_000);

            // --- Streaming sans bufferiser tout en RAM ---
            conn.setChunkedStreamingMode(8192);

            try (OutputStream out = new BufferedOutputStream(conn.getOutputStream())) {
                String prefixPart = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"prefix\"\r\n\r\n"
                        + "tim" + "\r\n";
                out.write(prefixPart.getBytes(StandardCharsets.UTF_8));

                // Part header
                String partHeader = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"any\"; filename=\""
                        + myFile.getFileName() + "\"\r\n"
                        + "Content-Type: " + mimeType + "\r\n\r\n";
                out.write(partHeader.getBytes(StandardCharsets.UTF_8));

                // Données fichier
                try (InputStream in = new BufferedInputStream(
                        getContentResolver().openInputStream(myFile.getUri()))) {
                    if (in == null)
                        throw new IOException("Impossible d'ouvrir l'URI : " + myFile.getUri());
                    byte[] buf = new byte[8192];
                    int len;
                    while ((len = in.read(buf)) != -1)
                        out.write(buf, 0, len);
                }

                // Part footer
                out.write(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();

            // --- Lire le body d'erreur pour faciliter le debug ---
            if (code < 200 || code >= 300) {
                InputStream err = conn.getErrorStream();
                if (err != null) {
                    String body = new String(err.readAllBytes(), StandardCharsets.UTF_8);
                    Log.w("SendPhoto", "HTTP " + code + " : " + body);
                    err.close();
                }
                return false;
            }
            return true;

        } catch (Exception e) {
            logException(e);
            return false;
        } finally {
            if (conn != null) conn.disconnect();  // ← toujours libéré
        }
    }

    public void logException(Exception e) {
        e.printStackTrace();
        String errorMsg = formatException(e);
        mainHandler.post(() -> {
            logView.append(errorMsg);
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    public String formatException(Exception e) {
        return "[" + classicTimeFormat.format(new Date()) + "] ERROR: " + e.getClass().getSimpleName() + " " + e.getMessage() + "\n";
    }

    public void logMessage(StringBuilder sb) {
        logMessage(sb.toString());
    }

    public void logMessage(String str) {
        String line = "[" + classicTimeFormat.format(new Date()) + "] " + str + "\n";
        mainHandler.post(() -> {
            logView.append(line);
            // scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
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
