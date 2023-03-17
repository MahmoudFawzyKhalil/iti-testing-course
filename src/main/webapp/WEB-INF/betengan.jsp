<%--@elvariable id="betengan" type="java.lang.Integer"--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<c:if test="${1 == 1}">
    1 = 1
</c:if>
<c:if test="${1 == 2}">
    1 = 2
</c:if>
<h1>${betengan}</h1>
</body>
</html>