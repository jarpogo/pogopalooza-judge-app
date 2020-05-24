<?php
mysql_connect("localhost", DATABASE, SERVER);
mysql_select_db(DATBASE);

$query = "SELECT * FROM Event_Table ORDER BY Event ASC";
$sql = mysql_query($query);

while($row=mysql_fetch_assoc($sql))
     $output[]=$row;
	 
print(json_encode($output));

mysql_close();
?>
