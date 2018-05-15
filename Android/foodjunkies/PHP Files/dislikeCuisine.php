<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }
    
    
    public function dislikeCuisine($userID, $cusID)
    {
        
        $response = array();
        
        $query = "UPDATE UserCuisineRating SET Rating = Rating - 10 WHERE User_ID = '$userID' && Cus_ID = '$cusID'";
        $result = mysqli_query($this->connection, $query);
        
        // check if row inserted or not
        if ($result) {
            // successfully inserted into database
            $response["success"] = 1;
            $response["message"] = "Cuisine disliked successfully.";
            
            // echoing JSON response
            echo json_encode($response);
        } else {
            // failed to insert row
            $response["fail"] = 0;
            $response["message"] = "Oops! Failed to rate.";
            
            // echoing JSON response
            echo json_encode($response);
        }
    }
    
}



$user = new User();
if(isset($_POST['User_ID']) && $_POST['Cus_ID']) {
    
    $userID = $_POST['User_ID'];
    $cusID = $_POST['Cus_ID'];
    
    if(!empty($userID) && !empty($cusID))
    {
        
        $user-> dislikeCuisine($userID, $cusID);
        
    }
    
    else
    {
        $json['error'] = 'No user or cus IDs';
        echo json_encode($json);
    }
}


?>