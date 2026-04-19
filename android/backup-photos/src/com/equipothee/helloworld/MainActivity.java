package com.equipothee.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    private static final int PICK_IMAGES = 1;
    private EditText urlField;
    private TextView logView;
    private ScrollView scrollView;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new Handler(Looper.getMainLooper());
        urlField = findViewById(R.id.urlField);
        logView = findViewById(R.id.logView);
        scrollView = findViewById(R.id.scrollView);

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> pickImages());
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
            out.write(("\r\n--" + boundary + "--\r\n").getBytes("UTF-8"));
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            conn.disconnect();
            return code >= 200 && code < 300;

        } catch (Exception e) {
            String a = "\n" + e.getClass().getSimpleName() + ": " + e.getMessage() + "\n";

            mainHandler.post(() -> {
                logView.append(a);
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
            });
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
        String[] projection = { android.provider.MediaStore.Images.Media.DATE_TAKEN };
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                long dateTaken = cursor.getLong(0);
                if (dateTaken > 0) {
                    String formatted = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                            .format(new Date(dateTaken));
                    return "IMG_" + formatted + ".jpg";
                }
            }
        } catch (Exception ignored) {
        }

        // Fallback : date actuelle
        String formatted = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return "IMG_" + formatted + ".jpg";
    }

    private String getFileNameFonctionnepas(Uri uri) {
        // Essai 1 : via MediaStore (donne le vrai nom PXL_xxxx.jpg)
        String[] projection = { android.provider.MediaStore.MediaColumns.DISPLAY_NAME };
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
            String a = "\n" + e.getClass().getSimpleName() + ": " + e.getMessage() + "\n";

            mainHandler.post(() -> {
                logView.append(a);
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
            });

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

            String a = "\n" + e.getClass().getSimpleName() + ": " + e.getMessage() + "\n";

            mainHandler.post(() -> {
                logView.append(a);
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
            });
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
}
