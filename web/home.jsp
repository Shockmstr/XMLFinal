<%-- 
    Document   : index
    Created on : Jun 23, 2020, 2:11:57 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <script type="text/javascript">
            var count = 0;
            var cells = [];
            var new_XMLDOM = null;
            var xmlHttp;
            function addRow(tableId, cells){
                var tableElem = document.getElementById(tableId);
                var newRow = tableElem.insertRow(tableElem.rows.length);
                var newCell;
                for (var i = 0; i < cells.length; i++) {
                    newCell = newRow.insertCell(newRow.cells.length);
                    newCell.innerHTML = cells[i];
                }
                return newRow;
            }
            function deleteRow(tableId, rowNumber){
                var tableElem = document.getElementById(tableId);
                if (rowNumber > 0 && rowNumber < tableElem.rows.length){
                    tableElem.deleteRow(rowNumber);
                } else {
                    alert("Failed");
                }
            }
            function getXmlHttpObject(){
                var xmlHttp = null;
                try { // Firefox, Opera 8+, Safari
                    xmlHttp = new XMLHttpRequest();
                    
                } catch (e) { // IE
                    try {
                        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                    }catch(e){
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                }       
                return xmlHttp;
            }
            function searchNode(node, strSearch, tableName){
                if (node == null)
                    return;
                if (node.tagName == "name"){
                    var tmp = node.firstChild.nodeValue;                  
                    if (tmp.indexOf(strSearch) > -1){
                        alert(node.parentNode.tagName)
                        var parent = node.parentNode;
                        alert("p: " + parent)
                        //var attrID = parent.attributes.getNamedItem("id").text;
                        new_XMLDOM += "<product>";
                        count++;
                        cells[0] = count;
                        cells[1] = count;
                        cells[2] = node.firstChild.nodeValue;
                        new_XMLDOM += "<brand>" + node.firstChild.nodeValue; + "</brand>";
                        var name = node.nextSibling;
                        cells[3] = name.firstChild.nodeValue;
                        new_XMLDOM += "<name>" + author.firstChild.nodeValue + "</name>";
                        var price = author.nextSibling;
                        cells[4] = price.firstChild.nodeValue;
                        new_XMLDOM += "<price>" + price.firstChild.nodeValue + "</price>";
                        addRow(tableName, cells);
                        new_XMLDOM += "</product>";
                        alert(new_XMLDOM)
                    }
                }
                var childs = node.childNodes;
                for (var i = 0; i < childs.length; i++) {
                    searchNode(childs[i], strSearch, tableName);
                }
            }
            function traversalDOMTree(fileName, tableName){
                var tableElem = document.getElementById(tableName);
                var i = 1;
                while(i < tableElem.rows.length){
                    deleteRow(tableName, i);
                }
                count = 0;
                new_XMLDOM = null;
                var xmlDOM = new ActiveXObject("Microsoft.XMLDOM");
                new_XMLDOM = '<products xmlns="http://schema/product">';
                xmlDOM.async = false;
                xmlDOM.load(fileName);
                if (xmlDOM.parseError.errorCode != 0){
                    alert("Error: " + xmlDOM.parseError.reason);
                } else {
                    searchNode(xmlDOM, myForm.txtSearch.value, tableName);
                    new_XMLDOM += "</products>";
                    alert(new_XMLDOM);
                }
            }
            function update(){
                xmlHttp = getXmlHttpObject();
                if (xmlHttp == null){
                    alert("Your browser does not support AJAX");
                    return ;
                }
                xmlHttp.open("POST", "Controller", true);
                xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                var url = "btAction=update&xmlContent=";
                url += new_XMLDOM;
                xmlHttp.send(url);
            }
            
            function getQuantity(id){
                var quantElem = document.getElementById(id);
                var quantity = quantElem.value;            
                alert(quantity + " items was added!");             
            }
        </script>
    </head>
    <body>
        <h2>Hello, ${sessionScope.USERNAME}</h2>
        <h1>Outdoor Activities Item</h1>
        <div style="float: right">
            <input type="button" value="Your Order" />          
            <a href="viewCart.jsp"><input type="button" value="View Cart"/></a>
        </div>
        <form action="searchcategory" method="POST">
            Choose your activity: 
            <select name="activity">
                <option value="Camp &amp; Hike">Camp & Hike</option>
                <option value="Fishing">Fishing</option>
                <option value="Climbing equipment">Climbing Equipment</option>
            </select>
            
            <input type="submit" value="Search"/>
        </form>    
        <br>
        <form action="search">
            Or Search By Name (Blank to get all):
            <input type="text" name="txtSearch" placeholder="Input name" />
            <input type="submit" value="Search" />
        </form>
        
      
        <c:set var="list" value="${sessionScope.PROLIST}"/>
            <c:if test="${not empty list}">
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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${list}" varStatus="counter">
                        <tr>
                            <input type="hidden" value="${dto.id}" name="id"/>
                            <td>${counter.count}</td>
                            <td><img src="${dto.img}" alt="img"/></td>
                            <td>${dto.brand}</td>
                            <td>${dto.name}</td>
                            <td>${dto.price}</td>
                            <td>${dto.type}</td>
                            <td>${dto.category}</td>                          
                            <td>
                                <form action="AddToCartServlet" method="POST">
                                    <input type="hidden" value="${dto.id}" name="txtId"/>
                                    <input type="number" id="${dto.id}" min="1" max="999" name="${dto.id}" value="1"/>
                                    <input type="submit" value="Add to Cart" onclick="getQuantity(${dto.id})"/>
                                </form>                             
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
 
       
        
    </body>
</html>
