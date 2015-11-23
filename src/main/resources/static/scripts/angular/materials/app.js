angular.module("materials", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("materials")
    .controller("ListMaterialsCtrl", ["$scope", "$location", "Materials", "NotificationService", function ($scope, $location, Materials, NotificationService) {

        $scope.materials = ViewData.materials;

        $scope.totalMaterials = ViewData.materials.length;

        $scope.loadMaterials = function () {

            Materials.getList({q: $scope.searchKey, page: $scope.page})
                .then(function (response) {
                    $scope.materials = response.plain();

                }, function (error) {
                    console.log(error);
                });
        };

        $scope.viewMaterial = function (material) {
            $window.location.href = "/materials/edit/" + material.id;
        }

        $scope.deleteMaterial = function (material) {
            var confirmDelete = confirm("Are you sure you want to delete this material?");
            if (!confirmDelete) { return; }

            Materials.one(material.id).remove().then(function (response) {
                NotificationService.notifySuccess("Material deleted successfully");
                $scope.loadMaterials();
            }, function (error) {
                NotificationService.notifySuccess("Material cannot be deleted");
            });
        }
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