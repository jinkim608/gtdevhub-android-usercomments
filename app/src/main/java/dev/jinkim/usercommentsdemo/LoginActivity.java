package dev.jinkim.usercommentsdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alertdialogpro.AlertDialogPro;

/**
 * Created by Jin on 2/3/15.
 */
public class LoginActivity extends Activity {

    public static final String LOGINURL = "http://dev.m.gatech.edu/login?url=usercomments://loggedin&sessionTransfer=window";
    private static final String TAG = "LoginActivity";

    private LoginActivity mActivity;
    private WebView webView;
    private Singleton app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mActivity = this;
        app = Singleton.getInstance();

        /* we will use webview to have user login on login.gatech */
        webView = (WebView) findViewById(R.id.wv_gt_login);
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        webView.loadUrl(LOGINURL);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // check if the redirect url contains the session info
                if (url.contains("usercomments://") && url.contains("sessionId")) {

                    // parse the data if this redirect url contains sessionName and sessionId
                    String[] splitParams = url.split("\\?")[1].split("&");
                    String sessionName = splitParams[0].split("=")[1];
                    String sessionId = splitParams[1].split("=")[1];

                    // save these in the app state
                    app.setSessionName(sessionName);
                    app.setSessionId(sessionId);

                    // TODO: Remove this for production of your app
                    Log.d(TAG, "### GT Login successful: " + sessionName + "&" + sessionId);

                    Intent in = new Intent(mActivity, MainActivity.class);
                    startActivity(in);
                    finish();

                } else {
                    // load the redirect url
                    Log.d(TAG, "### Redirect URL: " + url);
                    view.loadUrl(url);
                }

                return true;
            }

            public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {
                AlertDialogPro.Builder builder = new AlertDialogPro.Builder(mActivity);
                builder.setMessage("Failed to load the login page")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view.reload();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
    }
}