<?php
require_once( 'show_moves.php' );

header( 'Content-Type:text/xml' );

$dbh = new PDO('mysql:host=127.0.0.1;dbname=ttt', 'root', 'newrootpassword');
show_moves( $dbh, $_REQUEST['game'] );
?>
