var app = angular.module("registrationApp", []);
app.controller("registrationCtrl", function ($scope, $http) {
    $scope.roles = [];
    $scope.editFields = {
        userName: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        age: 0
    };

    $scope.getAllRoles = function () {
        $http({
            url: 'http://localhost:8080/api/registration-role',
            method: 'GET'
        }).then(function (response) {
            $scope.roles = response.data;
            console.log($scope.roles);
        })
    }
    $scope.getAllRoles();

    $scope.saveUser = function () {
        console.log($scope.editFields);
        if ($scope.editFields.password === $scope.editFields.confirmPassword) {
            let request = 'http://localhost:8080/api/registration/';
            let method = 'POST';
            let data = {
                userName: $scope.editFields.userName,
                password: $scope.editFields.password,
                firstName: $scope.editFields.firstName,
                lastName: $scope.editFields.lastName,
                age: $scope.editFields.age
            };
            $http({
                method: method,
                url: request,
                data: data
            }).then(function successCallback() {
                /*$window.location.href = '/';*/
                alert("Add user")
            }, function errorCallback() {
                alert("Error!");
            });
        }
    }
});
