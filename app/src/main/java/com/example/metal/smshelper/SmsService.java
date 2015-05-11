package com.example.metal.smshelper;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by metal on 09.05.15.
 */
public class SmsService extends IntentService
{
	public SmsService() {
		super("sms");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String body = intent.getExtras().getString("body");
		String sender = intent.getExtras().getString("sender");
		String timestamp = intent.getExtras().getString("timestamp");
		String url = intent.getExtras().getString("url");
		sendNotification(url, sender, body, timestamp);
	}

	private void sendNotification(String url, String sender, String body, String timestamp) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("sender", sender));
			nameValuePairs.add(new BasicNameValuePair("body", body));
			nameValuePairs.add(new BasicNameValuePair("timestamp", timestamp));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);


			StringBuilder sb = new StringBuilder();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}
			catch (Exception e) { e.printStackTrace(); }


			Log.i("tag", "finalResult " + sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
