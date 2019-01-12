angular.module("boms", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("boms")
    .controller("ListBOMsCtrl", ["$scope", "$location", "BOMs", "NotificationService", function ($scope, $location, BOMs, NotificationService) {

        $scope.boms = ViewData.boms;

        $scope.totalBOMs = ViewData.boms.length;

        $scope.loadBOMs = function () {

            BOMs.getList({q: $scope.searchKey, page: $scope.page})
                .then(function (response) {
                    $scope.boms = response.plain();
                }, function (error) {
                    console.log(error);
                });
        };

        $scope.viewBOM = function (bom) {
            window.location.href = "/boms/edit/" + bom.id;
        }

        $scope.deleteBOM = function (bom) {
            var confirmDelete = confirm("Are you sure you want to delete this bill of materials?");
            if (!confirmDelete) {
                return;
            }

            BOMs.one(bom.id).remove().then(function (response) {
                NotificationService.notifySuccess("Bill of materials deleted successfully");
                $scope.loadBOMs();
            }, function (error) {
                NotificationService.notifySuccess("Bill of materials cannot be deleted");
            });
        }

    }]);


angular.module("boms")
    .controller("EditBOMCtrl", [
        "$scope",
        "$rootScope",
        "$location",
        "$timeout",
        "BOMs",
        "Materials",
        "NotificationService",
        function ($scope,
                  $rootScope,
                  $location,
                  $timeout,
                  BOMs,
                  Materials,
                  NotificationService) {

            $scope.bom = ViewData.bom;

            $scope.newItem = {
                materialId: 0,
                materialName: "",
                quantity: 0,
                ratio: 0,
            };

            $scope.totalQuantity = 0;

            $scope.$watch("bom.items", function (items) {

                var totalQuantity = _.reduce(items, function (memo, item) {
                    return parseFloat(memo) + parseFloat(item.quantity);
                }, 0);

                angular.forEach(items, function (item) {
                    item.ratio = item.quantity / totalQuantity * 100;
                });

                $scope.totalQuantity = totalQuantity;

            }, true);

            $scope.mode = ViewData.mode;

            var materialMap;

            Materials.getList().then(function (result) {
                $scope.materials = result.plain();
                materialMap = _.indexBy($scope.materials, "id");

                angular.forEach($scope.bom.items, function (item) {
                    item.materialName = materialMap[item.materialId].name;
                });
            });

            $scope.addItem = function () {
                var newItem = {
                    materialId: $scope.newItem.materialId,
                    quantity: 0,
                    ratio: 0
                };

                var material = materialMap[newItem.materialId];

                if (material == null) {
                    alert("Please select a material");
                    return;
                }

                newItem.materialName = material.name;

                var index = _.findIndex($scope.bom.items, function (item) {
                    return item.materialId == newItem.materialId;
                });

                if (index != -1) {
                    console.log("List of materials cannot contain duplicate item");
                    return;
                }

                $scope.bom.items.push(newItem);
            };

            $scope.deleteItem = function (index) {
                $scope.bom.items.splice(index, 1);
            };

            $scope.saveBOM = function () {
                $rootScope.pageLoading = true;

                if ($scope.mode === "new") {

                    BOMs.post($scope.bom).then(function (response) {
                        var created = response.plain();
                        NotificationService.notifySuccess("BOM created successfully");
                        $rootScope.pageLoading = false;

                        window.location.href = "/boms";
                    }, function (error) {
                        console.log(error);
                        $rootScope.pageLoading = false;
                    });

                } else if ($scope.mode === "edit") {

                    BOMs.one($scope.bom.id).customPUT($scope.bom).then(function (response) {
                        var updated = response.plain();
                        NotificationService.notifySuccess("BOM updated successfully");
                        $rootScope.pageLoading = false;

                        window.location.href = "/boms";
                    }, function (error) {
                        console.log(error);
                        $rootScope.pageLoading = false;
                    });

                    return;

                }
            }
        }]);