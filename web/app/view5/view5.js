'use strict';

angular.module('myApp.view5', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view5', {
    templateUrl: 'app/view5/view5.html',
    controller: 'View5Ctrl'
  });
}])

.controller('View5Ctrl', ['$http', '$scope', 'CurrencyFactory', function($http,$scope,CurrencyFactory) {
    $scope.test = "this is a test";
    $scope.rates = [];
    $scope.status = "Connecting...";
    $scope.status = CurrencyFactory.status;
    $scope.rates = CurrencyFactory.getRates();
    $scope.info = CurrencyFactory.getInfo();
                console.log("printing rates here:");
                console.log($scope.rates);
    $scope.convert = function(amount, from, to){
                    
    };
}]);
