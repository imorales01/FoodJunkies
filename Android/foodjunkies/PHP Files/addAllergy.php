<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }
    
    
    public function addAllergy($userID, $ingID)
    {
        
        $response = array();
        
        $query = "INSERT INTO UserAllergies(User_ID, Ing_ID) VALUES('$userID', '$ingID')";
        $result = mysqli_query($this->connection, $query);
        
        // check if row inserted or not
        if ($result) {
            // successfully inserted into database
            $response["success"] = 1;
            $response["message"] = "Allergy added successfully.";
            
            // echoing JSON response
            echo json_encode($response);
        } else {
            // failed to insert row
            $response["fail"] = 0;
            $response["message"] = "Oops! Failed to add.";
            
            // echoing JSON response
            echo json_encode($response);
        }
    }
    
}



$user = new User();
if(isset($_POST['User_ID']) && $_POST['Ing_ID']) {
    
    $userID = $_POST['User_ID'];
    $ingID = $_POST['Ing_ID'];
    
    if(!empty($userID) && !empty($ingID))
    {
        
           $user-> addAllergy($userID, $ingID);
        
    }
    
    else
    {
        $json['error'] = 'No user or ing IDs';
        echo json_encode($json);
    }
}


?>