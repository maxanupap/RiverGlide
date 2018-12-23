<?php
	require("conn.php");
		$stop = $_POST["id"];
 		$line = $_POST["line"];
   		$type = $_POST["type"];

    $sql="SELECT * FROM boat_stop WHERE stop_id_eng='$stop'";
        
			$result = mysqli_query($conn,$sql);
          $row = $result->fetch_assoc();
           $lat1 = $row['stop_lat'];
           $lon1 = $row['stop_long'];

             	$sql="SELECT * FROM location2 WHERE id_line LIKE $line AND type LIKE
		$type ORDER BY id_location DESC LIMIT 0,1";

                 $result = mysqli_query($conn,$sql);  
				$row = $result->fetch_assoc();
                  $lat2 = $row['lat'];
                    $lon2 = $row['lon'];

                      $sql="SELECT * FROM location2 WHERE id_line LIKE $line AND type LIKE 
					 $type ORDER BY id_location DESC LIMIT 10,1";
                             
								$result = mysqli_query($conn,$sql);
                               $row = $result->fetch_assoc();
                                $lat3 = $row['lat'];
                                  $lon3 = $row['lon'];
                                   $unit = null; //ค ำนวณระยะห่ำงของ เรือ - ท่ำเรือ 
	function distance($lat1, $lon1, $lat2, $lon2, $unit) {

  $theta = $lon1 - $lon2;
    $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) *
     cos(deg2rad($lat2)) * cos(deg2rad($theta));
      $dist = acos($dist);
       $dist = rad2deg($dist);
         $miles = $dist * 60 * 1.1515;
           $unit = strtoupper($unit);

  if ($unit == "M") {
    return ($miles * 1609.344);
      }
	}

function distanceboat($lat2, $lon2, $lat3, $lon3, $unit) {

 $theta = $lon2 - $lon3;
    $dist = sin(deg2rad($lat2)) * sin(deg2rad($lat3)) +  cos(deg2rad($lat2)) *
     cos(deg2rad($lat3)) * cos(deg2rad($theta));
      $dist = acos($dist);
         $dist = rad2deg($dist);
            $miles = $dist * 60 * 1.1515;
               $unit = strtoupper($unit);

 if ($unit == "M") {
         return ($miles * 1609.344);
           }
         }
         $distanm = distance($lat1, $lon1, $lat2, $lon2, "M");
           $distanboatm = distanceboat($lat2, $lon2, $lat3, $lon3, "M");
             
		$response = array();
                 $response = 
					 array("success"=>true,"dis1"=>$distanm,"dis2"=>$distanboatm,"lat"=>$lat2,"lon"=>$lon2);
                      
		echo json_encode($response);
			
   ?>
