'use strict';

angular.module('myApp.view5', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view5', {
    templateUrl: 'app/view5/view5.html',
    controller: 'View5Ctrl'
  });
}])

.controller('View5Ctrl', ['$http', '$scope', 'currencyFactory', function($http,$scope, currencyFactory) {
    $scope.fromCurrency = "";
    $scope.toCurrency = "";
    $scope.amount = 0;
    $scope.rates = [];
    $scope.status = "Connecting...";
    $scope.status = currencyFactory.status;
    $scope.rates = currencyFactory.getRates();
    $scope.convert = function(amount, from, to){
                    console.log(rates);
    };
}]);
