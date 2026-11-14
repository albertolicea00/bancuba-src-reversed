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

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends Activity {
    private static final String i = "MainActivity";
    WebView a;
    ImageView b;
    String c;
    String d;
    ProgressBar e;
    WebChromeClient f;
    Context g;
    Boolean h = false;
    private String j;
    private ValueCallback<Uri> k;
    private ValueCallback<Uri[]> l;
    private FrameLayout m;
    private WebChromeClient.CustomViewCallback n;
    private View o;

    public class a {
        Context a;

        a(Context context) {
            this.a = context;
        }

        @JavascriptInterface
        public void askEnableGPS() {
            MainActivity.this.f();
        }

        @JavascriptInterface
        public void clickMenuButton() {
            MainActivity.this.openOptionsMenu();
        }

        @JavascriptInterface
        public void exitApp() {
            MainActivity.this.d();
        }

        @JavascriptInterface
        public int getAppVersionCode() {
            try {
                return MainActivity.this.getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @JavascriptInterface
        public String getAppVersionName() {
            try {
                return MainActivity.this.getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "Error Fetching Version Info";
            }
        }

        @JavascriptInterface
        public String getUniqueDeviceID() {
            return MainActivity.this.h();
        }

        @JavascriptInterface
        public void openExternal(String str) {
            MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }

        @JavascriptInterface
        public void printPage() {
            MainActivity.this.a.post(new Runnable() { // from class: cu.bpa.bancuba.MainActivity.a.2
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.a(MainActivity.this.a);
                }
            });
        }

        @JavascriptInterface
        public void rateUs() {
            MainActivity.this.g();
        }

        @JavascriptInterface
        public void refreshPage() {
            MainActivity.this.a.post(new Runnable() { // from class: cu.bpa.bancuba.MainActivity.a.1
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.a.reload();
                }
            });
        }

        @JavascriptInterface
        public void shareIntent() {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", this.a.getResources().getString(R.string.sharesubject));
            intent.putExtra("android.intent.extra.TEXT", this.a.getResources().getString(R.string.sharetext));
            this.a.startActivity(Intent.createChooser(intent, "Share App via"));
        }

        @JavascriptInterface
        public void showAboutDialog() {
            MainActivity.this.e();
        }

        @JavascriptInterface
        public void showAboutDialog(String str, String str2, String str3) {
            MainActivity.this.a(str, str2, str3);
        }

        @JavascriptInterface
        public void showToast(String str) {
            Toast.makeText(this.a, str, 0).show();
        }
    }

    public static final String a(String str) {
        String str2 = str + "00110011";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str2.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bArrDigest) {
                String hexString = Integer.toHexString(b & 255);
                while (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean a(Context context) {
        if (Build.VERSION.SDK_INT < 19) {
            return !TextUtils.isEmpty(Settings.Secure.getString(context.getContentResolver(), "location_providers_allowed"));
        }
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode") != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) {
        Intent intent;
        if (str.startsWith("market://")) {
            try {
                this.a.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                return true;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                return true;
            }
        }
        if (str.startsWith("tel:")) {
            intent = new Intent("android.intent.action.DIAL", Uri.parse(str));
        } else {
            if (str.startsWith("exit:")) {
                finish();
                return true;
            }
            if (str.startsWith("intent://") && str.contains("scheme=http")) {
                Matcher matcher = Pattern.compile("intent://(.*?)#").matcher(Uri.decode(str));
                if (!matcher.find()) {
                    return false;
                }
                intent = new Intent("android.intent.action.VIEW", Uri.parse("http://" + matcher.group(1)));
            } else if (str.startsWith("whatsapp:") || str.startsWith("skype:") || str.startsWith("geo:0,0?q=") || str.startsWith("maps:")) {
                intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            } else if (str.startsWith("sms:") || str.startsWith("mailto:")) {
                intent = new Intent("android.intent.action.SENDTO", Uri.parse(str));
            } else {
                if (!str.contains("#___external")) {
                    return false;
                }
                intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            }
        }
        startActivity(intent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("android.permission.");
        sb.append(str);
        return checkCallingOrSelfPermission(sb.toString()) == 0;
    }

    private void j() {
        if (getResources().getString(R.string.csum).toUpperCase().substring(16, 32).equals(n())) {
            return;
        }
        long time = (new Date().getTime() - k().getTime()) / 86400000;
        String installerPackageName = this.g.getPackageManager().getInstallerPackageName(this.g.getPackageName());
        if (time <= 30 || installerPackageName == null || installerPackageName.equals("com.google.android.packageinstaller")) {
            return;
        }
        this.c = "https://websitetoapk.com/apptest/testp.html";
    }

    private Date k() {
        Date date = new Date(Long.parseLong("1567092931175"));
        try {
            ZipFile zipFile = new ZipFile(getPackageManager().getApplicationInfo(getPackageName(), 0).sourceDir);
            Date date2 = new Date(zipFile.getEntry("classes.dex").getTime());
            try {
                zipFile.close();
                return date2;
            } catch (Exception unused) {
                return date2;
            }
        } catch (Exception unused2) {
            return date;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File l() {
        return File.createTempFile("img_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_", ".jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String m() {
        return a(getPackageName()).toUpperCase().substring(0, 16);
    }

    private String n() {
        return a(getResources().getString(R.string.devid) + getPackageName()).toUpperCase().substring(16, 32);
    }

    public void a() {
        int i2;
        if (getResources().getBoolean(R.bool.fullscr)) {
            if (Build.VERSION.SDK_INT < 16) {
                getWindow().setFlags(1024, 1024);
                return;
            }
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 19) {
                i2 = 1796;
            } else if (Build.VERSION.SDK_INT < 19) {
                return;
            } else {
                i2 = 3332;
            }
            decorView.setSystemUiVisibility(i2);
        }
    }

    public void a(WebView webView) {
        Context applicationContext;
        String str;
        if (Build.VERSION.SDK_INT >= 19) {
            PrintManager printManager = (PrintManager) getSystemService("print");
            PrintDocumentAdapter printDocumentAdapterCreatePrintDocumentAdapter = webView.createPrintDocumentAdapter();
            String str2 = getString(R.string.app_name) + " Document";
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
            PrintJob printJobPrint = printManager.print(str2, printDocumentAdapterCreatePrintDocumentAdapter, builder.build());
            if (printJobPrint.isCompleted()) {
                applicationContext = getApplicationContext();
                str = "Print Completed";
            } else {
                if (!printJobPrint.isFailed()) {
                    return;
                }
                applicationContext = getApplicationContext();
                str = "Print Failed";
            }
            Toast.makeText(applicationContext, str, 1).show();
        }
    }

    public void a(String str, String str2, String str3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str);
        builder.setTitle(str2);
        builder.setPositiveButton(str3, (DialogInterface.OnClickListener) null);
        builder.setCancelable(true);
        if (((Activity) this.g).isFinishing()) {
            return;
        }
        builder.create().show();
    }

    protected boolean b() {
        return Build.VERSION.SDK_INT > 23;
    }

    @TargetApi(23)
    protected void c() {
        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.RECORD_AUDIO", "android.permission.CAMERA"}, 200);
    }

    public void d() {
        if (getResources().getBoolean(R.bool.confexit)) {
            new AlertDialog.Builder(this).setTitle(R.string.cnfExit).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: cu.bpa.bancuba.MainActivity.9
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    MainActivity.this.finish();
                }
            }).create().show();
        } else {
            finish();
        }
    }

    public void e() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.tag);
        builder.setTitle(R.string.aboutTitle);
        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) null);
        builder.setCancelable(true);
        if (((Activity) this.g).isFinishing()) {
            return;
        }
        builder.create().show();
    }

    public void f() {
        if (a(this.g)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.g);
        builder.setTitle("Enable GPS");
        builder.setMessage("GPS is disabled. Do you want to enable it?");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() { // from class: cu.bpa.bancuba.MainActivity.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                MainActivity.this.g.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: cu.bpa.bancuba.MainActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public void g() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.g.getPackageName()));
        intent.addFlags(1207959552);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + this.g.getPackageName())));
        }
    }

    public String h() {
        return Settings.Secure.getString(this.g.getContentResolver(), "android_id");
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0037  */
    @Override // android.app.Activity
    protected void onActivityResult(int i2, int i3, Intent intent) {
        Uri[] uriArr;
        super.onActivityResult(i2, i3, intent);
        if (Build.VERSION.SDK_INT < 21) {
            if (i2 != 1 || this.k == null) {
                return;
            }
            this.k.onReceiveValue((intent == null || i3 != -1) ? null : intent.getData());
            this.k = null;
            return;
        }
        if (i3 != -1 || i2 != 1) {
            uriArr = null;
        } else {
            if (this.l == null) {
                return;
            }
            if (intent != null) {
                String dataString = intent.getDataString();
                if (dataString != null) {
                    uriArr = new Uri[]{Uri.parse(dataString)};
                } else {
                    uriArr = null;
                }
            } else if (this.j != null) {
                uriArr = new Uri[]{Uri.parse(this.j)};
            } else {
                uriArr = null;
            }
        }
        this.l.onReceiveValue(uriArr);
        this.l = null;
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (!(this.h.booleanValue() && getResources().getBoolean(R.bool.dblexit)) && this.a.canGoBack()) {
            this.a.goBack();
            this.h = true;
        } else {
            d();
        }
        new Handler().postDelayed(new Runnable() { // from class: cu.bpa.bancuba.MainActivity.8
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.h = false;
            }
        }, 200L);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getResources().getBoolean(R.bool.flag_secure)) {
            getWindow().setFlags(8192, 8192);
        }
        setContentView(R.layout.activity_main);
        this.a = (WebView) findViewById(R.id.webView);
        this.b = (ImageView) findViewById(R.id.imageView);
        this.m = (FrameLayout) findViewById(R.id.customViewContainer);
        this.g = this;
        this.e = (ProgressBar) findViewById(R.id.progressBar);
        final Boolean boolValueOf = Boolean.valueOf(getResources().getBoolean(R.bool.extlink));
        final String strSubstring = getResources().getString(R.string.csum).toUpperCase().substring(0, 16);
        WebSettings settings = this.a.getSettings();
        if (b()) {
            c();
        }
        a();
        if (getResources().getBoolean(R.bool.desktopMode)) {
            settings.setUserAgentString(settings.getUserAgentString().replace("Mobile", "").replace("Android", ""));
            settings.setUseWideViewPort(true);
        }
        this.c = "file:///android_asset/index.html";
        j();
        try {
            this.d = new URL(this.c).getHost();
        } catch (Exception unused) {
            this.d = "Error";
        }
        this.a.setVisibility(8);
        this.b.setVisibility(0);
        new Handler().postDelayed(new Runnable() { // from class: cu.bpa.bancuba.MainActivity.1
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.b.setVisibility(8);
                MainActivity.this.a.setVisibility(0);
                if (strSubstring.equals(MainActivity.this.m())) {
                    return;
                }
                MainActivity.this.a("This app is created using an unauthorized copy of 'Website 2 APK Builder Pro' Software.", "Unauthorized App", "Shame on me!");
            }
        }, getResources().getInteger(R.integer.duration));
        settings.setAppCacheEnabled(getResources().getBoolean(R.bool.CacheEnabled));
        if (getResources().getString(R.string.CacheMode).equals("NoCache")) {
            settings.setCacheMode(2);
        } else if (getResources().getString(R.string.CacheMode).equals("HighCache")) {
            settings.setCacheMode(1);
        } else {
            settings.setCacheMode(-1);
        }
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.setLayerType(2, null);
        }
        settings.setAppCachePath("/data/data" + getPackageName() + "/cache");
        this.a.setScrollBarStyle(33554432);
        settings.setSaveFormData(getResources().getBoolean(R.bool.SaveFormData));
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(getApplicationContext().getDir("database", 0).getPath());
        settings.setDomStorageEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        this.a.setHapticFeedbackEnabled(true);
        this.a.setHorizontalScrollBarEnabled(getResources().getBoolean(R.bool.ScrollBars));
        this.a.setVerticalScrollBarEnabled(getResources().getBoolean(R.bool.ScrollBars));
        this.a.setLongClickable(true);
        if (getResources().getBoolean(R.bool.hideWebView)) {
            this.a.getSettings().setUserAgentString(this.a.getSettings().getUserAgentString().replace("wv", ""));
        }
        settings.setGeolocationEnabled(true);
        if (getResources().getBoolean(R.bool.askGPS)) {
            f();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(getResources().getBoolean(R.bool.isDebugMode));
        }
        if (getResources().getBoolean(R.bool.enableJsApi)) {
            this.a.addJavascriptInterface(new a(this), "Website2APK");
        }
        this.a.setOnLongClickListener(new View.OnLongClickListener() { // from class: cu.bpa.bancuba.MainActivity.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return MainActivity.this.getResources().getBoolean(R.bool.TextSelection);
            }
        });
        settings.setAllowFileAccess(getResources().getBoolean(R.bool.allow_file_access));
        if (Build.VERSION.SDK_INT >= 16) {
            Boolean boolValueOf2 = Boolean.valueOf(getResources().getBoolean(R.bool.allow_x_origin_from_file));
            settings.setAllowFileAccessFromFileURLs(boolValueOf2.booleanValue());
            settings.setAllowUniversalAccessFromFileURLs(boolValueOf2.booleanValue());
        }
        this.a.loadUrl(this.c);
        this.a.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: cu.bpa.bancuba.MainActivity.4
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (MainActivity.this.a.getRootView().getHeight() - MainActivity.this.a.getHeight() < 100) {
                    MainActivity.this.a();
                }
            }
        });
        this.a.setScrollBarStyle(33554432);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(getResources().getBoolean(R.bool.gestureZoom));
        settings.setDisplayZoomControls(getResources().getBoolean(R.bool.Zoom));
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptCookie();
        CookieSyncManager.createInstance(this.g);
        if (Build.VERSION.SDK_INT > 21) {
            cookieManager.setAcceptThirdPartyCookies(this.a, true);
        }
        if (bundle != null) {
            this.a.restoreState(bundle);
        }
        this.f = new WebChromeClient() { // from class: cu.bpa.bancuba.MainActivity.5
            private View b;

            @Override // android.webkit.WebChromeClient
            public View getVideoLoadingProgressView() {
                if (this.b == null) {
                    this.b = LayoutInflater.from(MainActivity.this).inflate(R.layout.video_progress, (ViewGroup) null);
                }
                return this.b;
            }

            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                onConsoleMessage(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId());
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(j2 * 2);
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
                callback.invoke(str, true, false);
            }

            @Override // android.webkit.WebChromeClient
            public void onHideCustomView() {
                super.onHideCustomView();
                if (MainActivity.this.o == null) {
                    return;
                }
                MainActivity.this.a.setVisibility(0);
                MainActivity.this.m.setVisibility(8);
                MainActivity.this.o.setVisibility(8);
                MainActivity.this.getWindow().clearFlags(128);
                MainActivity.this.m.removeView(MainActivity.this.o);
                MainActivity.this.n.onCustomViewHidden();
                MainActivity.this.o = null;
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
                new AlertDialog.Builder(MainActivity.this).setTitle(MainActivity.this.getResources().getString(R.string.app_name)).setMessage(str2).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: cu.bpa.bancuba.MainActivity.5.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        jsResult.confirm();
                    }
                }).setCancelable(false).create().show();
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
                new AlertDialog.Builder(MainActivity.this.g).setTitle(MainActivity.this.getResources().getString(R.string.app_name)).setMessage(str2).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: cu.bpa.bancuba.MainActivity.5.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        jsResult.confirm();
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: cu.bpa.bancuba.MainActivity.5.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        jsResult.cancel();
                    }
                }).create().show();
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public void onPermissionRequest(PermissionRequest permissionRequest) {
                if (Build.VERSION.SDK_INT > 21) {
                    permissionRequest.grant(permissionRequest.getResources());
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i2) {
                if (i2 < 100 && MainActivity.this.e.getVisibility() == 8) {
                    MainActivity.this.e.setVisibility(0);
                }
                MainActivity.this.e.setProgress(i2);
                if (i2 == 100) {
                    MainActivity.this.e.setVisibility(8);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onShowCustomView(View view, int i2, WebChromeClient.CustomViewCallback customViewCallback) {
                onShowCustomView(view, customViewCallback);
            }

            @Override // android.webkit.WebChromeClient
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                if (MainActivity.this.o != null) {
                    customViewCallback.onCustomViewHidden();
                    return;
                }
                MainActivity.this.o = view;
                MainActivity.this.a.setVisibility(8);
                MainActivity.this.m.setVisibility(0);
                MainActivity.this.getWindow().addFlags(128);
                MainActivity.this.m.addView(view);
                MainActivity.this.n = customViewCallback;
            }

            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                File fileL;
                if (MainActivity.this.l != null) {
                    MainActivity.this.l.onReceiveValue(null);
                }
                MainActivity.this.l = valueCallback;
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                if (intent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
                    try {
                        fileL = MainActivity.this.l();
                        try {
                            intent.putExtra("PhotoPath", MainActivity.this.j);
                        } catch (IOException e) {
                            e = e;
                            Log.e(MainActivity.i, "Image file creation failed", e);
                        }
                    } catch (IOException e2) {
                        e = e2;
                        fileL = null;
                    }
                    if (fileL != null) {
                        MainActivity.this.j = "file:" + fileL.getAbsolutePath();
                        intent.putExtra("output", Uri.fromFile(fileL));
                    } else {
                        intent = null;
                    }
                }
                Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
                intent2.addCategory("android.intent.category.OPENABLE");
                intent2.setType("*/*");
                Intent[] intentArr = intent != null ? new Intent[]{intent, new Intent("android.media.action.VIDEO_CAPTURE")} : new Intent[0];
                Intent intent3 = new Intent("android.intent.action.CHOOSER");
                intent3.putExtra("android.intent.extra.INTENT", intent2);
                intent3.putExtra("android.intent.extra.TITLE", "Choose File");
                intent3.putExtra("android.intent.extra.INITIAL_INTENTS", intentArr);
                MainActivity.this.startActivityForResult(intent3, 1);
                return true;
            }
        };
        this.a.setWebChromeClient(this.f);
        this.a.setWebViewClient(new WebViewClient() { // from class: cu.bpa.bancuba.MainActivity.6
            Boolean a(String str) {
                if (boolValueOf.booleanValue()) {
                    return Boolean.valueOf(MainActivity.this.b(str));
                }
                if (MainActivity.this.b(str)) {
                    return true;
                }
                if (str.contains(MainActivity.this.d)) {
                    return false;
                }
                MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                return true;
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                CookieSyncManager.getInstance().sync();
                super.onPageFinished(webView, str);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, int i2, String str, String str2) {
                if (MainActivity.this.getResources().getBoolean(R.bool.isDebugMode)) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), i2 + str + str2, 0).show();
                }
                webView.loadUrl("file:///android_asset/404r.html");
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                if (MainActivity.this.getResources().getBoolean(R.bool.isDebugMode)) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), sslError.getPrimaryError(), 0).show();
                }
                if (MainActivity.this.getResources().getBoolean(R.bool.ignoreSsl)) {
                    sslErrorHandler.proceed();
                }
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                super.shouldOverrideUrlLoading(webView, str);
                if (a(str).booleanValue()) {
                    return true;
                }
                webView.loadUrl(str);
                return false;
            }
        });
        this.a.setDownloadListener(new DownloadListener() { // from class: cu.bpa.bancuba.MainActivity.7
            @Override // android.webkit.DownloadListener
            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                if (!MainActivity.this.c("WRITE_EXTERNAL_STORAGE")) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(str));
                    MainActivity.this.startActivity(intent);
                    return;
                }
                try {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
                    request.setMimeType(str4);
                    request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(str));
                    request.addRequestHeader("User-Agent", str2);
                    request.setDescription("Downloading file...");
                    request.setTitle(URLUtil.guessFileName(str, str3, str4));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(1);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(str, str3, str4));
                    ((DownloadManager) MainActivity.this.getSystemService("download")).enqueue(request);
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Downloading File", 1).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), e.getMessage(), 0).show();
                }
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        Intent intent = new Intent("android.intent.action.SEND");
        if (itemId == R.id.action_exit) {
            finish();
            return true;
        }
        if (itemId == R.id.action_tag) {
            e();
            return true;
        }
        if (itemId != R.id.action_share) {
            return super.onOptionsItemSelected(menuItem);
        }
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.sharesubject));
        intent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.sharetext));
        startActivity(Intent.createChooser(intent, "Share via"));
        return true;
    }

    @Override // android.app.Activity
    public void onPause() {
        CookieSyncManager.getInstance().stopSync();
        this.a.onPause();
        super.onPause();
    }

    @Override // android.app.Activity
    public void onResume() {
        a();
        CookieSyncManager.getInstance().startSync();
        this.a.onResume();
        super.onResume();
    }
}
