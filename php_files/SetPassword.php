<?php
mysql_connect("localhost", "bouncet2_pogoapp", "bouncet2");
mysql_select_db("bouncet2_pogoapp");

$username = $_POST['Username'];
$password = $_POST['Password'];

$query = "UPDATE `Judge_Table` SET Password='$password' WHERE Username='$username'";
$sql = mysql_query($query);

if($sql) {
	echo "true";
} else {
	echo "false";
}

mysql_close();
?>
