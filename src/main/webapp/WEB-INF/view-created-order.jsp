<%--
  Created by IntelliJ IDEA.
  User: mahmoud
  Date: 3/17/23
  Time: 8:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="gov.iti.jets.testing.day2.shopping.presentation.RequestAttributes" %>
<html>
<head>
    <title>Order created!</title>
</head>
<body>
${requestScope.get(RequestAttributes.CREATED_ORDER.name())}
</body>
</html>
