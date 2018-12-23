<?php
require("conn.php");
    $line = $_GET["line"];
      $type = $_GET["type"];
                 $statement = mysqli_prepare($conn, "SELECT lat, lon, id_boat FROM location2 WHERE
                    id_line LIKE $line AND type LIKE $type ORDER BY id_location DESC LIMIT 0,1");
                         mysqli_stmt_execute($statement);
                          mysqli_stmt_bind_result($statement, $lat, $lon, $id_boat);
                                    $response = array();
                                     $response["getlocation"] = false;
                                                 while(mysqli_stmt_fetch($statement)){
                                                      $response["getlocation"] = true;
                                                         $response["name"] = $id_boat;
                                                            $response["lat"] = $lat;
                                                                $response["lon"] = $lon;

                                                                           }
                                                                            echo json_encode($response);
   ?>
