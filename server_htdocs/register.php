<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['name']) && isset($_POST['id']) && isset($_POST['password'])) {
 
    // receiving the post params
    $name = $_POST['name'];
    $id = $_POST['id'];
    $password = $_POST['password'];
 
    // check if user is already existed with the same id
    if ($db->isUserExisted($id)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "이미 가입된 멤버입니다." . $id;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($name, $id, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["uid"] = $user["unique_id"];
            $response["user"]["name"] = $user["name"];
            $response["user"]["id"] = $user["id"];
            $response["user"]["created_at"] = $user["created_at"];
            $response["user"]["updated_at"] = $user["updated_at"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, id or password) is missing!";
    echo json_encode($response);
}
?>