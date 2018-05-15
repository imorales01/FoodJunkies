<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }

    
    public function setRatings($userID, $cusID)
    {
        
        $response = array();
        
        $query = "INSERT INTO UserCuisineRating(User_ID, Cus_ID, Rating) VALUES('$userID', '$cusID', 50)";
        $result = mysqli_query($this->connection, $query);
       
        // check if row inserted or not
        if ($result) {
            // successfully inserted into database
            $response["success"] = 1;
            $response["message"] = "Default ratings set succesfully.";
            
            // echoing JSON response
            echo json_encode($response);
        } else {
            // failed to insert row
            $response["fail"] = 0;
            $response["message"] = "Oops! Failed to set ratings.";
            
            // echoing JSON response
            echo json_encode($response);
        }
    } 
    
}
    

        
$user = new User();
if(isset($_POST['User_ID'])) {

    $userID = $_POST['User_ID'];
    $cusID = 1;
    
    if(!empty($userID))
    {
        for($cusID; $cusID < 11; $cusID++){
            $user-> setRatings($userID, $cusID); 
        }
    }
    
    else
    {
        $json['error'] = 'No user or cus IDs';
        echo json_encode($json);
    }
}


?>