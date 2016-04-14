'use strict';

angular.module('myApp.view5', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view5', {
                    templateUrl: 'app/view5/view5.html',
                    controller: 'View5Ctrl'
                });
            }])

        .controller('View5Ctrl', ['$http', '$scope', 'CurrencyFactory', function ($http, $scope, CurrencyFactory) {
                $scope.amount = 0;
                $scope.calculatedValue = 0;
                $scope.fromCurrency = null;
                $scope.setFromCurrency = function (val) {
                    $scope.fromCurrency = val;
                    $scope.calculate();
                };
                $scope.toCurrency = null;
                $scope.setToCurrency = function (val) {
                    $scope.toCurrency = val;
                    $scope.calculate();
                };
                $scope.currencies = [];
                CurrencyFactory.getData().success(function (data) {
                    $scope.currencies = data;
                    $scope.currencies.push({code: "DKK", rate: 100, description: "Danish Kroner"});
                    $scope.currencies.sort(function(a,b){
                        return a.description - b.description;
                    });
                });

                $scope.calculate = function (val) {

                    if ($scope.fromCurrency == null) {
                        $scope.fromCurrency = {rate: 100};
                    }
                    if ($scope.toCurrency == null) {
                        $scope.toCurrency = {rate: 100};
                    }

                    $scope.calculatedValue = $scope.amount * $scope.fromCurrency.rate / $scope.toCurrency.rate;
                };

            }]);
