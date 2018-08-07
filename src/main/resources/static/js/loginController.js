var app = angular.module('loginApp', ['ngRoute']);

app.controller('LogInController', function($scope, $http){
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
	$scope.login = function(){
		console.log($scope.user);
		$scope.dataLoading = false; //to let the gif run in index.jsp. viewmodel ung vm
		//$scope.user.idnumber = "";
		//$scope.user.password = "";
		/*$http({
            method : 'POST',
            url : 'login',
            //dataType: json,
            contentType: 'application/json',
            data : {'idnumber': JSON.stringify($scope.user.idnumber),
            		'password':JSON.stringify($scope.user.password)}, //make sure that your data is sent as proper JSON:
        }).success(function(data) {
        	$scope.dataLoading = true;
            console.log(data);
            //alert('haha');
        }).error(function(data) {
        	$scope.dataLoading = true;
            console.log(data);
        });*/
    };
	
});
