<?php

$con = mysqli_connect("localhost", "root", "root", "test");
if(!$con)
	die("Unable to connect".mysqli_error($con));
else
{
	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$username = $_POST['username'];
		$password = $_POST['password'];
		
		$stmt = $con->prepare("SELECT * FROM users where username = ? and password = ?;");
		$stmt->bind_param("ss",$username,$password);
		$stmt->execute();
		$stmt->store_result();
		$val = $stmt->num_rows;
		
		if($val>0)
			echo "login successful";
		else
			echo "invalid username or password";
	}
}
mysqli_close($con);

?>