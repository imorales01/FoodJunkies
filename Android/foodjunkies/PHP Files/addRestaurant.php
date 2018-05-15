<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }
    
    
    public function addRestaurant($Name, $Address)
    {
        
        $response = array();
        
        $query = "INSERT INTO Restaurant(Name, Address) VALUES('$Name', '$Address')";
       
        
        $result = mysqli_query($this->connection, $query);
        
        // check if row inserted or not
        if ($result) {
            // successfully inserted into database
            $response["success"] = 1;
            $response["message"] = "Restaurant Added.";
            
            // echoing JSON response
            echo json_encode($response);
        } else {
            // failed to insert row
            $response["fail"] = 0;
            $response["message"] = "Restaurant Already Exists.";
            
            // echoing JSON response
            echo json_encode($response);
        }
    }
    
}



$user = new User();
if(isset($_POST['Name']) && $_POST['Address']) {
    
    $Name = $_POST['Name'];
    $Address = $_POST['Address'];
    
    if(!empty($Name))
    {
            $user-> addRestaurant($Name, $Address);
    }
    
    else
    {
        $json['error'] = 'No Name provided';
        echo json_encode($json);
    }
}


?>