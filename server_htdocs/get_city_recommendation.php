<?php
 
require_once 'include/DB_Functions.php';

$con=mysqli_connect("localhost", "root", "", "test");

 
// json response array
$response = array("error" => FALSE);
 

// Get City recommendation

if (isset($_POST['city_name']))
{
   $city_name = $_POST['city_name'];
   $res = mysqli_query($con,"select * from recommendation where city_name = '$city_name'"); 
   $result = array();
   
   while($row = mysqli_fetch_array($res)) {
	   
	  array_push($result,
		array('recom_id'=>$row[0], 'user_id'=>$row[1], 'city_name'=>$row[2],
		'comments'=>$row[3], 'safety'=>$row[4], 'wifi'=>$row[5], 'english'=>$row[6],
		'user_rate'=>$row[7], 'recom_time'=>$row[8]
		));
   }
   
   echo json_encode($response+array("result"=>$result));
   
}
   mysqli_close($con);
 
?>
