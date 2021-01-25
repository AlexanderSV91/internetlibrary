var app = angular.module("userApp", []);
app.controller("userCtrl", function ($scope, $http) {

    $scope.titleModal = "";
    $scope.users = [];
    $scope.editFields = {
        id: 0,
        userName: "",
        firstName: "",
        lastName: "",
        email: "",
        age: 0,
    };

    $scope.changeTitleModal = function (titleName) {
        $scope.titleModal = titleName;
        if (titleName === "Add User") {
            $scope.editFields.id = 0;
            $scope.editFields.userName = "";
            $scope.editFields.password = "";
            $scope.editFields.firstName = "";
            $scope.editFields.lastName = "";
            $scope.editFields.email = "";
            $scope.editFields.age = "";
        }
    }

    $scope.fillFieldsModal = function (user) {
        $scope.editFields.id = user.id;
        $scope.editFields.userName = user.userName;
        $scope.editFields.firstName = user.firstName;
        $scope.editFields.lastName = user.lastName;
        $scope.editFields.email = user.email;
        $scope.editFields.age = user.age;
    }

    $scope.getAllUser = function () {
        $http({
            url: 'http://localhost:8080/api/user',
            method: 'GET'
        }).then(function (response) {
            $scope.users = response.data;
        })
    }
    $scope.getAllUser();

    $scope.deleteUserByIdRequest = function (userId) {
        $http({
            url: 'http://localhost:8080/api/user/' + userId,
            method: 'DELETE'
        }).then(function () {
            $scope.getAllUser();
        })
    }

    $scope.saveUser = function () {
        let data = '';
        let request = '';
        let method = '';
        if ($scope.editFields.id === 0) {
            request = 'http://localhost:8080/api/user/';
            method = 'POST';
            data = {
                userName: $scope.editFields.userName,
                password: $scope.editFields.password,
                firstName: $scope.editFields.firstName,
                lastName: $scope.editFields.lastName,
                email: $scope.editFields.email,
                age: $scope.editFields.age,
            };
        } else {
            request = 'http://localhost:8080/api/user/' + $scope.editFields.id;
            method = 'PUT';
            data = {
                id: $scope.editFields.id,
                userName: $scope.editFields.userName,
                password: $scope.editFields.password,
                firstName: $scope.editFields.firstName,
                lastName: $scope.editFields.lastName,
                email: $scope.editFields.email,
                age: $scope.editFields.age,
            };
        }
        $http({
            method: method,
            url: request,
            data: data
        }).then(function successCallback(response) {
            $scope.getAllUser();
        }, function errorCallback(response) {
        });
    }
});
