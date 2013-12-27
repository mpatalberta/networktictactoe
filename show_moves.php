<?php
function show_moves( $dbh, $game ) {
  $sql = 'SELECT * FROM moves WHERE game=?';

  $q = $dbh->prepare( $sql );
  $q->execute( array( $game ) );

  $doc = new DOMDocument();
  $r = $doc->createElement( "moves" );
  $doc->appendChild( $r );

  foreach ( $q->fetchAll() as $row) {
    $e = $doc->createElement( "move" );
    $e->setAttribute( 'x', $row['x'] );
    $e->setAttribute( 'y', $row['y'] );
    $e->setAttribute( 'color', $row['color'] );
    $r->appendChild( $e );
  }

  print $doc->saveXML();
}
?>
