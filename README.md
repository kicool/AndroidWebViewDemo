AndroidWebViewDemo
==================

AndroidWebViewDemo

Demo WebView callback to Java Native. 

Androids WebView class provides a method called shouldOverrideUrlLoading to intercept the loading of the requested URLs. It means any url request string will be passed to this java native function. We defeine a ptotocol. Use a special scheme (mywords://) to indentify the real callback. For the web part, it's really simple. Just make all information into a string to form a uri, then invoke the request in a good way after user subscriber. Like this in project/assets/index.html

	<a href="mywords://ef.com/callback/v1/status=0">Subcribe Failed</a>
	<a href="mywords://ef.com/callback/v1/status=1&msisdn=525544932016&token=abcdefg">Subcribe Success</a>

The native part will get all the url string requested in this web view, filter the scheme(mywords://) and get the information. Let other urls do normal request. Like in project/src/net/kicool/samples/webviewdemo/MainActivity.java

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

In this demo, after clicking subscribing links, callback is triggered and be displayed on a textview(native part). Other url you input in the edit text box, will be ignored by native part if not the scheme(mywords://).

This demo can be used as a test project for our integration work.
This is a quick demo for technical logic, so information kind and encoding issue need be discussed later. How and where invoke the url request need be decided by web developer.
