DROP TABLE IF EXISTS games;
CREATE TABLE games(
     id INT NOT NULL AUTO_INCREMENT,
     primary key ( id ) );

DROP TABLE IF EXISTS moves;
CREATE TABLE moves(
     id INT NOT NULL AUTO_INCREMENT,
     game INT NOT NULL,
     x INT NOT NULL,
     y INT NOT NULL,
     color INT NOT NULL,
     primary key ( id ) );
