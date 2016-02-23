/**
 * 
 */

var helloApp = angular.module("helloApp", [ 'ngResource' ]);
	helloApp.controller("HttpController", [ '$scope', '$resource',
			function($scope, $resource) {
				$scope.users = [];
				
				$scope.saveUser = function(){
					$scope.users.push({ 'firstname':$scope.firstname, 'lastname': $scope.lastname, 'address':$scope.address, 'email':$scope.email });		
					// Create a resource class object
					//
					var User = $resource('/user/new');
					// Call action method (save) on the class 
					//
					User.save({firstname:$scope.firstname,lastname:$scope.lastname,address:$scope.address,email:$scope.email}, function(response){
						$scope.message = response.message;
					});
					
					$scope.firstname='';
					$scope.lastname='';
					$scope.address='';
					$scope.email='';
				}
				
				$scope.updateUser = function(){							
					// Create a resource class object
					//
					var User = $resource('/user/:userId', {userId:'@id'});
					// Create an instance of User resource
					var user = User.get({userId:25});
					// Update the new values entered in the form fields
					//
					user.id = 25;
					user.firstname = $scope.firstname;
					user.lastname = $scope.lastname;
					user.address = $scope.address;
					user.email = $scope.email;
					// Call '$' prefixed action menthod to post ("save" )
					//
					user.$save(function(response){
						$scope.message = response.message;
					});
					// Push the new objects in the $scope.users 					
					//
					$scope.users.push({ 'firstname':$scope.firstname, 'lastname': $scope.lastname, 'address':$scope.address, 'email':$scope.email });
					$scope.firstname='';
					$scope.lastname='';
					$scope.address='';
					$scope.email='';
				}				
			} ]);
			
			