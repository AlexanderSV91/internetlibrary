var app = angular.module("registrationApp", ['ngRoute']);
app.controller("registrationCtrl", function ($scope, $http) {
    $scope.editFields = {
        userName: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        age: 0,
    };

    $scope.saveUser = function () {
        console.log($scope.editFields);
        if ($scope.editFields.password === $scope.editFields.confirmPassword) {
            $http({
                method: 'http://localhost:8080/api/user/',
                url: 'POST',
                data: {
                    userName: $scope.editFields.userName,
                    password: $scope.editFields.password,
                    firstName: $scope.editFields.firstName,
                    lastName: $scope.editFields.lastName,
                    age: $scope.editFields.age,
                }
            }).then(function successCallback() {
                $window.location.href = '/';
            }, function errorCallback() {
                alert("Error!");
            });
        }
    }
});
