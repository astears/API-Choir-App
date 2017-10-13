<?php

	require_once("conn_choir_db.php");

	$sql = "CREATE TABLE users (
	id INT PRIMARY KEY AUTO_INCREMENT,  
	fName Varchar(35) NOT NULL,
	lName Varchar(35) NOT NULL,
	username Varchar(20) UNIQUE NOT NULL,
	password Varchar(20) NOT NULL,
	email Varchar(35) UNIQUE NOT NULL,
	phone Varchar(20) UNIQUE NOT NULL,
	role Varchar(1) NOT NULL
);";

	$result = mysqli_query($conn, $sql);

	if (!$result) {
		echo "Failed to create users table";
	}

?>