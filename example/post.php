<?php
$body = isset($_POST['body']) ? $_POST['body'] : null;
$sender = isset($_POST['sender']) ? $_POST['sender'] : null;
$timestamp = isset($_POST['timestamp']) ? $_POST['timestamp'] : null;
if ($body && $sender && $timestamp) {
	$timestamp = date('Y-m-d H:i:s', (int)($timestamp / 1000));
	$message =
<<<SMS
New sms message:
sender: {$sender}
body: {$body}
time: {$timestamp}
SMS;
	mail('example@gmail.com', 'new sms', $message);
}
