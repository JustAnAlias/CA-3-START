'use strict';

/* Place your global Factory-service in this file */

angular.module('myApp.factories', []).
  factory('InfoFactory', function () {
      var factory = {};
    factory.info = "Hello World from a Factory";
    factory.dailyRates = function getInfo(){
      return ($http.get('api/'));
    };
    return factory;
  });