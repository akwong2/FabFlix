<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Like Predicate</title>
</head>
<body>

<h1>LIKE Predicate</h1>
<p>We used the LIKE predicate in our MovieListSearch.java. We used it to compare the input of the user  to the 
search criteria. 
We used:
</p>
<p>
- upper(movies.title) like upper('%'+title+'%') to match a movie title to the string 'title' which the user inputs.
If this string is found anywhere in the movie.title, it will match. By doing this, the user can have an empty input
and it would match every movie.title.
</p>
<p>
-upper(movies.director) like upper('%'+dir+'%') to match a movie director to the string 'dir' which the user also
has the option of inputting for the same reasoning above.
</p>
<p>
- upper(stars.name) like upper('%'+star+'%') to match a star name to the string 'star' which the user also has
the option of inputting.
</p>
<p>
We used upper() to deal with any case-sensitive issues that might appear.
</p>

</body>
</html>