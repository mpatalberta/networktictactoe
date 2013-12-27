<?php
header( 'Content-Type:text/xml' );

$dbh = new PDO('mysql:host=127.0.0.1;dbname=ttt', 'root', 'newrootpassword');
$sql = 'SELECT * FROM games';

$q = $dbh->prepare( $sql );
$q->execute( array() );

$doc = new DOMDocument();
$r = $doc->createElement( "games" );
$doc->appendChild( $r );

foreach ( $q->fetchAll() as $row) {
  $e = $doc->createElement( "game" );
  $e->setAttribute( 'id', $row['id'] );
  $r->appendChild( $e );
}

print $doc->saveXML();
?>
