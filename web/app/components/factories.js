'use strict';

/* Place your global Factory-service in this file */

angular.module('myApp.factories', []).
        factory('CurrencyFactory', function ($http) {
            var factory = {};
            factory.getData = function(){ // controller prints undefined when trying to print results..
                return $http({
                    method: "GET",
                    url: "api/currency/rates"
                });
            };
            return factory;
        })

        .factory('InfoFactory', function () {
            var info = "Hello World from a Factory";
            var getInfo = function getInfo() {
                return info;
            };
            return {
                getInfo: getInfo
            };
        });