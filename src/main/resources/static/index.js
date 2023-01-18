angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app/api/v1/urls';


    $scope.loadUrls = function () {
        $http.get(contextPath)
            .then(function (responce) {
                $scope.urls = responce.data;
            });
    }

    $scope.createShortUrl = function () {
        $http({
            url: contextPath,
            method: 'POST',
            data: $scope.urlDetails
        }).then(function (response) {
            // TODO : if catch exception return message with exception to user
            $scope.urlDetails = null;
            $scope.loadUrls();
        });
    };

    $scope.redirectByShortUrl = function (shortLink) {
        $http.get(contextPath + '/' + shortLink)
            .then(function (responce) {
                console.log(responce.data);
                console.log(responce);
                $scope.loadUrls();
                window.open(responce.data.url, '_blank');
            });
    };

    $scope.loadUrls();
});