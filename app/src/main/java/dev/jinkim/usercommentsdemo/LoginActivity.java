package dev.jinkim.usercommentsdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);

        mActivity = this;
        app = Singleton.getInstance();

        webView = (WebView) findViewById(R.id.wv_gt_login);
        webView.clearCache(true);
        webView.clearHistory();
        webView.loadUrl(LOGINURL);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains("usercomments://")) {
                    view.loadUrl(url);

                } else {
                    // extract the data (session name and id) from the redirect url
                    String[] splitParams = url.split("\\?")[1].split("&");
                    String sessionName = splitParams[0].split("=")[1];
                    String sessionId = splitParams[1].split("=")[1];

                    app.setSessionName(sessionName);
                    app.setSessionId(sessionId);

                    // TODO: Remove this for production of your app
                    Log.d(TAG, "GT Login successful: " + sessionName + "&" + sessionId);

                    Intent in = new Intent(mActivity, MainActivity.class);
                    startActivity(in);
                }

                return true;
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        LoginActivity.this);

                alertDialogBuilder.setTitle("Refresh page");
                alertDialogBuilder
                        .setMessage("Login page has failed to load. Would you like to try again?")
                        .setCancelable(false)
                        .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                webView.reload();
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


        webView.getSettings().setJavaScriptEnabled(true);

//        setProgressBarIndeterminateVisibility(true);
//        setProgressBarVisibility(true);


//        final Activity activity = this;
//        webView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                // Activities and WebViews measure progress with different scales.
//                // The progress meter will automatically disappear when we reach 100%
//                activity.setProgress(progress * 100);
//            }
//        });

    }


}
