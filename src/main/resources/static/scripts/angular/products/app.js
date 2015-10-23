angular.module("products", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("products")
    .controller("ListProductsCtrl", ["$scope", "$location", "Products", function ($scope, $location, Products) {

        $scope.products = ViewData.products;

        $scope.totalProducts = ViewData.products.length;

    }]);


angular.module("products")
    .controller("EditProductCtrl", ["$scope", "$rootScope", "$location", "Products", "NotificationService", function ($scope, $rootScope, $location, Products, NotificationService) {

        $scope.product = ViewData.product;
        $scope.mode = ViewData.mode;

        $scope.materials = ["HDPE", "LLDPE"];
        $scope.filmColors = ["Trắng", "Xanh", "Nâu", "Vàng", "Ghi", "Be", "Bạc"];
        $scope.bagTypes = ["Tshirt", "Gripbag", "Flatbag"]
        $scope.embossTypes = ["Không nhám", "Nhám hoa", "Nhám gai", "Nhám tứ giác"];
        $scope.printingTypes = ["0C/0S", "1C/1S", "1C/2S", "2C/1S"];
        $scope.outerbagPrintingTypes = ["0C/0S", "1C/1S", "2C/1S"];

        $scope.saveProduct = function () {
            $rootScope.pageLoading = true;

            if ($scope.mode === "new") {

                Products.post($scope.product).then(function (response) {
                    var created = response.plain();
                    NotificationService.notifySuccess("Product created successfully");
                    $rootScope.pageLoading = false;

                    window.location.href = "/products";
                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

            } else if ($scope.mode === "edit") {

                Products.one($scope.product.id).customPUT($scope.product).then(function (response) {
                    var updated = response.plain();
                    NotificationService.notifySuccess("Product updated successfully");
                    $rootScope.pageLoading = false;

                    window.location.href = "/products";
                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

                return;

            }
        }
    }]);