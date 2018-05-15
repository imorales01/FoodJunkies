<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }
    
    
    public function likeDish($User_ID, $Dish_ID)
    {
        
        $response = array();
        
        $query = "INSERT INTO LDdish (User_ID, Dish_ID, LD) VALUES('$User_ID', '$Dish_ID', 'L') ON DUPLICATE KEY UPDATE LD = 'L'";
        
        $result = mysqli_query($this->connection, $query);
        
        // check if row inserted or not
        if ($result) {
            // successfully inserted into database
            $response["success"] = 1;
            $response["message"] = "Dish liked.";
            
            // echoing JSON response
            echo json_encode($response);
        } else {
            // failed to insert row
            $response["fail"] = 0;
            $response["message"] = "Dish Already liked.";
            
            // echoing JSON response
            echo json_encode($response);
        }
    }
    
}



$user = new User();
if(isset($_POST['User_ID']) && $_POST['Dish_ID']) {
    
    $User_ID = $_POST['User_ID'];
    $Dish_ID = $_POST['Dish_ID'];
    
    if(!empty($User_ID))
    {
        $user-> likeDish($User_ID, $Dish_ID);
    }
    
    else
    {
        $json['error'] = 'No ID provided';
        echo json_encode($json);
    }
}


?>