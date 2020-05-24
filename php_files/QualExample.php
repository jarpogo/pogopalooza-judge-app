<?php
mysql_connect("localhost", DATABASE, SERVER);
mysql_select_db(DATBASE);

$judge = $_POST['Judge'];
$jumper = $_POST['Jumper'];
$event = $_POST['Event'];

$query = "SELECT Jumper FROM Score_Tracking_Table WHERE Event='Tech Qual'";
$sql = mysql_query($query);

while($row=mysql_fetch_row($sql)) :
     $query = "SELECT  `Judge`, `Jumper`, `Event`, AVG(`Creativity` + `Difficulty` + `Variety` + `Style` + `Landings`) FROM `Score_Tracking_Table` WHERE Jumper='$row[0]' AND Event='Tech Qual'";
	 $sql2 = mysql_query($query);

         while($row2=mysql_fetch_row($sql2)) :
              echo "$row2[0] $row2[1] $row2[2] $row2[3]\n";
         endwhile;
endwhile;

mysql_close();
?>