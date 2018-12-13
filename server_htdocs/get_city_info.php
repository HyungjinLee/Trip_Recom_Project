<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 

// Get City Information from city

if (isset($_POST['city_name']))
{	
	$city_name = $_POST['city_name'];
   // $city_name = "Beijing";
	$City = $db->GetCity($city_name);
	  
    if ($City) {
        // executed successfully
		 $response["error"] = FALSE;
		 $response["city_name"] = $City["city_name"];
         $response["currency"] = $City["currency"];
		 $response["exchange_rate"] = $City["exchange_rate"];
         $response["air"] = $City["air"];
         $response["safety"] = $City["safety"];
         $response["wifi"] = $City["wifi"];
         $response["english"] = $City["english"];
         $response["rate"] = $City["rate"];
         $response["nation"] = $City["nation"];
         echo json_encode($response);		
	}
	else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in city!";
            echo json_encode($response);			
	}
    
}
 
?>
