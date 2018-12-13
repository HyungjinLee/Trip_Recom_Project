<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 

if (isset($_POST['recom_id']) && isset($_POST['user_id']) &&
 isset($_POST['city_name']) && isset($_POST['comments']) && 
 isset($_POST['safety']) && isset($_POST['wifi']) &&
 isset($_POST['english']) && isset($_POST['user_rate'])) {

 /*
    // testing 
    $recom_id = 1;
    $user_id = "dummy";
    $city_name = "seoul";
	$comments = "Awsome!";
	$safety = 30;
	$wifi = 330;
	$english = 5;
	$user_rate = 16;
*/
 
    // receiving the post params
    $recom_id = $_POST['recom_id'];
    $user_id = $_POST['user_id'];
    $city_name = $_POST['city_name'];
	$comments = $_POST['comments'];
	$safety = $_POST['safety'];
	$wifi = $_POST['wifi'];
	$english = $_POST['english'];
	$user_rate = $_POST['user_rate'];

 
	// recommendating city
	
    $recom = $db->Recommendation($recom_id ,$user_id, $city_name, $comments, $safety, $wifi, $english, $user_rate);
	  
    if ($recom) {
        // user recommended successfully
        $response["error"] = FALSE;
        $response["recommendation"]["recom_id"] = $recom["name"];
        $response["recommendation"]["user_id"] = $recom["id"];
        $response["recommendation"]["city_name"] = $recom["created_at"];
        $response["recommendation"]["comments"] = $recom["comments"];
        $response["recommendation"]["safety"] = $recom["safety"];
        $response["recommendation"]["wifi"] = $recom["wifi"];
        $response["recommendation"]["english"] = $recom["english"];
		$response["recommendation"]["user_rate"] = $recom["user_rate"];
		
        echo json_encode($response);
    }
	else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in recommendation!";
            echo json_encode($response);			
	}
    
	
 }
 
 else {
		$response["error"] = TRUE;
		$response["error_msg"] = "Required parameters is missing!";
		echo json_encode($response);
}	
 
?>
