<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginform</title>
</head>
<body>
	<h1>관리자 로그인</h1><br><br>
	${errorMessage }<br>
<!--flashmap에 넣어져있던 errorMessage의 값을 가져온 것 -->


	<form action="login" method="post">
		암호 : <input type="password" name="passwd"><br>
		<input type="submit" value="로그인">
	</form>
	
</body>
</html>