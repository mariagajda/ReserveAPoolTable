<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reserve a Service</title>

</head>
<body>
<h1>Reserve a Service</h1>
<form method="post">
    <label>Pick date
        <input type="date" name="dateStr"/>
    </label>
    <input type=submit value="Check availability"/>
</form>

<h3>Prices for table reservation:</h3>
<table>
    <thead>
    <tr>
        <th></th>
        <th>Monday-Thursday</th>
        <th>Friday</th>
        <th>Saturday</th>
        <th>Sunday and holidays<sup>*</sup></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>before 18:00</td>
        <td>18 PLN/hour</td>
        <td>18 PLN/hour</td>
        <td>23 PLN/hour</td>
        <td>23 PLN/hour</td>
    </tr>
    <tr>
        <td>after 18:00</td>
        <td>18 PLN/hour</td>
        <td>18 PLN/hour</td>
        <td>23 PLN/hour</td>
        <td>23 PLN/hour</td>
    </tr>
    </tbody>
</table>
<p><sup>*</sup>holidays on Friday or Saturday = Friday's or Saturday's prices</p>

<p>For registered users: if you have more than 5 reservation we have for You 5% discount from regular prices!</p>
</body>
</html>