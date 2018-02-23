CREATE PROCEDURE `add_movie`(moviesid varchar(10), title varchar(100), 
year int(11), director varchar(100), starid varchar(10), starname varchar(100), starbirthyear int(11),
 genrename varchar(32), out mExists int(11), out sExists int(11), out gExists int(11))
BEGIN
	DECLARE movieExists int(10);
    DECLARE starExists int(10);
    DECLARE genreExists int(10);
    DECLARE genreId int(11);
    
    
    SET movieExists := (SELECT count(*) FROM movies WHERE movies.id = moviesid);
    SET starExists := (SELECT count(*) FROM stars WHERE stars.id = starid);
    SET genreExists := (SELECT count(*) FROM genres WHERE genres.name = genrename);
    
    SET mExists := movieExists;
    SET sExists := starExists;
    SET gExists := genreExists;
    
    
    
	IF (movieExists < 1) THEN
		 BEGIN
		 INSERT into movies VALUES(moviesid, title, year, director);
         END;
    END IF;
    IF (starExists < 1) THEN
		BEGIN
		INSERT into stars VALUES(starid, starname, starbirthyear);
		END;
	END IF;
	IF (genreExists < 1) THEN
		BEGIN
		INSERT into genres VALUES(DEFAULT, genrename);
		END;
	END IF;
    
	INSERT into stars_in_movies VALUES(starid,moviesid);
	SET genreId := (SELECT id FROM genres WHERE name = genrename);
	INSERT into genres_in_movies VALUES(genreId,moviesid);
    
END