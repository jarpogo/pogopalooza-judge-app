<?php
mysql_connect("localhost", DATABASE, SERVER);
mysql_select_db(DATBASE);

$username = $_POST['Username'];
$password = $_POST['Password'];

$query = "SELECT Password FROM Judge_Table WHERE Username='$username'";
$sql = mysql_query($query);

$row = mysql_fetch_row($sql);
$pass = $row[0];

if($pass == $password) {
	echo "true";
} else {
	echo "false";
}

mysql_close();
?>
