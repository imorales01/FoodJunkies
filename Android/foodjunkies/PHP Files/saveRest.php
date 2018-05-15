<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }
    
    
    public function saveRestaurant($User_ID, $Rest_ID, $Dish_ID)
    {
        
        $response = array();
        
        $query = "INSERT INTO LDrestaurant(User_ID, Rest_ID, LD, Bookmark, Dish_ID) VALUES('$User_ID', '$Rest_ID', 'N', 'Y', '$Dish_ID') ON DUPLICATE KEY UPDATE Bookmark = 'Y', Dish_ID = $Dish_ID";
        
        $result = mysqli_query($this->connection, $query);
        
        // check if row inserted or not
        if ($result) {
            // successfully inserted into database
            $response["success"] = 1;
            $response["message"] = "Restaurant Saved.";
            
            // echoing JSON response
            echo json_encode($response);
        } else {
            // failed to insert row
            $response["fail"] = 0;
            $response["message"] = "Restaurant Already Saved.";
            
            // echoing JSON response
            echo json_encode($response);
        }
    }
    
}



$user = new User();
if(isset($_POST['User_ID']) && $_POST['Rest_ID'] && $_POST['Dish_ID']) {
    
    $User_ID = $_POST['User_ID'];
    $Rest_ID = $_POST['Rest_ID'];
    $Dish_ID = $_POST['Dish_ID'];
    
    if(!empty($User_ID))
    {
        $user-> saveRestaurant($User_ID, $Rest_ID, $Dish_ID);
    }
    
    else
    {
        $json['error'] = 'No ID provided';
        echo json_encode($json);
    }
}


?>