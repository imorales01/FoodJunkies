<?php
include_once 'connection.php';

class User {
    
    private $db;
    private $connection;
    
    function __construct() {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }
    
    
    public function getRestID($Name)
    {
        
        $response = array();
        
        $query = "Select Rest_ID from Restaurant WHERE Name='$Name'";
        $result = mysqli_query($this->connection, $query);
        
        
        // check for empty result
        if (mysqli_num_rows($result) > 0) {
            // looping through all results
            // products node
            $response["products"] = array();
            
            while ($row = mysqli_fetch_array($result)) {
                // temp user array
                $product = array();
                $product["Rest_ID"] = $row["Rest_ID"];
                
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
if(isset($_POST['Name'])) {
    
    $Name = $_POST['Name'];
    
    if(!empty($Name))
    {
        $user-> getRestID($Name);
    }
    
    else
    {
        $json['error'] = 'No Restaurant provided';
        echo json_encode($json);
    }
}


?>