'use strict';

/* Place your global Factory-service in this file */

  angular.module('myApp.factories', []).
  factory('CurrencyFactory', function ($http) {
      
      var rates;
      var status;
      $http({
        method: "GET",
        url: "api/currency/rates"
    })
            .success(function(data, status, headers, config){
                rates = data;
                status = true;
            })
            .error(function(){
                status = "Connection failed";
            });
      var info = "This is the CurrencyFactory";
      var getInfo = function(){
          return info;
      };
       var getRates = function(){
          return rates;
      };
      var convertCurrency = function(amount, from, to){
          var curr = getRates();
                console.log(curr);
      };
    
    return {
        rates: rates,
        status: status,
        info: info,
        getInfo: getInfo,
        getRates: getRates,
        convertCurrency: convertCurrency
    };
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