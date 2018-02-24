<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Performance</title>
</head>
<body>
<h1>Performance Tuning</h1>
<p>
We used a batch insert when inserting into the tables of movies, genres, genre_in_movies, stars, stars_in_movies.
This method reduced the runtime significantly because the query didn't need to be reparsed and all the commands were
single transaction.
</p>
</body>
</html>