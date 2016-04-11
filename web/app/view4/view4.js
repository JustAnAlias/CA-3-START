'use strict';

angular.module('myApp.view4', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view4', {
    templateUrl: 'app/view4/view4.html',
    controller: 'View4Ctrl'
  });
}])

.controller('View4Ctrl', function($http,$scope) {

    $scope.rates = [];
    $scope.status = "Connecting...";
    
    $http({
        method: "GET",
        url: "api/currency/dailyrates"
    })
            .success(function(data, status, headers, config){
                $scope.rates = data;
                $scope.status = true;
            })
            .error(function(){
                $scope.status = "Connection failed";
            });
 
});