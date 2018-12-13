<?php
 
require_once 'include/DB_Functions.php';

$con=mysqli_connect("localhost", "root", "", "test");

 
// json response array
$response = array("error" => FALSE);
 

// Order city by rate
	
   $res = mysqli_query($con, "select * from city order by rate desc"); 
   $result = array();
   
   while($row = mysqli_fetch_array($res)) {
	   
	  array_push($result,
		array('city_name'=>$row[0], 'currency'=>$row[1], 'exchange_rate'=>$row[2],
		'air'=>$row[3], 'safety'=>$row[4], 'wifi'=>$row[5], 'english'=>$row[6],
		'rate'=>$row[7], 'nation'=>$row[8]
		));
   }
   
   echo json_encode(array("result"=>$result));
   
   mysqli_close($con);
 
?>
