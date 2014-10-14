<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html ng-app="ea">
<head>
    <title>Lift Control Room</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/zebra_dialog.css" />" />
    <script type="text/javascript" src="<c:url value="/resources/scripts/angular.min.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/jquery-2.1.1.min.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/angular-bootstrap.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/zebra_dialog.js" />"></script>
    <script type="text/javascript">
        var ctx = '${pageScope.context}';
    </script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/app.js" />" ></script>
</head>
<body>
	<h1>Lift Control Room </h1>
    <div id="container">
        <div id="first"><table id="controller" ng-controller="ButtonController" class="table table-striped">
            <tr>
                <th>Floor</th>
                <th>No. of People</th>
                <th>Level you wish to go to</th>
            </tr>
            <tr ng-repeat="n in [10,9,8,7,6,5,4,3,2,1]">
                <td>{{n}}</td>
                <td>
                    <input class="peopleInput" type="text"/>
                </td>
                <td>
                    <select >
                        <option value=""></option>
                        <option ng-repeat="m in [10,9,8,7,6,5,4,3,2,1]" value="{{m}}">{{m}}</option>

                    </select>
                    <button type="button" class="btn btn-default btn-sm" disabled
                            ng-click="sendLift($event)">Go
                    </button>
                </td>
            </tr>
        </table></div>
        <div id="second"><table id="lifts" ng-controller="LiftController" class="table table-striped">
            <tr>
                <th>Elevator A</th>
                <th>Elevator B</th>
                <th>Elevator C</th>
                <th>Elevator D</th>
            </tr>
            <tr ng-repeat="floor in floors" ng-model="floor">
                <td>{{(floor.lift.indexOf('A') > -1) ? elevators[0].currentCapacity + ' ' + elevators[0].status:'-'}}</td>
                <td>{{(floor.lift.indexOf('B') > -1) ? elevators[1].currentCapacity + ' ' + elevators[1].status:'-'}}</td>
                <td>{{(floor.lift.indexOf('C') > -1) ? elevators[2].currentCapacity + ' ' + elevators[2].status:'-'}}</td>
                <td>{{(floor.lift.indexOf('D') > -1) ? elevators[3].currentCapacity + ' ' + elevators[3].status:'-'}}</td>
            </tr>
        </table></div>
        <div id="clear"></div>
    </div>


</body>
</html>