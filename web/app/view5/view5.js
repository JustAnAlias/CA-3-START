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
                $scope.calculatedValue = 0;
                $scope.fromCurrency = null;
                $scope.setFromCurrency = function(val){
                    $scope.fromCurrency = val;
                };
                $scope.toCurrency = null;
                $scope.setToCurrency = function(val){
                    $scope.toCurrency = val;
                };
                $scope.currencies = [];
                CurrencyFactory.getData().success(function (data) {
                    $scope.currencies = data;
                });
                
                $scope.calculate = function(val){
                    $scope.calculatedValue = val * $scope.fromCurrency.rate / $scope.toCurrency.rate;
                };
                
            }]);
