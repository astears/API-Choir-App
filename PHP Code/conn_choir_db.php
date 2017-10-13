<?php

	header("Access-Control-Allow-Origin: *");
	header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
	
	defined('DB_HOST') or define('DB_HOST', 'us-cdbr-iron-east-05.cleardb.net');
	defined('DB_USER') or define('DB_USER', 'b756b279902529');
	defined('DB_PASS') or define('DB_PASS', 'a8ca6e94');
	defined('DB_NAME') or define('DB_NAME', 'heroku_9e871dbfe3e631c');

	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME);

	if (!$conn) {
		die("connection failed" . mysqli_connect_error());
	}