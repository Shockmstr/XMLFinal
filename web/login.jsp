<%-- 
    Document   : login
    Created on : Jul 9, 2020, 12:59:07 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <div style="width: 100%; text-align: center; display: block">
        <h1>LOGIN</h1>
        <form action="login" method="POST" style="margin-left: auto; margin-right: auto; text-align: left; display: inline-block">
            <table border="0" cellspacing="2">               
                <tbody>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="txtUsername"/></td>
                    </tr>
                    <tr>
                        <td>Password:</td> 
                        <td><input type="password" name="txtPassword"/></td>
                    </tr>
                </tbody>
            </table>
            <div>
                <input type="reset" value="Reset" style="width: auto" />
                <input type="submit" value="Login" style="width: auto"/>
            </div>
        </form>
        </div>
    </body>
</html>
