<?php

$con=mysqli_connect("localhost", "root", "", "test");

if(mysqli_connect_errno($con))
{
	echo "Failed to connect to MySQL: " . mysql_connect_error();
}

mysqli_set_charset($con, "utf8");

$res = mysqli_query($con, "select * from user");

$result = array();

while($row = mysqli_fetch_array($res)){
	

	array_push($result,
	 array('id'=>$row[0], 'name'=>$row[1], 'address'=>$row[2]
	 ));
}

echo json_encode(array("result"=>$result));

mysqli_close($con);

?>