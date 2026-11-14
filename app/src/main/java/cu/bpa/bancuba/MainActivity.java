package cu.bpa.bancuba;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

/**
 * Single launcher activity for BanCuba ({@code cu.bpa.bancuba} v1.3, versionCode 2).
 *
 * <p>Generated with the "Website 2 APK Builder Pro" hybrid wrapper template and customized by
 * Banco Popular de Ahorro (BPA) Granma (August 2019). Loads the local offline portal
 * {@code file:///android_asset/index.html} inside an Android {@link WebView}, displays a 1500 ms
 * splash screen ({@code R.drawable.splash}), exposes the {@code Website2APK} JavaScript bridge,
 * and routes external URI schemes ({@code mailto:}, {@code tel:}, {@code market://}, etc.) to
 * system intents.</p>
 *
 * @obfuscated_class cu.bpa.bancuba.MainActivity
 */
public class MainActivity extends Activity {

    /** Logcat tag (obfuscated field {@code i}). */
    private static final String TAG = "MainActivity";

    /** Primary WebView rendering local HTML/JS banking calculators and directories (field {@code a}). */
    WebView webView;

    /** Splash screen overlay image shown for {@code R.integer.duration} ms (field {@code b}). */
    ImageView splashImageView;

    /** Initial URL loaded into the WebView, defaulting to {@code file:///android_asset/index.html} (field {@code c}). */
    String targetUrl;

    /** Parsed host component of {@link #targetUrl} (field {@code d}). */
    String targetHost;

    /** Indeterminate/determinate progress indicator shown during page loads (field {@code e}). */
    ProgressBar progressBar;

    /** Custom WebChromeClient handling dialogs, video fullscreen, and file choosers (field {@code f}). */
    WebChromeClient webChromeClient;

    /** Activity context reference (field {@code g}). */
    Context activityContext;

    /** Tracks whether the back button was pressed within the last 200 ms (field {@code h}). */
    Boolean doubleBackToExitPressedOnce = false;

    /** Temporary file URI string for camera photo capture in file chooser (field {@code j}). */
    private String cameraPhotoPath;

    /** Legacy single-URI file upload callback for Android < 5.0 (field {@code k}). */
    private ValueCallback<Uri> legacyUploadMessage;

    /** Lollipop+ multi-URI file upload callback for Android >= 5.0 (field {@code l}). */
    private ValueCallback<Uri[]> filePathCallback;

    /** Fullscreen container used by WebChromeClient custom views (field {@code m}). */
    private FrameLayout customViewContainer;

    /** Callback notified when a fullscreen custom view is hidden (field {@code n}). */
    private WebChromeClient.CustomViewCallback customViewCallback;

    /** Active fullscreen custom view, or {@code null} if none is displayed (field {@code o}). */
    private View customView;

    /**
     * JavaScript bridge exposed to WebView pages under {@code window.Website2APK}
     * when {@code R.bool.enableJsApi} is {@code true} (inner class {@code MainActivity$a}).
     */
    public class Website2ApkJavascriptBridge {

        /** Bridge context reference (field {@code a}). */
        Context bridgeContext;

        Website2ApkJavascriptBridge(Context context) {
            this.bridgeContext = context;
        }

        @JavascriptInterface
        public void askEnableGPS() {
            MainActivity.this.promptEnableGps();
        }

        @JavascriptInterface
        public void clickMenuButton() {
            MainActivity.this.openOptionsMenu();
        }

        @JavascriptInterface
        public void exitApp() {
            MainActivity.this.confirmOrExitApp();
        }

        @JavascriptInterface
        public int getAppVersionCode() {
            try {
                return MainActivity.this.getPackageManager()
                        .getPackageInfo(MainActivity.this.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @JavascriptInterface
        public String getAppVersionName() {
            try {
                return MainActivity.this.getPackageManager()
                        .getPackageInfo(MainActivity.this.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "Error Fetching Version Info";
            }
        }

        @JavascriptInterface
        public String getUniqueDeviceID() {
            return MainActivity.this.getAndroidDeviceId();
        }

        @JavascriptInterface
        public void openExternal(String url) {
            MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }

        @JavascriptInterface
        public void printPage() {
            MainActivity.this.webView.post(new Runnable() {
                @Override
                public void run() {
                    MainActivity.this.printWebViewPage(MainActivity.this.webView);
                }
            });
        }

        @JavascriptInterface
        public void rateUs() {
            MainActivity.this.openPlayStoreListing();
        }

        @JavascriptInterface
        public void refreshPage() {
            MainActivity.this.webView.post(new Runnable() {
                @Override
                public void run() {
                    MainActivity.this.webView.reload();
                }
            });
        }

        @JavascriptInterface
        public void shareIntent() {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,
                    this.bridgeContext.getResources().getString(R.string.sharesubject));
            intent.putExtra(Intent.EXTRA_TEXT,
                    this.bridgeContext.getResources().getString(R.string.sharetext));
            this.bridgeContext.startActivity(Intent.createChooser(intent, "Share App via"));
        }

        @JavascriptInterface
        public void showAboutDialog() {
            MainActivity.this.showDefaultAboutDialog();
        }

        @JavascriptInterface
        public void showAboutDialog(String message, String title, String buttonText) {
            MainActivity.this.showCustomDialog(message, title, buttonText);
        }

        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(this.bridgeContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Computes the lowercase hexadecimal MD5 digest of {@code input + "00110011"}
     * used by Website 2 APK Builder Pro license verification (method {@code a(String)}).
     */
    public static final String md5WithSalt(String input) {
        String salted = input + "00110011";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(salted.getBytes());
            byte[] digestBytes = messageDigest.digest();
            StringBuilder hexBuilder = new StringBuilder();
            for (byte b : digestBytes) {
                String hexString = Integer.toHexString(b & 255);
                while (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                hexBuilder.append(hexString);
            }
            return hexBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Checks whether system location services are enabled (method {@code a(Context)}).
     */
    public static boolean isLocationEnabled(Context context) {
        if (Build.VERSION.SDK_INT < 19) {
            return !TextUtils.isEmpty(
                    Settings.Secure.getString(context.getContentResolver(), "location_providers_allowed"));
        }
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode") != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Routes external URI schemes ({@code market://}, {@code tel:}, {@code exit:}, {@code intent://},
     * {@code whatsapp:}, {@code skype:}, {@code geo:}, {@code maps:}, {@code sms:}, {@code mailto:},
     * and {@code #___external}) to Android system intents (method {@code b(String)}).
     */
    private boolean handleExternalSchemeUrl(String url) {
        Intent intent;
        if (url.startsWith("market://")) {
            try {
                this.webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                return true;
            }
        }
        if (url.startsWith("tel:")) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
        } else {
            if (url.startsWith("exit:")) {
                finish();
                return true;
            }
            if (url.startsWith("intent://") && url.contains("scheme=http")) {
                Matcher matcher = Pattern.compile("intent://(.*?)#").matcher(Uri.decode(url));
                if (!matcher.find()) {
                    return false;
                }
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + matcher.group(1)));
            } else if (url.startsWith("whatsapp:")
                    || url.startsWith("skype:")
                    || url.startsWith("geo:0,0?q=")
                    || url.startsWith("maps:")) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            } else if (url.startsWith("sms:") || url.startsWith("mailto:")) {
                intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
            } else {
                if (!url.contains("#___external")) {
                    return false;
                }
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            }
        }
        startActivity(intent);
        return true;
    }

    /**
     * Checks whether the process holds {@code android.permission.<permissionShortName>}
     * (method {@code c(String)}).
     */
    private boolean hasPermission(String permissionShortName) {
        StringBuilder sb = new StringBuilder();
        sb.append("android.permission.");
        sb.append(permissionShortName);
        return checkCallingOrSelfPermission(sb.toString()) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Secondary Website 2 APK Builder Pro license check (method {@code j()}).
     *
     * <p>Compares the second 16 characters of {@code R.string.csum} ({@code A7EB693C4A912815})
     * against {@link #computeDeveloperIdHashSuffix()} ({@code 4AEA01C4CEBA5F57}). Because they do
     * not match in this APK, if more than 30 days have elapsed since {@link #getApkBuildDate()} and
     * the package installer is non-null and not {@code com.google.android.packageinstaller}, this
     * method redirects {@link #targetUrl} to {@code https://websitetoapk.com/apptest/testp.html}.</p>
     */
    private void verifyBuilderLicenseOrRedirect() {
        if (getResources().getString(R.string.csum).toUpperCase().substring(16, 32)
                .equals(computeDeveloperIdHashSuffix())) {
            return;
        }
        long elapsedDays = (new Date().getTime() - getApkBuildDate().getTime()) / 86400000L;
        String installerPackageName = this.activityContext.getPackageManager()
                .getInstallerPackageName(this.activityContext.getPackageName());
        if (elapsedDays <= 30
                || installerPackageName == null
                || installerPackageName.equals("com.google.android.packageinstaller")) {
            return;
        }
        this.targetUrl = "https://websitetoapk.com/apptest/testp.html";
    }

    /**
     * Reads the modification timestamp of {@code classes.dex} inside the installed APK archive,
     * falling back to epoch millisecond {@code 1567092931175L} (2019-08-29T15:35:31.175Z)
     * (method {@code k()}).
     */
    private Date getApkBuildDate() {
        Date fallbackDate = new Date(Long.parseLong("1567092931175"));
        try {
            ZipFile zipFile = new ZipFile(
                    getPackageManager().getApplicationInfo(getPackageName(), 0).sourceDir);
            Date dexDate = new Date(zipFile.getEntry("classes.dex").getTime());
            try {
                zipFile.close();
                return dexDate;
            } catch (Exception unused) {
                return dexDate;
            }
        } catch (Exception unused2) {
            return fallbackDate;
        }
    }

    /**
     * Creates a temporary JPEG file in {@link Environment#DIRECTORY_PICTURES} for camera capture
     * (method {@code l()}).
     */
    private File createTempImageFile() throws IOException {
        return File.createTempFile(
                "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_",
                ".jpg",
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /**
     * Returns the first 16 uppercase hex characters of {@code MD5(packageName + "00110011")}
     * (method {@code m()}).
     */
    private String computePackageHashPrefix() {
        return md5WithSalt(getPackageName()).toUpperCase().substring(0, 16);
    }

    /**
     * Returns the last 16 uppercase hex characters of {@code MD5(devid + packageName + "00110011")}
     * (method {@code n()}).
     */
    private String computeDeveloperIdHashSuffix() {
        return md5WithSalt(getResources().getString(R.string.devid) + getPackageName())
                .toUpperCase().substring(16, 32);
    }

    /**
     * Applies fullscreen window flags or immersive system UI visibility when {@code R.bool.fullscr}
     * is enabled (method {@code a()}).
     */
    public void applyFullscreenMode() {
        int visibilityFlags;
        if (getResources().getBoolean(R.bool.fullscr)) {
            if (Build.VERSION.SDK_INT < 16) {
                getWindow().setFlags(1024, 1024);
                return;
            }
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 19) {
                visibilityFlags = 1796;
            } else if (Build.VERSION.SDK_INT < 19) {
                return;
            } else {
                visibilityFlags = 3332;
            }
            decorView.setSystemUiVisibility(visibilityFlags);
        }
    }

    /**
     * Prints the current WebView document using {@link PrintManager} on Android 4.4+ (API >= 19)
     * with {@link PrintAttributes.MediaSize#ISO_A5} paper size (method {@code a(WebView)}).
     */
    public void printWebViewPage(WebView targetWebView) {
        Context applicationContext;
        String statusMessage;
        if (Build.VERSION.SDK_INT >= 19) {
            PrintManager printManager = (PrintManager) getSystemService("print");
            PrintDocumentAdapter printAdapter = targetWebView.createPrintDocumentAdapter();
            String jobName = getString(R.string.app_name) + " Document";
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
            PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());
            if (printJob.isCompleted()) {
                applicationContext = getApplicationContext();
                statusMessage = "Print Completed";
            } else {
                if (!printJob.isFailed()) {
                    return;
                }
                applicationContext = getApplicationContext();
                statusMessage = "Print Failed";
            }
            Toast.makeText(applicationContext, statusMessage, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Displays a cancelable alert dialog with custom message, title, and button text
     * (method {@code a(String, String, String)}).
     */
    public void showCustomDialog(String message, String title, String buttonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton(buttonText, (DialogInterface.OnClickListener) null);
        builder.setCancelable(true);
        if (((Activity) this.activityContext).isFinishing()) {
            return;
        }
        builder.create().show();
    }

    /**
     * Returns {@code true} on Android 7.0+ (API > 23) where runtime permissions are requested
     * on startup (method {@code b()}).
     */
    protected boolean shouldRequestRuntimePermissions() {
        return Build.VERSION.SDK_INT > 23;
    }

    /**
     * Requests storage, location, microphone, and camera runtime permissions on API >= 23
     * (method {@code c()}). Note that none of these permissions are declared in
     * {@code AndroidManifest.xml}, so Android denies them immediately without prompting.
     */
    @TargetApi(23)
    protected void requestRuntimePermissions() {
        requestPermissions(
                new String[]{
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.RECORD_AUDIO",
                    "android.permission.CAMERA"
                },
                200);
    }

    /**
     * Prompts the user to confirm application exit when {@code R.bool.confexit} is {@code true},
     * or finishes immediately otherwise (method {@code d()}).
     */
    public void confirmOrExitApp() {
        if (getResources().getBoolean(R.bool.confexit)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.cnfExit)
                    .setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            MainActivity.this.finish();
                        }
                    })
                    .create()
                    .show();
        } else {
            finish();
        }
    }

    /**
     * Displays the default "About" dialog showing {@code R.string.aboutTitle} ("About") and
     * {@code R.string.tag} ("Banco Popular de Ahorro | Granma | 2019") (method {@code e()}).
     */
    public void showDefaultAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.tag);
        builder.setTitle(R.string.aboutTitle);
        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) null);
        builder.setCancelable(true);
        if (((Activity) this.activityContext).isFinishing()) {
            return;
        }
        builder.create().show();
    }

    /**
     * Prompts the user to enable GPS in system location settings if location is currently disabled
     * (method {@code f()}).
     */
    public void promptEnableGps() {
        if (isLocationEnabled(this.activityContext)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activityContext);
        builder.setTitle("Enable GPS");
        builder.setMessage("GPS is disabled. Do you want to enable it?");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                MainActivity.this.activityContext.startActivity(
                        new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /**
     * Opens the Google Play Store listing for this application's package name (method {@code g()}).
     */
    public void openPlayStoreListing() {
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + this.activityContext.getPackageName()));
        intent.addFlags(1207959552);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + this.activityContext.getPackageName())));
        }
    }

    /**
     * Returns the device's {@link Settings.Secure#ANDROID_ID} string (method {@code h()}).
     */
    public String getAndroidDeviceId() {
        return Settings.Secure.getString(this.activityContext.getContentResolver(), "android_id");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri[] results;
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT < 21) {
            if (requestCode != 1 || this.legacyUploadMessage == null) {
                return;
            }
            this.legacyUploadMessage.onReceiveValue(
                    (data == null || resultCode != Activity.RESULT_OK) ? null : data.getData());
            this.legacyUploadMessage = null;
            return;
        }
        if (resultCode != Activity.RESULT_OK || requestCode != 1) {
            results = null;
        } else {
            if (this.filePathCallback == null) {
                return;
            }
            if (data != null) {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                } else {
                    results = null;
                }
            } else if (this.cameraPhotoPath != null) {
                results = new Uri[]{Uri.parse(this.cameraPhotoPath)};
            } else {
                results = null;
            }
        }
        this.filePathCallback.onReceiveValue(results);
        this.filePathCallback = null;
    }

    @Override
    public void onBackPressed() {
        if (!(this.doubleBackToExitPressedOnce.booleanValue()
                && getResources().getBoolean(R.bool.dblexit))
                && this.webView.canGoBack()) {
            this.webView.goBack();
            this.doubleBackToExitPressedOnce = true;
        } else {
            confirmOrExitApp();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 200L);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.flag_secure)) {
            getWindow().setFlags(8192, 8192);
        }
        setContentView(R.layout.activity_main);
        this.webView = (WebView) findViewById(R.id.webView);
        this.splashImageView = (ImageView) findViewById(R.id.imageView);
        this.customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
        this.activityContext = this;
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final Boolean openExternalLinksInBrowser = Boolean.valueOf(
                getResources().getBoolean(R.bool.extlink));
        final String expectedPackageHashPrefix = getResources().getString(R.string.csum)
                .toUpperCase().substring(0, 16);

        WebSettings settings = this.webView.getSettings();
        if (shouldRequestRuntimePermissions()) {
            requestRuntimePermissions();
        }
        applyFullscreenMode();
        if (getResources().getBoolean(R.bool.desktopMode)) {
            settings.setUserAgentString(
                    settings.getUserAgentString().replace("Mobile", "").replace("Android", ""));
            settings.setUseWideViewPort(true);
        }
        this.targetUrl = "file:///android_asset/index.html";
        verifyBuilderLicenseOrRedirect();
        try {
            this.targetHost = new URL(this.targetUrl).getHost();
        } catch (Exception unused) {
            this.targetHost = "Error";
        }
        this.webView.setVisibility(View.GONE);
        this.splashImageView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.splashImageView.setVisibility(View.GONE);
                MainActivity.this.webView.setVisibility(View.VISIBLE);
                if (expectedPackageHashPrefix.equals(MainActivity.this.computePackageHashPrefix())) {
                    return;
                }
                MainActivity.this.showCustomDialog(
                        "This app is created using an unauthorized copy of 'Website 2 APK Builder Pro' Software.",
                        "Unauthorized App",
                        "Shame on me!");
            }
        }, getResources().getInteger(R.integer.duration));

        settings.setAppCacheEnabled(getResources().getBoolean(R.bool.CacheEnabled));
        if (getResources().getString(R.string.CacheMode).equals("NoCache")) {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else if (getResources().getString(R.string.CacheMode).equals("HighCache")) {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 19) {
            this.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        settings.setAppCachePath("/data/data" + getPackageName() + "/cache");
        this.webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setSaveFormData(getResources().getBoolean(R.bool.SaveFormData));
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(getApplicationContext().getDir("database", 0).getPath());
        settings.setDomStorageEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        this.webView.setHapticFeedbackEnabled(true);
        this.webView.setHorizontalScrollBarEnabled(getResources().getBoolean(R.bool.ScrollBars));
        this.webView.setVerticalScrollBarEnabled(getResources().getBoolean(R.bool.ScrollBars));
        this.webView.setLongClickable(true);
        if (getResources().getBoolean(R.bool.hideWebView)) {
            this.webView.getSettings().setUserAgentString(
                    this.webView.getSettings().getUserAgentString().replace("wv", ""));
        }
        settings.setGeolocationEnabled(true);
        if (getResources().getBoolean(R.bool.askGPS)) {
            promptEnableGps();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(getResources().getBoolean(R.bool.isDebugMode));
        }
        if (getResources().getBoolean(R.bool.enableJsApi)) {
            this.webView.addJavascriptInterface(new Website2ApkJavascriptBridge(this), "Website2APK");
        }
        this.webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return MainActivity.this.getResources().getBoolean(R.bool.TextSelection);
            }
        });
        settings.setAllowFileAccess(getResources().getBoolean(R.bool.allow_file_access));
        if (Build.VERSION.SDK_INT >= 16) {
            Boolean allowCrossOriginFromFile = Boolean.valueOf(
                    getResources().getBoolean(R.bool.allow_x_origin_from_file));
            settings.setAllowFileAccessFromFileURLs(allowCrossOriginFromFile.booleanValue());
            settings.setAllowUniversalAccessFromFileURLs(allowCrossOriginFromFile.booleanValue());
        }
        this.webView.loadUrl(this.targetUrl);
        this.webView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (MainActivity.this.webView.getRootView().getHeight()
                                - MainActivity.this.webView.getHeight() < 100) {
                            MainActivity.this.applyFullscreenMode();
                        }
                    }
                });
        this.webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(getResources().getBoolean(R.bool.gestureZoom));
        settings.setDisplayZoomControls(getResources().getBoolean(R.bool.Zoom));

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptCookie();
        CookieSyncManager.createInstance(this.activityContext);
        if (Build.VERSION.SDK_INT > 21) {
            cookieManager.setAcceptThirdPartyCookies(this.webView, true);
        }
        if (savedInstanceState != null) {
            this.webView.restoreState(savedInstanceState);
        }

        this.webChromeClient = new WebChromeClient() {
            private View videoProgressView;

            @Override
            public View getVideoLoadingProgressView() {
                if (this.videoProgressView == null) {
                    this.videoProgressView = LayoutInflater.from(MainActivity.this)
                            .inflate(R.layout.video_progress, (ViewGroup) null);
                }
                return this.videoProgressView;
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                onConsoleMessage(
                        consoleMessage.message(),
                        consoleMessage.lineNumber(),
                        consoleMessage.sourceId());
                return true;
            }

            @Override
            public void onExceededDatabaseQuota(
                    String url,
                    String databaseIdentifier,
                    long quota,
                    long estimatedDatabaseSize,
                    long totalQuota,
                    WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(
                    String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                if (MainActivity.this.customView == null) {
                    return;
                }
                MainActivity.this.webView.setVisibility(View.VISIBLE);
                MainActivity.this.customViewContainer.setVisibility(View.GONE);
                MainActivity.this.customView.setVisibility(View.GONE);
                MainActivity.this.getWindow().clearFlags(128);
                MainActivity.this.customViewContainer.removeView(MainActivity.this.customView);
                MainActivity.this.customViewCallback.onCustomViewHidden();
                MainActivity.this.customView = null;
            }

            @Override
            public boolean onJsAlert(
                    WebView view, String url, String message, final JsResult jsResult) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(MainActivity.this.getResources().getString(R.string.app_name))
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                jsResult.confirm();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            @Override
            public boolean onJsConfirm(
                    WebView view, String url, String message, final JsResult jsResult) {
                new AlertDialog.Builder(MainActivity.this.activityContext)
                        .setTitle(MainActivity.this.getResources().getString(R.string.app_name))
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                jsResult.confirm();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                jsResult.cancel();
                            }
                        })
                        .create()
                        .show();
                return true;
            }

            @Override
            public void onPermissionRequest(PermissionRequest permissionRequest) {
                if (Build.VERSION.SDK_INT > 21) {
                    permissionRequest.grant(permissionRequest.getResources());
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100 && MainActivity.this.progressBar.getVisibility() == View.GONE) {
                    MainActivity.this.progressBar.setVisibility(View.VISIBLE);
                }
                MainActivity.this.progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    MainActivity.this.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onShowCustomView(
                    View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
                onShowCustomView(view, callback);
            }

            @Override
            public void onShowCustomView(
                    View view, WebChromeClient.CustomViewCallback callback) {
                if (MainActivity.this.customView != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                MainActivity.this.customView = view;
                MainActivity.this.webView.setVisibility(View.GONE);
                MainActivity.this.customViewContainer.setVisibility(View.VISIBLE);
                MainActivity.this.getWindow().addFlags(128);
                MainActivity.this.customViewContainer.addView(view);
                MainActivity.this.customViewCallback = callback;
            }

            @Override
            public boolean onShowFileChooser(
                    WebView view,
                    ValueCallback<Uri[]> filePathCallbackParam,
                    WebChromeClient.FileChooserParams fileChooserParams) {
                File photoFile;
                if (MainActivity.this.filePathCallback != null) {
                    MainActivity.this.filePathCallback.onReceiveValue(null);
                }
                MainActivity.this.filePathCallback = filePathCallbackParam;
                Intent captureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                if (captureIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
                    try {
                        photoFile = MainActivity.this.createTempImageFile();
                        try {
                            captureIntent.putExtra("PhotoPath", MainActivity.this.cameraPhotoPath);
                        } catch (Exception e) {
                            Log.e(MainActivity.TAG, "Image file creation failed", e);
                        }
                    } catch (IOException e2) {
                        Log.e(MainActivity.TAG, "Image file creation failed", e2);
                        photoFile = null;
                    }
                    if (photoFile != null) {
                        MainActivity.this.cameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        captureIntent.putExtra("output", Uri.fromFile(photoFile));
                    } else {
                        captureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
                Intent[] intentArray = captureIntent != null
                        ? new Intent[]{captureIntent, new Intent("android.media.action.VIDEO_CAPTURE")}
                        : new Intent[0];
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose File");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                MainActivity.this.startActivityForResult(chooserIntent, 1);
                return true;
            }
        };
        this.webView.setWebChromeClient(this.webChromeClient);

        this.webView.setWebViewClient(new WebViewClient() {
            Boolean shouldHandleExternally(String url) {
                if (openExternalLinksInBrowser.booleanValue()) {
                    return Boolean.valueOf(MainActivity.this.handleExternalSchemeUrl(url));
                }
                if (MainActivity.this.handleExternalSchemeUrl(url)) {
                    return true;
                }
                if (url.contains(MainActivity.this.targetHost)) {
                    return false;
                }
                MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                CookieSyncManager.getInstance().sync();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(
                    WebView view, int errorCode, String description, String failingUrl) {
                if (MainActivity.this.getResources().getBoolean(R.bool.isDebugMode)) {
                    Toast.makeText(
                            MainActivity.this.getApplicationContext(),
                            errorCode + description + failingUrl,
                            Toast.LENGTH_SHORT).show();
                }
                view.loadUrl("file:///android_asset/404r.html");
            }

            @Override
            public void onReceivedSslError(
                    WebView view, SslErrorHandler sslErrorHandler, SslError sslError) {
                if (MainActivity.this.getResources().getBoolean(R.bool.isDebugMode)) {
                    Toast.makeText(
                            MainActivity.this.getApplicationContext(),
                            sslError.getPrimaryError(),
                            Toast.LENGTH_SHORT).show();
                }
                if (MainActivity.this.getResources().getBoolean(R.bool.ignoreSsl)) {
                    sslErrorHandler.proceed();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                if (shouldHandleExternally(url).booleanValue()) {
                    return true;
                }
                view.loadUrl(url);
                return false;
            }
        });

        this.webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(
                    String url,
                    String userAgent,
                    String contentDisposition,
                    String mimeType,
                    long contentLength) {
                if (!MainActivity.this.hasPermission("WRITE_EXTERNAL_STORAGE")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    MainActivity.this.startActivity(intent);
                    return;
                }
                try {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.setMimeType(mimeType);
                    request.addRequestHeader(
                            "cookie", CookieManager.getInstance().getCookie(url));
                    request.addRequestHeader("User-Agent", userAgent);
                    request.setDescription("Downloading file...");
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            URLUtil.guessFileName(url, contentDisposition, mimeType));
                    ((DownloadManager) MainActivity.this.getSystemService("download"))
                            .enqueue(request);
                    Toast.makeText(
                            MainActivity.this.getApplicationContext(),
                            "Downloading File",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(
                            MainActivity.this.getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if (itemId == R.id.action_exit) {
            finish();
            return true;
        }
        if (itemId == R.id.action_tag) {
            showDefaultAboutDialog();
            return true;
        }
        if (itemId != R.id.action_share) {
            return super.onOptionsItemSelected(menuItem);
        }
        shareIntent.setType("text/plain");
        shareIntent.putExtra(
                Intent.EXTRA_SUBJECT, getResources().getString(R.string.sharesubject));
        shareIntent.putExtra(
                Intent.EXTRA_TEXT, getResources().getString(R.string.sharetext));
        startActivity(Intent.createChooser(shareIntent, "Share via"));
        return true;
    }

    @Override
    public void onPause() {
        CookieSyncManager.getInstance().stopSync();
        this.webView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        applyFullscreenMode();
        CookieSyncManager.getInstance().startSync();
        this.webView.onResume();
        super.onResume();
    }
}
