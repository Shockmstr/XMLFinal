<%-- 
    Document   : viewCart
    Created on : Jul 12, 2020, 5:09:39 PM
    Author     : Admin
--%>

<%@page import="hieubd.stax.XMLUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
    </head>
    <body>
        <h1>Your cart details</h1>

        <c:set var="cartList" value="${sessionScope.CARTLIST}"/>
        <c:if test="${not empty cartList}">
            <c:set var="cartItems" value="${cartList.items}"/>
            <c:if test="${not empty cartItems}">
                <form action="cart">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Image</th>
                            <th>Brand</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Type</th>
                            <th>Category</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${cartItems}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td><img src="${entry.value.img}"/></td>
                                <td>${entry.value.brand}</td>
                                <td>${entry.value.name}</td>
                                <td>${entry.value.price}</td>
                                <td>${entry.value.type}</td>
                                <td>${entry.value.category}</td>
                                <td><input type="text" name="${entry.key}" value="${entry.value.quantity}"/> </td> 
                                <td>
                                    <fmt:parseNumber var="parsedPrice" type="currency" value="${entry.value.price}"/>
                                    $ ${parsedPrice * entry.value.quantity}
                                </td>
                                <td><input type="checkbox" name="chkCartAction" value="${entry.key}" /></td>
                            </tr>      
                        </c:forEach>                                
                    </tbody>
                </table>
                <a href="checkout.jsp" style="float: left"><input type="button" value="Checkout"/></a><br>
                <div style="float: right">
                    <input type="submit" value="Update" name="btnCart" />
                    <input type="submit" value="Remove" name="btnCart"/>
                </div>                
                </form>
            </c:if>
        </c:if>
        <c:if test="${empty cartItems}">
            <h2 style="color: red">Your cart is empty!!</h2>        
        </c:if>
        <br>
        <a href="home.jsp">Go back to search page</a>
    </body>
</html>
