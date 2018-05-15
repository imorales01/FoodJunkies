<?php
include_once 'connection.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function does_user_exist($Email,$Password)
		{
			$query = "Select * from User where Email='$Email' and Password = '$Password' ";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
			    $row = mysqli_fetch_array($result);
			    $userID = $row['User_ID'];
				$json['success'] = ''.$userID;
				
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$query = "insert into User (Email, Password) values ( '$Email','$Password')";
				$inserted = mysqli_query($this -> connection, $query);
				if($inserted == 1 ){
					$json['created'] = 'Acount created';
				}else{
					$json['error'] = 'Wrong password';
				}
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$user = new User();
	if(isset($_POST['Email'],$_POST['Password'])) {
		$Email = $_POST['Email'];
		$Password = $_POST['Password'];
		
		if(!empty($Email) && !empty($Password)){
			
			$encrypted_password = md5($Password);
			$user-> does_user_exist($Email,$encrypted_password);
			
		}else{
		    $json['error'] = 'You must fill both fields';
		    echo json_encode($json);
		}
		
	}
?>


