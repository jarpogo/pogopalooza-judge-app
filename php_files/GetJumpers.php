<?php
mysql_connect("localhost", DATABASE, SERVER);
mysql_select_db(DATBASE);

$query = "SELECT * FROM Jumper_Table Order By Jumper ASC";
$sql = mysql_query($query);

while($row=mysql_fetch_assoc($sql))
     $output[]=$row;
	 
print(json_encode($output));

mysql_close();
?>
