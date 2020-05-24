<?php

// load JSON data
$data = json_decode(file_get_contents('php://input'), true);


$james = $data["James Roumeliotis"];
$jack = $data["Jack Sexty"];
$casie = $data["Casie Merza"];

print_r($data);

$mysqli = new mysqli("localhost", DATABASE, SERVER);
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}


$query = "
		UPDATE `Bounce_Table` SET Count='$james' WHERE Name='James Roumeliotis'; 
		UPDATE `Bounce_Table` SET Count='$jack' WHERE Name='Jack Sexty'; 
		UPDATE `Bounce_Table` SET Count='$casie' WHERE Name='Casie Merza';";

$sql = mysqli->multi_query($query);

if($sql) {
	echo "true";
} else {
	echo "false";
}

mysqli->close();

?>
