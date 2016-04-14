'use strict';

angular.module('myApp.developer', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/developer', {
                    templateUrl: 'app/developer/developer.html',
                    controller: 'developerCtrl'
                });
            }])

        .controller('developerCtrl', ['$http', '$scope', 'DocumentationFactory', function ($http, $scope, DocumentationFactory) {
                $scope.newEntry = {};
                $scope.newEntry.number = 0;
                $scope.newEntry.header = "";
                $scope.newEntry.text = "";
                $scope.documentation = [];
                DocumentationFactory.getData().success(function (data) {
                    $scope.documentation = data;
                });
                $scope.addDocumentEntry = function(){
                    DocumentationFactory.addData($scope.newEntry);
                    $scope.documentation.push($scope.newEntry)
                    
                };
                $scope.removeEntry = function(entry){
                    DocumentationFactory.removeData(entry);
                    
                };
            }]);

