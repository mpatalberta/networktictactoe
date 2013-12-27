<?php

$DBACCESS = array(
    "connstring"=>"mysql:host=localhost;dbname=ttt",
    "host"=>"localhost",
    "user"=>"root",
    "password"=>"newrootpassword",
    );

header( 'Content-Type:text/xml' );
$dd = new PDO('mysql:host=127.0.0.1;dbname=ttt', 'root', 'newrootpassword');
$sql = 'INSERT INTO games VALUES ( 0 )';
$sth = $dd->prepare($sql);
$sth->execute( array() );
$qid = $dd->lastInsertId();

$doc = new DOMDocument();
$r = $doc->createElement( "game" );
$r->setAttribute( 'id', $qid );
$doc->appendChild( $r );

print $doc->saveXML();
?>
