<%@ taglib prefix="wl" uri="http://www.e-cosystems.org/taglib" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<jsp:include page="header.jsp"></jsp:include>



<body>


<div class="lang">

</div>

<wl:cf formName="party" >
        <div class="validate" >
            <wl:lang key="modifs_done" namespace="form"/>
        </div>
    </wl:cf>

<div id="container">





    <h1><wl:lang key="slogan1" namespace="general"/></h1>
    <h2><wl:lang key="slogan2" namespace="general"/></h2>

	
	
	${ wl:trad( pageContext,"general", "info") }


    <div id="step2"><p><wl:lang key="info" namespace="general"></wl:lang></p></div>

<div id="container_page">
    <div class="drawing2">
        <img src="${pageContext.request.contextPath}/img/deco2.gif" alt="visuel" />
    </div>

    <wl:form action="#" postedKey="party" method="post" enctype="multipart/form-data"  >
	
	<p>paramtèrer pk = ${param["pk"]} }</p>
	
    <%@ include file="form.jsp"  %>

    <div class="centered"><p>
        <wl:reset><wl:lang key="reset" namespace="form" /></wl:reset>
         <wl:submit id="ponctuel_submit" ><wl:lang key="valid" namespace="form"></wl:lang></wl:submit>
         </p></div>


     </wl:form>

	<jsp:include page="addform.jsp" />
	

     </div>




</div>

<wl:debugParam />

</body>
</html>
