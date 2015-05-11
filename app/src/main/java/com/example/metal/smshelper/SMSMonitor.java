package com.example.metal.smshelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by metal on 09.05.15.
 */
public class SMSMonitor extends BroadcastReceiver
{
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		final SharedPreferences settings = context.getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
		boolean enabledSetting = settings.getBoolean("enabled", false);
		String urlSetting = settings.getString("url", "");

		if (!enabledSetting) {
			return ;
		}

		if (urlSetting.isEmpty() || !urlSetting.startsWith("http")) {
			return ;
		}

		if (intent != null && intent.getAction() != null && ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
			Object[] pduArray = (Object[]) intent.getExtras().get("pdus");

			if (pduArray.length < 1) {
				return;
			}

			SmsMessage[] messages = new SmsMessage[pduArray.length];

			for (int i = 0; i < pduArray.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
			}

			String sms_from = messages[0].getDisplayOriginatingAddress();
			String timestamp = ((Long) messages[0].getTimestampMillis()).toString();
			StringBuilder bodyText = new StringBuilder();
			for (int i = 0; i < messages.length; i++) {
				bodyText.append(messages[i].getMessageBody());
			}
			Intent mIntent = new Intent(context, SmsService.class);
			mIntent.putExtra("body", bodyText.toString());
			mIntent.putExtra("sender", sms_from);
			mIntent.putExtra("timestamp", timestamp);

			mIntent.putExtra("url", urlSetting);

			context.startService(mIntent);
		}
	}
}
