var app = angular.module("registrationApp", []);
app.controller("registrationCtrl", function ($scope, $http) {
    $scope.editFields = {
        userName: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        email: "",
        age: 0
    };

    $scope.saveUser = function () {
        console.log($scope.editFields);
        if ($scope.editFields.password === $scope.editFields.confirmPassword) {
            let request = 'http://localhost:8080/api-public/registration';
            let method = 'POST';
            let data = {
                userName: $scope.editFields.userName,
                password: $scope.editFields.password,
                firstName: $scope.editFields.firstName,
                lastName: $scope.editFields.lastName,
                email: $scope.editFields.email,
                age: $scope.editFields.age
            };
            $http({
                method: method,
                url: request,
                data: data
            }).then(function successCallback() {
                document.location.href='/';
            }, function errorCallback() {
                alert("Error!");
            });
        } else {
            alert("password does not match!");
        }
    }
});