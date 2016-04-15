'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'developerCtrl'
                });
            }])

        .controller('developerCtrl', ['$http', '$scope', '$route', 'DocumentationFactory', function ($http, $scope, $route, DocumentationFactory) {
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
                    $route.reload();
                    
                };
                $scope.removeEntry = function(entryId){
                    DocumentationFactory.removeData(entryId);
                    $route.reload();
                };
            }]);

