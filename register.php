<?php

$con = mysqli_connect("localhost", "root", "root", "test");
if(!$con)
	die("Unable to connect".mysqli_error($con));
else
{
	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$username = $_POST['username'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		$sql = "INSERT INTO users (username,email,password) VALUES ('$username','$email','$password');";
		$result = mysqli_query($con, $sql);
		
		if(!$result)
			die("Unable to send the data".mysqli_error($con));
		else
			echo "Data inserted";
	}
}
mysqli_close($con);

?>