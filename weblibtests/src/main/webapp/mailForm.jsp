<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="wl" uri="http://www.e-cosystems.org/taglib" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!--Le formulaire se nomme 'mail' -->
        <wl:form postedKey="mail" enctype="multipart/form-data"> 
            Entrez votre email : <wl:input type="TEXT" name="mail" />
            <wl:ci name="mail" rule="NotNull" >
                Vous devez entrer une adresse!
            </wl:ci>
            <wl:ci name="mail" rule="invalidMail" >
                L'adresse mail est invalide!
            </wl:ci>
                
        </wl:form>
    </body>
</html>
