<?php

	require_once('conn_choir_db.php');

	$username = $_REQUEST['username'];
	$password = $_REQUEST['password'];

	$sql = "SELECT role FROM users WHERE username = '$username';";
	$result = mysqli_query($conn, $sql);


	// TODO: Create better error codes
	if (mysqli_num_rows($result) == 0) {
		$role = array('role' => "Incorrect Username");
		echo json_encode($role);
		return;
	}
	$sql = "SELECT role FROM users WHERE username = '$username' && password = '$password';";
	$result = mysqli_query($conn, $sql);

	if (mysqli_num_rows($result) > 0) {
		$row = mysqli_fetch_assoc($result);
		$role = array('role' => $row["role"]);
	}
	else {
		$role = "Incorrect Password";
		$role = array('role' => "Incorrect Password");
	}

	echo json_encode($role);

	mysqli_close($conn);

?>