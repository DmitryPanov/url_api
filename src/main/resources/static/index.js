angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app/api/v1/urls';

    $scope.loadUrls = function () {
        $http.get(contextPath).then(function (responce) {
            $scope.urls = responce.data;
        });
    }

    $scope.createShortUrl = function () {
        $http({
            url: contextPath,
            method: 'POST',
            data: $scope.urlDetails
        }).then(function (response) {
            // alert('Url create');
            $scope.urlDetails = null;
            $scope.loadUrls();
        });
    };

    $scope.loadUrls();

});