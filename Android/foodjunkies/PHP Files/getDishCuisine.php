<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }
    
    
    public function getDishCuisine($Dish_ID)
    {
        
        $response = array();
        
        $query = "Select * from DishCuisine WHERE Dish_ID='$Dish_ID'";
        $result = mysqli_query($this->connection, $query);
        
        
        // check for empty result
        if (mysqli_num_rows($result) > 0) {
            // looping through all results
            // products node
            $response["products"] = array();
            
            while ($row = mysqli_fetch_array($result)) {
                // temp user array
                $product = array();
                $product["Cus_ID"] = $row["Cus_ID"];
                
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
    
    $Dish_ID = $_POST['Dish_ID'];
    
    if(!empty($Dish_ID))
    {
        $user-> getDishCuisine($Dish_ID);
    }
    
    else
    {
        $json['error'] = 'No Dish_ID provided';
        echo json_encode($json);
    }
}


?>