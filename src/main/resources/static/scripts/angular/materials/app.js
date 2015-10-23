angular.module("materials", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("materials")
    .controller("ListMaterialsCtrl", ["$scope", "$location", "Materials", function ($scope, $location, Materials) {

        $scope.materials = ViewData.materials;

        $scope.totalMaterials = ViewData.materials.length;

    }]);


angular.module("materials")
    .controller("EditMaterialCtrl", ["$scope", "$rootScope", "$location", "Materials", "NotificationService", function ($scope, $rootScope, $location, Materials, NotificationService) {

        $scope.material = ViewData.material;
        $scope.mode = ViewData.mode;

        $scope.saveMaterial = function () {
            $rootScope.pageLoading = true;

            if ($scope.mode === "new") {

                Materials.post($scope.material).then(function (response) {
                    var created = response.plain();
                    NotificationService.notifySuccess("Material created successfully");
                    $rootScope.pageLoading = false;

                    window.location.href = "/materials";
                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

            } else if ($scope.mode === "edit") {

                Materials.one($scope.material.id).customPUT($scope.material).then(function (response) {
                    var updated = response.plain();
                    NotificationService.notifySuccess("Material updated successfully");
                    $rootScope.pageLoading = false;

                    window.location.href = "/materials";
                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

                return;

            }
        }
    }]);