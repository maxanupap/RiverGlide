<?php  
		require("conn.php");
	$stopb = array();
	$sql = "select stop_id_eng,stop_id_th,stopname_eng,stopname_th,stop_lat,stop_long from boat_stop"; 
 
	if($res = $conn->query($sql)){
		while($row=$res->fetch_assoc()){  
			$stop_id_eng = $row['stop_id_eng'];
				$stop_id_th = $row['stop_id_th'];  
					$stopname_eng = $row['stopname_eng'];
						$stopname_th = $row['stopname_th'];
							$stop_lat = $row['stop_lat'];       
								$stop_long = $row['stop_long']; 
				$data=array("stop_id_eng"=>$stop_id_eng,"stop_id_th"=>$stop_id_th,"stopname_eng"=>$stopname_eng,"stopname_th"=>$stopname_th,"stop_lat"=>$stop_lat,"stop_long"=>$stop_long);                
		$stopb[] = $data; 
		$result = array("result"=>$stopb);  
	}       
	echo json_encode($result); 
} 
	
?>