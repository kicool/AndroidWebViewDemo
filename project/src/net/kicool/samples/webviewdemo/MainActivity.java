package net.kicool.samples.webviewdemo;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String URL = "file:///android_asset/index.html";

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private WebView webView;
	private TextView textView;
	private EditText urlField;
	private Button button;
	private Button buttonBack;

	/** Called when the activity is first created. */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		urlField = (EditText) findViewById(R.id.url);
		textView = (TextView) findViewById(R.id.callback);
		button = (Button) findViewById(R.id.go_button);
		buttonBack = (Button) findViewById(R.id.back_button);

		// Setup click listener
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				openURL();
			}
		});

		// Setup click listener
		buttonBack.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				webView.loadUrl(URL);
			}
		});

		// Setup key listener
		urlField.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					openURL();
					return true;
				} else {
					return false;
				}
			}
		});

		// Create reference to UI elements
		webView = (WebView) findViewById(R.id.webview_compontent);

		// workaround so that the default browser doesn't take over
		webView.setWebViewClient(new WebViewClientExtension());

		/**
		 * 设置WebView的属性，此时可以去执行JavaScript脚本
		 */
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSaveFormData(false);
		webSettings.setSavePassword(false);
		webSettings.setSupportZoom(false);

		webView.loadUrl(URL);
	}

	// Give the host application a chance to take over the control when a new
	// url is about to be loaded in the current WebView. If WebViewClient is not
	// provided, by default WebView will ask Activity Manager to choose the
	// proper handler for the url. If WebViewClient is provided, return true
	// means the host application handles the url, while return false means the
	// current WebView handles the url.
	private class WebViewClientExtension extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("Titan", "Load URL:" + url);
			String scheme = Uri.parse(url).getScheme();
			if (scheme.equalsIgnoreCase("mywords")) {
				textView.setText(url);
				return true;
			}

			return false;
		}
	}

	/** Opens the URL in a browser */
	private void openURL() {
		webView.loadUrl(urlField.getText().toString());
	}
}
