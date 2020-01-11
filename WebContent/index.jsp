<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>天天笔记服务端</title>
</head>
<body>

	<form name="user" method="post">
		<p>用户名：<input name="username" type="text" onkeyup="this.value=this.value.replace(/[^\w_]/g,'');">
		<p><p>密&emsp;码：<input name="password" type="password">
		</p><p>
			<input type="submit" value="登录" onClick="login()" />&nbsp;&nbsp;<input
				type="submit" value="注册" onClick="register()">
		</p>
	</form>
</body>

<script type="text/javascript">
	function login() {
		user.action = '/progress_note_server/LoginServlet'
		user.submit()
	}
	function register() {
		user.action = '/progress_note_server/RegisterServlet'
		user.submit()
	}
</script>
</html>
