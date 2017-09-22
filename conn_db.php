<?php

	header("Access-Control-Allow-Origin: *");
	header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
	
	defined('DB_HOST') or define('DB_HOST', 'localhost');
	defined('DB_USER') or define('DB_USER', 'root');
	defined('DB_PASS') or define('DB_PASS', '');
	defined('DB_NAME') or define('DB_NAME', 'choir_db');

	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME);

	if (!$conn) {
		die("connection failed" . mysqli_connect_error());
	}
?>