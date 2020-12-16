<%@ page language="java" contentType="text/html; charset=GB2312"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>天天笔记服务端</title>
</head>
<body>

<form name="user" method="post">
    <p>用户名：<input name="username" type="text" onkeyup="this.value=this.value.replace(/[^\w_]/g,'');">
    <p>
    <p>密&emsp;码：<input name="password" type="password">
    <p>用户ID：<input name="userId" type="text" onkeyup="value=value.replace(/[^(\d)]/g,'')">
    <p><input type="hidden" name="language" value="aaa">
        <input type="hidden" name="version" value="aaa">
        <input type="hidden" name="display"
               value="sdk_gphone_x86_arm-userdebug%2011%20RSR1.201013.001%206903271%20dev-keys">
        <input type="hidden" name="model" value="aaa">
        <input type="hidden" name="brand" value="aaa">
    <p>
    </p>
    <p>
        <input type="submit" value="登录" onClick="login()"/>&nbsp;&nbsp;
        <input type="submit" value="注册" onClick="register()">&nbsp;&nbsp;
        <input type="submit" value="查询" onClick="query()">&nbsp;&nbsp;
        <input type="submit" value="下载" onClick="download()">&nbsp;&nbsp;
    </p>
</form>
<form action="UploadAvatarServlet" enctype="multipart/form-data" method="post">
    上传ID：<input type="text" name="userId">
    选择文件:
    <input type="file" name="upload">
    <input type="submit" value="上传">

</form>
</body>

<script type="text/javascript">
    function login() {
        user.action = 'LoginServlet';
        user.submit()
    }

    function register() {
        user.action = 'RegisterServlet';
        user.submit()
    }

    function query() {
        user.action = 'SyncServlet_SC';
        user.submit()
    }

    function download() {
        user.action = 'DownloadAvatarServlet';
        user.submit()
    }
</script>
</html>
