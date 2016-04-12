'use strict';

angular.module('myApp.view5', ['ngRoute'])
        
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view5', {
                    templateUrl: 'app/view5/view5.html',
                    controller: 'View5Ctrl'
                });
            }])
        
        .controller('View5Ctrl', ['$http', '$scope', 'CurrencyFactory', function ($http, $scope, CurrencyFactory) {
//                $scope.conversions = {"Danish Kroner": 1};
//                        $scope.status = CurrencyFactory.status;
                $scope.amount = 0;
                $scope.calculatedValue = 0;
                $scope.fromCurrency = null;
                $scope.toCurrency = null;
                $scope.currencies = [];
                CurrencyFactory.getData().success(function (data) {
                    $scope.currencies = data;
//                    for (var i = 0; i<data.length; i++) {
//                        $scope.conversions.create(data.description, data.rate);
//                    }
                    
//                    console.log($scope.conversions);
                });
                $scope.calc = function () {
                    $scope.calculatedValue = $scope.amount * $scope.fromCurrency * $scope.toCurrency;
                };
            }]);
