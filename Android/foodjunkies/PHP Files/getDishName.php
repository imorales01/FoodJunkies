<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }
    
    
    public function getDishName($dishID)
    {
        
        $response = array();
        
        $query = "Select * from Dish WHERE Dish_ID='$dishID'";
        $result = mysqli_query($this->connection, $query);
        
        
        // check for empty result
        if (mysqli_num_rows($result) > 0) {
            // looping through all results
            // products node
            $response["products"] = array();
            
            while ($row = mysqli_fetch_array($result)) {
                // temp user array
                $product = array();
                $product["Name"] = $row["Name"];
                $product["Url"] = $row["Url"];
                
                // push single product into final response array
                array_push($response["products"], $product);
            }
            // success
            $response["success"] = 1;
            
            // echoing JSON response
            echo json_encode($response);
            mysqli_close($this->connection);
        } else {
            // no products found
            $response["success"] = 0;
            $response["message"] = "No products found";
            
            // echo no users JSON
            echo json_encode($response);
            mysqli_close($this->connection);
        }
    }
    
}



$user = new User();
if(isset($_POST['Dish_ID'])) {
    
    $dishID = $_POST['Dish_ID'];
    
    if(!empty($dishID))
    {
        $user-> getDishName($dishID);
    }
    
    else
    {
        $json['error'] = 'No dish ID provided';
        echo json_encode($json);
    }
}


?>