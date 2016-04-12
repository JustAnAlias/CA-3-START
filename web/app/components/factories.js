'use strict';

/* Place your global Factory-service in this file */

  angular.module('myApp.factories', []).
  factory('CurrencyFactory', function ($http) {
      var factory = {};
      factory.rates = [];
      $http({
        method: "GET",
        url: "api/currency/rates"
    })
            .success(function(data, status, headers, config){
                factory.rates = data;
                        console.log("status of the request from the factory is: " + status);
                        console.log("get method was successful, printing data now:");
                        console.log(data);
                status = true;
            })
            .error(function(){
                status = "Connection failed";
            });
      factory.info = "This is the CurrencyFactory";
      factory.getInfo = function(){
          return factory.info;
      };
      factory.getRates = function(){
          return factory.rates;
      };
      factory.convertCurrency = function(amount, from, to){
          var curr = factory.getRates();
                console.log(curr);
      };
    
    return factory;
  })
  
  .factory('InfoFactory', function () {
    var info = "Hello World from a Factory";
    var getInfo = function getInfo(){
      return info;
    };
    return {
      getInfo: getInfo
    };
  });