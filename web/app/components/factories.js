'use strict';

/* Place your global Factory-service in this file */

angular.module('myApp.factories', []).
  factory('CurrencyFactory', function () {
      var factory = {};
      $http({
        method: "GET",
        url: "api/currency/dailyrates"
    })
            .success(function(data, status, headers, config){
                factory.rates = data;
                factory.status = true;
            })
            .error(function(){
                factory.status = "Connection failed";
            });
      factory.info = "This is the CurrencyFactory";
      factory.getRates = function(){
          return factory.rates;
      };
      factory.convertCurrency = function(amount, from, to){
          var curr = factory.getRates();
                console.log(curr);
          
      };
    
    return factory;
  });