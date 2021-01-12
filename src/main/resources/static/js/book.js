var app = angular.module("bookApp", []);
app.controller("bookCtrl", function ($scope, $http) {

    $scope.titleModal = "";
    $scope.books = [];
    $scope.editFields = {
        id: 0,
        name: "",
        bookCondition: "",
        description: ""
    };

    $scope.changeTitleModal = function (titleName) {
        $scope.titleModal = titleName;
        if (titleName === "Add Book") {
            $scope.editFields.id = 0;
            $scope.editFields.name = "";
            $scope.editFields.bookCondition = "";
            $scope.editFields.description = "";
        }
    }

    $scope.fillFieldsModal = function (book) {
        $scope.editFields.id = book.id;
        $scope.editFields.name = book.name;
        $scope.editFields.bookCondition = book.bookCondition;
        $scope.editFields.description = book.description;
    }

    $scope.getAllBook = function () {
        $http({
            url: 'http://localhost:8080/api/book',
            method: 'GET'
        }).then(function (response) {
            $scope.books = response.data;
        })
    }
    $scope.getAllBook();

    $scope.deleteBookByIdRequest = function (bookId) {
        $http({
            url: 'http://localhost:8080/api/book/' + bookId,
            method: 'DELETE'
        }).then(function () {
            $scope.getAllBook();
        })
    }

    $scope.saveBook = function () {
        let data = '';
        let request = '';
        let method = '';
        if ($scope.editFields.id === 0) {
            request = 'http://localhost:8080/api/book/';
            method = 'POST';
            data = {
                name: $scope.editFields.name,
                bookCondition: $scope.editFields.bookCondition,
                description: $scope.editFields.description
            };
        } else {
            request = 'http://localhost:8080/api/book/' + $scope.editFields.id;
            method = 'PUT';
            data = {
                id: $scope.editFields.id,
                name: $scope.editFields.name,
                bookCondition: $scope.editFields.bookCondition,
                description: $scope.editFields.description
            };
        }
        $http({
            method: method,
            url: request,
            data: data
        }).then(function successCallback() {
            $scope.getAllBook();
        }, function errorCallback() {
            alert("Error!");
        });
    }
});