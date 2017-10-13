<?php

	require_once('conn_choir_db.php');

	$fName = $_REQUEST['fName'];
	$lName = $_REQUEST['lName'];
	$username = $_REQUEST['username'];
	$password = $_REQUEST['password'];
	$email = $_REQUEST['email'];
	//$phone = $_REQUEST['phone'];
	$role = $_REQUEST['role']; // TODO: figure out how to choose what role to send from the client

	$sql = "SELECT username, email FROM users;";
	$result = mysqli_query($conn, $sql);

	// TODO: Create better error codes
	if (mysqli_num_rows($result) > 0) {
		while($row = mysqli_fetch_assoc($result)) {

			if ($row['username'] == $username) {
				$res = array('result' => 'Username exists');
				echo json_encode($res);
				return;
			}
			elseif ($row['email'] == $email) {
				$res = array('result' => 'Email registered');
				echo json_encode($res);
				return;

			}
			/*elseif ($row['phone'] == $phone) {
				echo json_encode('Account is already registered with this phone number');
				return;
			}*/
		}
	}

	$sql = "INSERT INTO `users` (`fName`, `lName`, `username`, `password`, `email`, `role`) VALUES ('$fName', '$lName', '$username', '$password', '$email', '$role');";
	$result = mysqli_query($conn, $sql);

	$res = array('result' => $result);

	echo json_encode($res);

	mysqli_close($conn);
?>