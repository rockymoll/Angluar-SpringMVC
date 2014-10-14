var ea = angular.module('ea', []);
ea.controller('LiftController', ['$scope', '$http', function ($scope, $http) {
    ea.getLifts = function (id) {
        $http({method: 'GET', url: ctx + '/lift/'}).
            success(function (data, status, headers, config) {
                $scope.elevators = data;
                var result = true;
                for(var i =0; i < data.length; i++){
                    if(data[i].status != 'STATIONARY'){
                        result = false
                    }
                }
                if(result && id > 0){
                    clearInterval(id)
                }
                ea.getFloors();

            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    ea.getFloors = function () {
        $http({method: 'GET', url: ctx + '/floor/'}).
            success(function (data, status, headers, config) {

                $scope.floors = data.slice().reverse();

                for (var i = 0; i < $scope.floors.length; i++) {
                    $scope.floors[i].lift = [];
                    for (var j = 0; j < $scope.elevators.length; j++) {
                        if ($scope.elevators[j].currentLevel == $scope.floors[i].level) {
                            $scope.floors[i].lift.push($scope.elevators[j].name);
                        }
                    }
                }
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    ea.getLifts(0);
}]);

ea.controller('ButtonController', ['$scope', '$http', function ($scope, $http) {
    $(function(){
        $('tr input[type=text], tr select').bind('change', function(){
            var people = $($(this).parents('tr').children('td')[1]).children('input').val();
            var desiredFloor = $($(this).parents('tr').children('td')[2]).children('select').val();
            var button = $($(this).parents('tr').children('td')[2]).children('button');
            if (people < 0 || people > 20 || isNaN(people) || desiredFloor === '') {
                $($(this).parents('tr').children('td')[1]).children('input').css('color','red');
                button.attr('disabled','disabled');
            } else {
                $($(this).parents('tr').children('td')[1]).children('input').css('color','black');
                button.removeAttr('disabled');
            }
        });
    });

    $scope.sendLift = function ($event) {
        var people = $($($event.target).parents('tr').children('td')[1]).children('input').val();
        var desiredFloor = $($($event.target).parents('tr').children('td')[2]).children('select').val();
        var currentLevel = parseFloat($($($event.target).parents('tr').children('td')[0]).text());
        var button = $($($event.target).parents('tr').children('td')[2]).children('button');
        button.attr('disabled','disabled');
        if(desiredFloor == currentLevel){
            $.Zebra_Dialog('You are already on level: ' + currentLevel);
        }
        $($($event.target).parents('tr').children('td')[1]).children('input').val('');
        $($($event.target).parents('tr').children('td')[2]).children('select').val('');
        $http({method: 'PUT', url: ctx + '/floor/', data: {people: people, level: currentLevel, levelWishToGo: desiredFloor}}).
            success(function (data, status, headers, config) {
                $http({method: 'POST', url: ctx + '/sendNearestLift/', data: {from: currentLevel, to: parseFloat(desiredFloor), peopleWaiting: people}}).
                    success(function (data, status, headers, config) {
                        ea.getLifts(0);
                        var id = setInterval(function () {
                            ea.getLifts(id)
                        }, 1000);
                    })
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };
}]);
