<%-- 
    Document   : index_
    Created on : 27 feb. 2023, 20:55:48
    Author     : alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página de login</title>
        <link href="css/estilo.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="cuadro">
            <form action="Validar" method="POST">
                <p id="titulo"> INICIAR SESIÓN</p>
                <hr>
                <br><br><!-- comment -->
                <label id="subtitulo1">NOMBRE DE USUARIO</label>
                <br> <br><!-- comment -->
                <input type="text" name="txtuser" class="entrada"/> 
                <br> <br><!-- comment -->
                <label id="subtitulo2">CONTRASEÑA</label>
                <br> <br>
                <input type="password" name="txtpass" class="entrada"/>
                <br> <br>
                <input type="submit" name="accion" value="Ingresar" id="boton"/> 
            </form>
            <br><!-- comment -->
            <p id="marca">INEEL/AES</p>
        </div>
    </body>
</html>
