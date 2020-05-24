<?php
mysql_connect("localhost", DATABASE, SERVER);
mysql_select_db(DATBASE);

$event = $_POST['Event'];

$array = array();
$previousJumper = null;
$total=null;
$counter=1;

mysql_connect("localhost", DATABASE, SERVER);
mysql_select_db(DATBASE);

$query = "SELECT DISTINCT Judge, Jumper FROM Score_Tracking_Table WHERE Event='$event' ORDER BY Jumper";
$sql = mysql_query($query);

while($row=mysql_fetch_row($sql)) :
	$query = "SELECT  Judge, Jumper, Event, (`Creativity` + `Difficulty` + `Variety` + `Style` + `Landings`), Type FROM `Score_Tracking_Table` WHERE Judge='$row[0]' AND Jumper='$row[1]' AND Event='$event'";
	$sql2 = mysql_query($query);

	if($previousJumper == $row[1]) {
			$counter = $counter + 1;
	} else if ($previousJumper == null ) {
		// do nothing
	} else {
		//echo "we're in!\n";
		$total = $total/$counter;
		$array[$previousJumper] = $total;
		//echo "$previousJumper Total:  $total\n";
		$total = 0;
		$counter = 1;
	}

	while($row2=mysql_fetch_row($sql2)) :
		//echo "$previousJumper || $row[1] || $total || $counter\n";
		//echo "$row2[0] $row2[1] $row2[2] $row2[3] $row2[4]\n";
		
		if(strpos($event,"Final") !== false) {
		//echo "Final\n";
			if(strpos($row2[4],"jam1") !== false) {
				$jam = intval($row2[3]) * .4;
				$total = $total + $jam;
			} elseif (strpos($row2[4],"solo1") !== false) {
				$solo1 = intval($row2[3]) * .6;
				$total = $total + $solo1;
			} elseif (strpos($row2[4],"solo2") !== false) {
				$solo2 = intval($row2[3]) * .6;
				if(intval($solo2 > $solo1)) {
					$total = $total - $solo1;
					$total = $total + $solo2;
				} else {
					// do nothing
				}
			}
		}
		
		if(strpos($event,"Qual") !== false) {
		//echo "Qual\n";
			if(strpos($row2[4],"jam1") !== false) {
				$total = $total + ($row2[3] * .5);
			} elseif (strpos($row2[4],"jam2") !== false) {
				$jam = $row2[3] * .5;
				$total = $total + $jam;
			} 
		}		
		 
	
	endwhile;
	
	$previousJumper = $row[1];
	
endwhile;

$total = $total/$counter;
$array[$previousJumper] = $total;

arsort($array);

print(json_encode($array));

mysql_close();
?>
