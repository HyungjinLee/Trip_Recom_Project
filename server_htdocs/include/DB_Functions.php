<?php
 
/**
 * @author Ravi Tamada
 * @link https://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */
 
class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
	
	 /**
     * Recommendation by user
     * Update new details in recom table
     */
	  public function Recommendation($recom_id ,$user_id, $city_name, $comments, $safety, $wifi, $english, $user_rate) 
	  {
		   $stmt = $this->conn->prepare("INSERT INTO recommendation(recom_id, user_id, city_name, comments,
		   safety, wifi, english, user_rate, recom_time) VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW())");
		   $stmt->bind_param("isssiiid",$recom_id ,$user_id, $city_name, $comments,
			$safety, $wifi,$english,$user_rate);
		   $result = $stmt->execute();
			$stmt->close();
	
			if($result) {
				return true;
			}
			else
			{
				return false;
			}
	  }
	  
	    /**
     * Getting city information 
     * Return a particular city's information
     */
	  public function GetLastIndexInRecommendation() 
	  {
		   $stmt = $this->conn->prepare("SELECT recom_id FROM recommendation order by recom_id desc limit 1");
		
	
			if($stmt->execute()) {
				$result = $stmt->get_result()->fetch_assoc();;
				$stmt->close();
				return $result;
			}
			else
			{
				return NULL;
			}
	  }
	  
	   /**
     * Getting the last primary key in Recommendation table
     * Update new details in recom table
     */
	  public function GetCity($city_name) 
	  {
		   $stmt = $this->conn->prepare("SELECT * FROM city where city_name = ?");
		   $stmt->bind_param("s", $city_name);
	
			if($stmt->execute()) {
				$result = $stmt->get_result()->fetch_assoc();;
				$stmt->close();
				return $result;
			}
			else
			{
				return NULL;
			}
	  }

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $id, $password) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
 
        $stmt = $this->conn->prepare("INSERT INTO user(unique_id, name, id, encrypted_password, salt, created_at) VALUES(?, ?, ?, ?, ?, NOW())");
        $stmt->bind_param("sssss", $uuid, $name, $id, $encrypted_password, $salt);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM user WHERE id = ?");
            $stmt->bind_param("s", $id);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }

	 
    /**
     * Get user by id and password
     */
    public function getUserByIdAndPassword($id, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM user WHERE id = ?");
 
        $stmt->bind_param("s", $id);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            // verifying user password
            $salt = $user['salt'];
            $encrypted_password = $user['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($id) {
        $stmt = $this->conn->prepare("SELECT id from user WHERE id = ?");
	
        $stmt->bind_param("s", $id);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
 
 

}
 
?>