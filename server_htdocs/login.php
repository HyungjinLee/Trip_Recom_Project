<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['id']) && isset($_POST['password'])) {
 
    // receiving the post params
    $id = $_POST['id'];
    $password = $_POST['password'];
 
    // get the user by id and password
    $user = $db->getUserByIDAndPassword($id, $password);
 
    if ($user != false) {
        // user is found
        $response["error"] = FALSE;
        $response["uid"] = $user["unique_id"];
        $response["user"]["name"] = $user["name"];
        $response["user"]["id"] = $user["id"];
        $response["user"]["created_at"] = $user["created_at"];
        $response["user"]["updated_at"] = $user["updated_at"];
        
	
		echo json_encode($response);
			
		
				
	} else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "로그인 정보가 맞지 않습니다. 다시 시도하여 주세요!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters id or password is missing!";
    echo json_encode($response);
}
?>