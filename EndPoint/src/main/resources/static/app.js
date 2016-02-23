/**
 * 
 */


var helloApp = angular.module("SG", [ 'ngResource' ]);
helloApp.controller("HttpController", [ '$scope', '$resource',
		function($scope, $resource) {
			$scope.users = [];
			
			$scope.saveUser = function(){
				//alert($scope.name);
				$scope.users.push({ 'name':$scope.name, 'username': $scope.username, 'size':$scope.size, 'email':$scope.email });		
				// Create a resource class object
				//
				var User = $resource('/upload');
				// Call action method (save) on the class 
				//
				User.save({name:$scope.firstname,username:$scope.username,size:$scope.size,email:$scope.email}, function(response){
					$scope.message = response.message;
				});
				
				$scope.firstname='';
				$scope.username='';
				$scope.size='';
				$scope.email='';
			}
			
		} ]);
		