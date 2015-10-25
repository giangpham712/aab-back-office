angular.module("orders", ["AAB.models", "AAB.rest", "AAB.services", "AAB.schemas", "AAB.directives", "angularUtils.directives.dirPagination"]);

angular.module("orders")
    .controller("ListOrdersCtrl", ["$scope", "$window", "Orders", function ($scope, $window, Orders) {

        $scope.orders = window.ViewData.orders;
        var customers = ViewData.customers;
        var customerMap = _.indexBy(customers, "id");

        $scope.orders.forEach(function (order) {
            order.customerName = customerMap[order.customerId].displayName;
        });

        $scope.totalOrders = 10;

        $scope.searchKey = null;
        $scope.page = ViewData.page;
        $scope.limit = ViewData.limit;

        $scope.loadOrders = function () {

            Orders.getList({q: $scope.searchKey, page: $scope.page})
                .then(function (response) {
                    $scope.orders = response.plain();

                    $scope.orders.forEach(function (order) {
                        order.customerName = customerMap[order.customerId].displayName;
                    });
                }, function (error) {
                    console.log(error);
                });
        };

        $scope.searchOrders = function () {
            $scope.page = 1;

            $scope.loadOrders();
        }

        $scope.viewOrder = function (order) {
            $window.location.href = "/orders/edit/" + order.id;
        }
    }]);

angular.module("orders")
    .controller("EditOrderCtrl", [
        "$scope",
        "$rootScope",
        "$q",
        "Products",
        "Orders",
        "NotificationService",
        "ORDER_ITEM_SCHEMA",
        function ($scope,
                  $rootScope,
                  $q,
                  Products,
                  Orders,
                  NotificationService,
                  ORDER_ITEM_SCHEMA) {

            $scope.mode = window.ViewData.mode;

            $scope.customers = window.ViewData.customers;
            $scope.items = window.ViewData.items;
            $scope.estimate = window.ViewData.estimate;

            var productMap;
            Products.getList().then(function (response) {
                $scope.products = response.plain();
                productMap = _.indexBy($scope.products, "id");

                angular.forEach(ViewData.order.items, function (item) {
                    if (productMap[item.productId]) {
                        item.itemName = productMap[item.productId].name;
                    }
                });

            }, function (error) {
                $scope.products = [];
            });

            $scope.materials = ["HDPE", "LLDPE"];
            $scope.filmColors = ["Trắng", "Xanh", "Nâu", "Vàng", "Ghi", "Be", "Bạc"];
            $scope.bagTypes = ["Tshirt", "Gripbag", "Flatbag"]
            $scope.embossTypes = ["Không nhám", "Nhám hoa", "Nhám gai", "Nhám tứ giác"];
            $scope.printingTypes = ["0C/0S", "1C/1S", "1C/2S", "2C/1S"];
            $scope.outerbagPrintingTypes = ["0C/0S", "1C/1S", "2C/1S"];

            var orderItem = {
                productId: 0,
                thickness: 0.00,
                width: 0,
                blowingWidth: 0,
                length: 0,
                quantity: 0,
                piecesPerCarton: 0,
                piecesPerOuterbag: 0,
                totalCartons: 0,
                unitPrice: 0,
                errors: []
            }

            var loadedItems = {};


            $scope.fillProductInfo = fillProductInfo;

            $scope.$watch("editingItem.productId", function (newValue, oldValue) {

                if ($scope.editingItem == null)
                    return;

                if (newValue && productMap[newValue]) {
                    $scope.editingItem.itemName = productMap[newValue].name;
                } else if (newValue == undefined) {
                    $scope.editingItem.itemName = "";
                } else {

                }

            });

            // Calculate total quantity by multiplying number of pieces per box with total number of boxes
            $scope.$watch("editingItem", function (newValue, oldValue) {
                if (oldValue == null || newValue == null) {
                    return;
                }

                if (oldValue.itemIndex != newValue.itemIndex) {
                    return;
                }

                if (oldValue.piecesPerCarton * oldValue.totalCartons != newValue.piecesPerCarton * newValue.totalCartons) {
                    $scope.editingItem.quantity = newValue.piecesPerCarton * newValue.totalCartons;
                }

            }, true)


            $scope.order = ViewData.order;

            var getSize = function (item) {
                return (parseFloat(item.thickness) || 0).toFixed(3) + " x " +
                    (parseFloat(item.width) || 0).toFixed(0) + " / " +
                    (parseFloat(item.blowingWidth) || 0).toFixed(0) + " x " +
                    (parseFloat(item.length) || 0).toFixed(0);
            }

            var getLineTotal = function (item) {
                return item.quantity * item.unitPrice;
            }

            var isInvalid = function (item) {
                return item.errors && item.errors.length > 0;
            }

            var orderTotal = function () {
                return _.reduce($scope.order.items, function (memo, item) {
                    return memo + getLineTotal(item);
                }, 0)
            }

            $scope.getLineTotal = getLineTotal;
            $scope.getSize = getSize;
            $scope.isInvalid = isInvalid;
            $scope.orderTotal = orderTotal;
            $scope.generateProductionSheet = generateProductionSheet;

            $scope.addItem = addItem;

            $scope.removeItem = removeItem;

            $scope.saveOrder = saveOrder;

            function addItem() {
                $scope.order.items.push(angular.copy(orderItem))
            }

            function validateOrderItems(order) {
                var result = {
                    errors: []
                };

                _.forEach(order.items, function (item, index) {

                    var itemValidation = tv4.validateMultiple(item, ORDER_ITEM_SCHEMA);

                    item.errors = itemValidation.errors;

                    result.errors = result.errors.concat(item.errors);
                });

                return result;
            }

            function clearValidationErrors() {
                $scope.order.orderNumberError = "";
                $scope.order.orderDateError = "";
                $scope.order.estimatedTimeOfArrivalError = "";
                $scope.order.estimatedTimeOfDepartureError = "";
            }

            function saveOrder() {
                clearValidationErrors();

                if ($scope.editOrderForm.$invalid) {

                    var firstControl = null
                    if ($scope.editOrderForm.$error.required) {
                        angular.forEach($scope.editOrderForm.$error.required, function (error) {

                            var control = angular.element("input[name=" + error.$name + "]");
                            var label = control.data("label");

                            $scope.order[error.$name + "Error"] = label + " is required";

                            firstControl = firstControl || control;
                        });
                    }

                    setTimeout(function () {
                        firstControl.focus()
                    }, 0);

                    return;
                }

                var validationResult = validateOrderItems($scope.order);
                if (validationResult.errors.length > 0) {
                    console.log(validationResult.errors);
                    return;
                }

                $scope.order.items.forEach(function (item) {
                    item.total = getLineTotal(item);
                });

                $scope.order.total = orderTotal();

                $rootScope.pageLoading = true;

                if ($scope.mode === 'new') {

                    Orders.post($scope.order).then(function (response) {
                        var created = response.plain();
                        NotificationService.notifySuccess("Order created successfully");
                        window.location.href = "/orders"
                        $rootScope.pageLoading = false;

                    }, function (error) {
                        console.log(error);
                        $rootScope.pageLoading = false;
                    });

                } else {
                    Orders.one($scope.order.id).customPUT($scope.order).then(function (response) {
                        var updated = response.plain();

                        NotificationService.notifySuccess("Order updated successfully");

                        console.log(updated);
                        $rootScope.pageLoading = false;

                    }, function (error) {
                        console.log(error);
                        $rootScope.pageLoading = false;
                    });
                }
            }

            function removeItem() {
                event.preventDefault();
                if ($scope.order.items.length <= 1) {
                    return;
                }
                var itemIndex = $scope.editingItem.itemIndex;
                $scope.editingItem = null;
                $scope.order.items.splice(itemIndex, 1);
            }

            function loadItemInformation(productId) {

                var deferred = $q.defer();

                if (loadedItems[productId] != null) {
                    deferred.resolve(loadedItems[productId]);
                } else {

                    $rootScope.pageLoading = true;

                    Products.one(productId).get().then(function (response) {
                        var item = response.plain();

                        loadedItems[item.id] = item;
                        deferred.resolve(item);

                        $rootScope.pageLoading = false;
                    }, function (error) {
                        deferred.reject(error);
                        $rootScope.pageLoading = false;
                    });
                }

                return deferred.promise;
            }

            function fillProductInfo(event, item) {
                event.preventDefault();

                if (!item.productId) {
                    return;
                }

                loadItemInformation(item.productId).then(function (data) {

                    $scope.editingItem.thickness = data.thickness;
                    $scope.editingItem.width = data.width;
                    $scope.editingItem.blowingWidth = data.blowingWidth;
                    $scope.editingItem.length = data.length;
                    $scope.editingItem.emboss = data.emboss;
                    $scope.editingItem.handleWidth = data.handleWidth;
                    $scope.editingItem.handleLength = data.handleLength;
                    $scope.editingItem.material = data.material;
                    $scope.editingItem.filmColor = data.filmColor;
                    $scope.editingItem.bagType = data.bagType;
                    $scope.editingItem.printing = data.printing;
                    $scope.editingItem.outerbagPrinting = data.outerbagPrinting;
                    $scope.editingItem.piecesPerOuterbag = data.piecesPerOuterbag;
                    $scope.editingItem.piecesPerCarton = data.piecesPerCarton;

                }, function (error) {

                });
            }

            function generateProductionSheet() {
                window.location.href = "/orders/productionsheet/" + $scope.order.id;
            }
        }]);


angular.module("orders")
    .controller("OrderProductionSheetCtrl", ["$scope", "$http", "Products", function ($scope, $http, Products) {

        $scope.order = ViewData.order;
        $scope.order.orderName = $scope.order.orderNumber;

        $scope.productionSheetOrder = ViewData.productionSheetOrder;

        var productMap;
        Products.getList().then(function (response) {
            $scope.products = response.plain();
            productMap = _.indexBy($scope.products, "id");

            angular.forEach(ViewData.order.items, function (item) {
                if (productMap[item.productId]) {
                    item.itemName = productMap[item.productId].name;
                }
            });

        }, function (error) {
            $scope.products = [];
        });

        $scope.generateProductionSheet = generateProductionSheet;

        function generateProductionSheet() {
            var request = $http({
                method: "POST",
                url: "/orders/productionsheet/" + $scope.order.id,
                data: {

                    orderName: $scope.order.orderName
                }
            }).then(function (response) {
                console.log(response);
            }, function (error) {
                console.log(error);
            })


        }

    }])
;

