'use strict';

angular.module('myApp.admin', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/admin', {
        templateUrl: 'app/admin/admin.html',
        controller: 'AdminCtrl'
    });
}])



.controller('AdminCtrl', ['$http', '$scope', function($http, $scope) {
    $scope.users = [];

    $http.get('api/admin/users').success(function(data, status, headers, config) {
            $scope.users = data;
        })
        .error(function(data, status, headers, config) {
        });
    $scope.deleteUser = function(id) {
        $http.delete('api/admin/users/' + id)
            .success(function(data, status, headers, config) {
                for (var i = 0; i < $scope.users.length; i++) {
                    if (id === $scope.users[i].id) {
                        $scope.users.splice(i, 1);

                    }
                }
            })
            .error(function(data, status, headers, config) {
            });
    };




}]);