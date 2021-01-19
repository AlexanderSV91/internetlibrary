var app = angular.module("successfulPageApp", []);
app.controller("successfulPageCtrl", function ($scope, $http, $location) {

    /*const queryString = window.location.search;
    console.log(queryString);*/
    $scope.imageUrl = ""

    $scope.url = '';
    $scope.getUrl = function () {
        $scope.url = $location.absUrl();
    };
    $scope.getUrl()

    $scope.activateUser = function () {
        let token = $scope.url.replace('confirm', 'api-public/confirm');
        $http({
            url: token,
            method: 'GET'
        }).then(function successCallback(response) {
            let status = response.data;
            if (status === 'VERIFIED') {
                $scope.imageUrl = "https://galaktika-soft.com/wp-content/uploads/2018/02/activation-4.png";
            } else if (status === 'EXPIRED') {
                $scope.imageUrl = "https://image.shutterstock.com/image-vector/error-message-red-vector-icon-260nw-1184478349.jpg";
            }
        }, function errorCallback(response) {

        });
    }
    $scope.activateUser();
});
