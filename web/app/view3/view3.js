'use strict';

angular.module('myApp.view3', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/view3', {
        templateUrl: 'app/view3/view3.html',
        controller: 'View3Ctrl'
    });
}])

.controller('View3Ctrl', ["$scope", "$http", function($scope, $http) {
    $http.get('api/demouser')
        .success(function(data, status, headers, config) {
            $scope.data = data;
        })
        .error(function(data, status, headers, config) {
        });

    $scope.output = {};
    
    $scope.getCompany = function() {
        return $http({
            method: 'GET',
            url: 'http://cvrapi.dk/api?search=' + $scope.cvr + '&country=DK',
            skipAuthorization: true,
            contentType: "application/json"
        }).success(function(data, status, headers, config) {
            $scope.output = data;
        }).
        error(function(data, status, headers, config) {
        })
    };




}])