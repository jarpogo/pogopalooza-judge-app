<?php
mysql_connect("localhost", "bouncet2_pogoapp", "bouncet2");
mysql_select_db("bouncet2_pogoapp");

$judge = $_POST['Judge'];
$jumper = $_POST['Jumper'];
$event = $_POST['Event'];
$type = $_POST['Type'];
$create = $_POST['Create'];
$diff = $_POST['Diff'];
$var = $_POST['Var'];
$style = $_POST['Style'];
$land = $_POST['Land'];

$query = "INSERT INTO `Score_Tracking_Table` (`Judge`, `Jumper`, `Event`, `Type`, `Creativity`, `Difficulty`, `Variety`, `Style`, `Landings`) VALUES ('$judge','$jumper','$event','$type','$create','$diff','$var','$style','$land')";

$sql = mysql_query($query);

print($query);

mysql_close();
?>
