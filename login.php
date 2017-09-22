<?php

	require_once('conn_db.php');

	$username = $_REQUEST['username'];
	$password = $_REQUEST['password'];

	$sql = "SELECT role FROM users WHERE username = '$username';";
	$result = mysqli_query($conn, $sql);

	// TODO: Create better error codes
	if (mysqli_num_rows($result) == 0) {
		echo json_encode("Incorrect Username");
		return;
	}
	$sql = "SELECT role FROM users WHERE username = '$username' && password = '$password';";
	$result = mysqli_query($conn, $sql);

	if (mysqli_num_rows($result) > 0) {
		$row = mysqli_fetch_assoc($result);
		$role = $row["role"];
	}
	else {
		$role = "Incorrect Password";
	}

	echo json_encode($role);

	mysqli_close($conn);

?>