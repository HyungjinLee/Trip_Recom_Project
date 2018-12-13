<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 

// Get Last Index from recom
	
    $Last = $db->GetLastIndexInRecommendation();
	  
    if ($Last) {
        // executed successfully
		 $response["error"] = FALSE;
         $response["recom_id"] = $Last["recom_id"];
         echo json_encode($response);		
	}
	else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in recommendation!";
            echo json_encode($response);			
	}
    

 
?>
