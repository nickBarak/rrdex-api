DROP TABLE IF EXISTS ability;
CREATE TABLE ability (
	id SERIAL PRIMARY KEY,
	ability VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL
);


DROP TABLE IF EXISTS pokedata;
CREATE TABLE pokedata (
	id SERIAL PRIMARY KEY,-- Serial is basically the same as an INT, but with auto incrementing abilities
	name VARCHAR(255) NOT NULL,
	type1 VARCHAR(255) NOT NULL,
	type2 VARCHAR(255) NOT NULL,
	ability_id INTEGER REFERENCES ability(id)
);

CREATE table users(
	id SERIAL primary key,
	username VARCHAR(255) not null,
	password VARCHAR(255) not null
);

INSERT INTO ability
(ability, description)
VALUES
('Torrent', 'Raises Water-type attacks by 1.5x when at 30% or less health'),
('Overgrow', 'Raises Grass-type attacks by 1.5x when at 30% or less health'),
('Blaze', 'Raises Fire-type attacks by 1.5x when at 30% or less health'),
('Simple', 'The stat changes the Pok�mon receives are doubled');

INSERT INTO pokedata (name, type1, type2, ability_id)
VALUES
('Charmander', 'Fire', 'Fire', 3),
('Bulbasaur', 'Grass', 'Poison', 2),
('Squirtle', 'Water', 'Water', 1);

insert into users (username, password)
values
('admin', 'pass')
('user', 'pass');


/*
 * 
-- DQL Statements
SELECT *
FROM pokedata;

SELECT *
FROM ability;

-- These are what are known as SQL Joins
-- Joins are where you join tables together when they meet certain conditions on each row
SELECT *
FROM pokedata p
INNER JOIN ability a
ON p.ability_id = a.id;
*/
