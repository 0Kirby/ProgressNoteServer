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
		<p>用户ID：<input name="userId" type="text" onkeyup="value=value.replace(/[^(\d)]/g,'')">
		<p><input type="hidden" name="language" value="aaa">
		<input type="hidden" name="version" value="aaa">
		<input type="hidden" name="display" value="aaa">
		<input type="hidden" name="model" value="aaa">
		<input type="hidden" name="brand" value="aaa"><p>
		</p><p>
			<input type="submit" value="登录" onClick="login()" />&nbsp;&nbsp;
			<input type="submit" value="注册" onClick="register()">&nbsp;&nbsp;
			<input type="submit" value="查询" onClick="query()">&nbsp;&nbsp;
			
		</p>
	</form>
	<form action="/progress_note_server/AvatarServlet" enctype="multipart/form-data" method="post">
    上传ID：<input type="text" name="userId">
    选择文件:
    <input type="file" name="upload">
    <input type="submit" value="上传">

</form>
    <br><input type="submit" value="列出" onClick="javascript:location.href='/progress_note_server/ListFileServlet'">
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
	function query() {
		user.action = '/progress_note_server/SyncServlet_SC'
		user.submit()
	}
	function list() {
		window.location.href '/ListFileServlet'
		user.submit()
	}
</script>
</html>
