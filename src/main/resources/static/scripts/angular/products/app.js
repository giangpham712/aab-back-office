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
            $scope.page = page;

            $scope.loadProducts();
        }

        $scope.viewProduct = function (product) {
            $window.location.href = "/products/edit/" + product.id;
        }

        $scope.deleteProduct = function (product) {
            var confirmDelete = confirm("Are you sure you want to delete this product?");
            if (!confirmDelete) {
                return;
            }

            Products.one(product.id).remove().then(function (response) {
                NotificationService.notifySuccess("Order deleted successfully");
                $scope.loadProducts();
            }, function (error) {
                NotificationService.notifySuccess("Order cannot be deleted");
            });
        }
    }]);


angular.module("products")
    .controller("EditProductCtrl", ["$scope", "$rootScope", "$location", "Products", "ModalService",  "NotificationService", function ($scope, $rootScope, $location, Products, ModalService, NotificationService) {

        $scope.product = ViewData.product;
        $scope.mode = ViewData.mode;

        $scope.materials = ["HDPE", "LLDPE"];
        $scope.filmColors = ["Trắng", "Xanh", "Nâu", "Vàng", "Ghi", "Be", "Bạc", "Olive", "Trong", "Xanh lá", "Xanh nước biển"];
        $scope.bagTypes = ["Tshirt", "Gripbag", "Flatbag"]
        $scope.embossTypes = ["Không nhám", "Nhám hoa", "Nhám gai", "Nhám tứ giác"];
        $scope.printingTypes = ["0C/0S", "1C/1S", "1C/2S", "2C/1S"];
        $scope.outerbagPrintingTypes = ["0C/0S", "1C/1S", "2C/1S"];

        $scope.boms = ViewData.boms;

        Products.one($scope.product.id).all("readings").getList().then(function (response) {
            $scope.product.readings = response.plain();
        })

        $scope.$watch("product.length", function (value, oldValue) {
            if (value == oldValue) {
                return;
            }

            $scope.product.cartonLength = parseInt($scope.product.length) + 15;
            console.log(value, $scope.product.cartonLength);
        });

        $scope.$watch("product.width", function (value, oldValue) {
            if (value == oldValue) {
                return;
            }

            $scope.product.cartonWidth = parseInt($scope.product.width) + 15;
            console.log(value, $scope.product.cartonWidth);
        });

        $scope.$watch(function() {

            var actualThickness = calculateActualThickness($scope.product.thickness);
            return  actualThickness * 4 * $scope.product.piecesPerCarton + 15;

        }, function (value, oldValue) {

            if (value == oldValue) {
                return;
            }

            var actualThickness = calculateActualThickness($scope.product.thickness);
            $scope.product.cartonHeight = value;
            console.log(actualThickness, $scope.product.piecesPerCarton, $scope.product.cartonHeight);
        });

        $scope.deleteProduct = function (product) {
            var confirmDelete = confirm("Are you sure you want to delete this product?");
            if (!confirmDelete) {
                return;
            }

            Products.one(product.id).remove().then(function (response) {
                NotificationService.notifySuccess("Product deleted successfully");
                window.location.href = "/products"
            }, function (error) {
                NotificationService.notifySuccess("Product cannot be deleted");
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

        // Readings

        $scope.showNewReading = function () {

            $scope.reading = {
                $isNew: true,
                $index: -1,
                value: 0,
                error: {}
            };

            ModalService.showModal("edit_reading");

            return true;
        }

        $scope.showEditReading = function (index) {

            var reading = $scope.product.readings[index];

            $scope.reading = angular.copy(reading);
            $scope.reading.$isNew = false;
            $scope.reading.$index = index;

            ModalService.showModal("edit_reading");

            return true;
        }

        $scope.saveReading = function () {

            $rootScope.pageLoading = true;

            if ($scope.reading.$isNew) {

                Products.one($scope.product.id).all("readings").post($scope.reading).then(function (response) {
                    console.log(response);
                    var created = response.plain();
                    $scope.product.readings.push(created);
                    $rootScope.pageLoading = false;
                    ModalService.closeModal("edit_reading");
                    NotificationService.notifySuccess("Reading created successfully");

                }, function (error) {

                });

            } else {

                Products.one($scope.product.id).one("readings", $scope.reading.id).customPUT($scope.reading).then(function (response) {
                    var updated = response.plain();

                    $scope.product.readings[$scope.reading.$index] = updated;

                    $rootScope.pageLoading = false;
                    ModalService.closeModal("edit_reading");

                    NotificationService.notifySuccess("Reading updated successfully");

                }, function (error) {

                });
            }
        }

        $scope.deleteReading = function (index) {
            var confirmDelete = confirm("Are you sure you want to delete this reading?");
            if (!confirmDelete) {
                return;
            }

            Products.one($scope.product.id).one("readings", $scope.product.readings[index].id).remove().then(function (response) {
                $scope.product.readings.splice(index, 1);
                NotificationService.notifySuccess("Reading deleted successfully");
            }, function (error) {
                NotificationService.notifySuccess("Reading cannot be deleted");
            });
        }

        function calculateActualThickness(thickness) {
            var tolerance;
            if (thickness < 0.013) {
                tolerance = 0;
            } else if (thickness < 0.014) {
                tolerance = 2;
            } else {
                tolerance = 5;
            }

            return (100 - tolerance) * thickness / 100;
        }
    }]);