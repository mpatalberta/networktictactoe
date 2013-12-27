<?php
require_once( 'show_moves.php' );

header( 'Content-Type:text/xml' );
$dbh = new PDO('mysql:host=127.0.0.1;dbname=ttt', 'root', 'newrootpassword');
$sql = 'DELETE FROM moves WHERE game=? AND x=? AND y=?';
$sth = $dbh->prepare($sql);
$sth->execute( array(
  $_REQUEST['game'],
  $_REQUEST['x'],
  $_REQUEST['y']
) );

$sql = 'INSERT INTO moves VALUES ( 0, ?, ?, ?, ? )';
$sth = $dbh->prepare($sql);
$sth->execute( array(
  $_REQUEST['game'],
  $_REQUEST['x'],  $_REQUEST['y'],  $_REQUEST['color']) );

show_moves( $dbh, $_REQUEST['game'] );
?>
