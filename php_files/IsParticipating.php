<?php
mysql_connect("localhost", DATABASE, SERVER);
mysql_select_db(DATBASE);

$jumper = $_POST['Jumper'];
$event = $_POST['Event'];

$query = "SELECT * FROM Jumper_Table WHERE Jumper='$jumper' AND `$event`=1";
$sql = mysql_query($query);

while($row=mysql_fetch_assoc($sql))
     $output[]=$row;
	
print(json_encode($output));

mysql_close();
?>
