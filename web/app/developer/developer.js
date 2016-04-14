'use strict';

angular.module('myApp.developer', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/developer', {
                    templateUrl: 'app/developer/developer.html',
                    controller: 'developerCtrl'
                });
            }])

        .controller('developerCtrl', ['$http', '$scope', 'DocumentationFactory', function ($http, $scope, DocumentationFactory) {
                var entry = {};
                $scope.number = 0;
                $scope.header = "";
                $scope.text = "";
                $scope.documentation = [];
                DocumentationFactory.getData().success(function (data) {
                    $scope.documentation = data;
                });
                $scope.addDocumentEntry = function(){
                    DocumentationFactory.addData(entry);
                };
                $scope.removeEntry = function(entry){
                    DocumentationFactory.removeData(entry);
                };
            }]);

