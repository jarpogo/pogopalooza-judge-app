<?php
mysql_connect("localhost", DATABASE, SERVER);
mysql_select_db(DATBASE);

$judge = $_POST['Judge'];
$jumper = $_POST['Jumper'];
$event = $_POST['Event'];

$query = "SELECT * FROM Score_Tracking_Table WHERE Jumper='$jumper' AND Judge='$judge' AND Event='$event'";
$sql = mysql_query($query);

while($row=mysql_fetch_assoc($sql))
     $output[]=$row;

print(json_encode($output));

mysql_close();
?>
