<?php
$host="127.0.0.1";
$user="root";
$DBpassword="root";
$database="demo";
$connect =  mysqli_connect($host, $user, $DBpassword, $database);
if(mysqli_connect_errno())
{
	die("cannot connect to database field:".mysqli_connect_error());
}

$usename = $_GET['username'];
$password = $_GET['password'];

//$query="insert into login(username,password) values ('".$usename."','".$password."');";
$query="select * from login;";
$result =  mysqli_query($connect, $query);
if(!$result)
{
	$output = "{'msg': 'something went wrong'}";
	die("Error in query");
}

//$output = "{'msg': 'data inserted successfuly'}";

while($row = mysqli_fetch_assoc($result))
{
	$output[] = $row;
}
print(json_encode($output));//Print the output in json format

mysqli_free_result($result);
mysqli_close($connect);
?>