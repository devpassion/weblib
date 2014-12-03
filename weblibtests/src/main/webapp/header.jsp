<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="wl" uri="http://www.e-cosystems.org/taglib" %>
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<title><wl:lang key="site_name" namespace="general"/></title>
<meta name="Description" content="<wl:lang key="slogan1" namespace="general"/>"/>
<meta name="Keywords" content="<wl:lang key="keywords" namespace="general"/>" />
<meta name="Robots" content="index,follow" />

<%-- <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/style.css" /> --%>
<link rel="shortcut icon" href="favicon.ico" />

<!-- Goolge map-->
<!-- <meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> -->
<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> -->

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/map.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/modernizr.js"></script>


<!-- set language and hide javascript avertissement -->
<%--
<script type="text/javascript">
    site_lang = '${lang}';
    $(document).ready( function()
    {
        $('#nojs').addClass("hidden");
    }
     );
</script> --%>

<!-- Tigra calendar -->
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tcal.css"> --%>
<%-- <script language="JavaScript" src="${pageContext.request.contextPath}/js/tcal.js"></script> --%>

<!-- perso -->
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/form.js"></script> --%>


<!--<script>-->
<!--$(document).ready(function(){-->
<!---->
<!--if(!Modernizr.input.placeholder){-->
<!---->
<!--    $('[placeholder]').focus(function() {-->
<!--      var input = $(this);-->
<!--      if (input.val() == input.attr('placeholder')) {-->
<!--        input.val('');-->
<!--        input.removeClass('placeholder');-->
<!--      }-->
<!--    }).blur(function() {-->
<!--      var input = $(this);-->
<!--      if (input.val() == '' || input.val() == input.attr('placeholder')) {-->
<!--        input.addClass('placeholder');-->
<!--        input.val(input.attr('placeholder'));-->
<!--      }-->
<!--    }).blur();-->
<!--    $('[placeholder]').parents('form').submit(function() {-->
<!--      $(this).find('[placeholder]').each(function() {-->
<!--        var input = $(this);-->
<!--        if (input.val() == input.attr('placeholder')) {-->
<!--          input.val('');-->
<!--        }-->
<!--      })-->
<!--    });-->
<!---->
<!--}-->
<!---->
<!--});-->
<!--</script>-->




</head>