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

$query = "UPDATE `Score_Tracking_Table` SET Creativity='$create', Difficulty='$diff', Variety='$var', Style='$style', Landings='$land' WHERE Judge='$judge' AND Jumper='$jumper' AND Event='$event' AND Type='$type'";

$sql = mysql_query($query);

print($query);

mysql_close();
?>
