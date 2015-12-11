angular.module("products", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("products")
    .controller("ListProductsCtrl", ["$scope", "$location", "Products", "NotificationService", function ($scope, $location, Products, NotificationService) {

        $scope.products = ViewData.products;

        $scope.totalProducts = ViewData.totalProducts;

        $scope.searchKey = null;
        $scope.page = ViewData.page;
        $scope.limit = ViewData.limit;

        $scope.loadProducts = function () {

            Products.getList({q: $scope.searchKey, page: $scope.page})
                .then(function (response) {
                    $scope.products = response.plain();

                }, function (error) {
                    console.log(error);
                });
        };

        $scope.searchProducts = function () {
            $scope.page = 1;

            $scope.loadProducts();
        }

        $scope.pageChanged = function (page) {
            console.log(page);
        }

        $scope.viewProduct = function (product) {
            $window.location.href = "/products/edit/" + product.id;
        }

        $scope.deleteProduct = function (product) {
            var confirmDelete = confirm("Are you sure you want to delete this product?");
            if (!confirmDelete) { return; }

            Products.one(product.id).remove().then(function (response) {
                NotificationService.notifySuccess("Order deleted successfully");
                $scope.loadProducts();
            }, function (error) {
                NotificationService.notifySuccess("Order cannot be deleted");
            });
        }
    }]);


angular.module("products")
    .controller("EditProductCtrl", ["$scope", "$rootScope", "$location", "Products", "NotificationService", function ($scope, $rootScope, $location, Products, NotificationService) {

        $scope.product = ViewData.product;
        $scope.mode = ViewData.mode;

        $scope.materials = ["HDPE", "LLDPE"];
        $scope.filmColors = ["Trắng", "Xanh", "Nâu", "Vàng", "Ghi", "Be", "Bạc", "Olive", "Trong", "Xanh lá", "Xanh nước biển"];
        $scope.bagTypes = ["Tshirt", "Gripbag", "Flatbag"]
        $scope.embossTypes = ["Không nhám", "Nhám hoa", "Nhám gai", "Nhám tứ giác"];
        $scope.printingTypes = ["0C/0S", "1C/1S", "1C/2S", "2C/1S"];
        $scope.outerbagPrintingTypes = ["0C/0S", "1C/1S", "2C/1S"];

        $scope.boms = ViewData.boms;


        $scope.$watch("product.length", function (value, oldValue) {
           console.log(value, oldValue);
        });

        $scope.deleteProduct = function (product) {
            var confirmDelete = confirm("Are you sure you want to delete this product?");
            if (!confirmDelete) { return; }

            Products.one(product.id).remove().then(function (response) {
                NotificationService.notifySuccess("Order deleted successfully");
                window.location.href = "/orders"
            }, function (error) {
                NotificationService.notifySuccess("Order cannot be deleted");
            });
        }

        $scope.setActive = function (product) {

        }

        $scope.setInactive = function (product) {

        }

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