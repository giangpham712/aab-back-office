angular.module("orders", ["AAB.models", "AAB.rest", "AAB.services", "AAB.schemas", "AAB.directives", "angularUtils.directives.dirPagination"]);

angular.module("orders")
    .controller("ListOrdersCtrl", ["$scope", "$window", function ($scope, $window) {

        $scope.orders = window.ViewData.orders;

        $scope.totalOrders = 10;

        $scope.viewOrder = function (order) {
            $window.location.href = "/orders/edit/" + order.id;
        }
    }]);

angular.module("orders")
    .controller("EditOrderCtrl", ["$scope", "$rootScope", "$q", "Items", "Orders", "NotificationService", "ORDER_ITEM_SCHEMA", function ($scope, $rootScope, $q, Items, Orders, NotificationService, ORDER_ITEM_SCHEMA) {

        $scope.mode = window.ViewData.mode;

        $scope.customers = window.ViewData.customers;
        $scope.items = window.ViewData.items;
        $scope.estimate = window.ViewData.estimate;

        var itemMap = _.indexBy($scope.items, "id");

        var orderItem = {
            itemId: 0,
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

        var loadItemInformation = function (itemId) {

            var deferred = $q.defer();

            if (loadedItems[itemId] != null) {
                deferred.resolve(loadedItems[itemId]);
            } else {

                $rootScope.pageLoading = true;

                Items.one(itemId).get().then(function (response) {
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

        var getAttributeValue = function (attributes, key) {
            return attributes[key] == null ? null : attributes[key].attributeValue;
        }

        $scope.autofillItemInfo = function (item) {

            if (!(item.itemId && item.itemId != 0)) {
               return;
            }

            loadItemInformation(item.itemId).then(function (data) {
                var attributes = _.indexBy(data.attributes, "attributeKey");

                $scope.editingItem.thickness = getAttributeValue(attributes, "thickness");
                $scope.editingItem.width = getAttributeValue(attributes, "width");
                $scope.editingItem.blowingWidth = getAttributeValue(attributes, "blowingWidth");
                $scope.editingItem.length = getAttributeValue(attributes, "length");
                $scope.editingItem.material = getAttributeValue(attributes, "material");
                $scope.editingItem.filmColor = getAttributeValue(attributes, "filmColor");
                $scope.editingItem.bagType = getAttributeValue(attributes, "bagType");
                $scope.editingItem.printing = getAttributeValue(attributes, "printing");
                $scope.editingItem.outerbagPrinting = getAttributeValue(attributes, "outerbagPrinting");
                $scope.editingItem.piecesPerOuterbag = getAttributeValue(attributes, "piecesPerOuterbag");

            }, function (error) {

            });

        }

        $scope.$watch("editingItem.itemId", function (newValue, oldValue) {

            if ($scope.editingItem == null)
                return;

            if (newValue && itemMap[newValue]) {
                $scope.editingItem.itemName = itemMap[newValue].displayName;
            } else if (newValue == undefined) {
                $scope.editingItem.itemName = "";
            } else {

            }

        });

        // Calculate total quantity by multiplying number of pieces per box with total number of boxes
        $scope.$watch("editingItem", function (newValue, oldValue) {
            if (oldValue == null || newValue  == null) {
                return;
            }

            if (oldValue.itemIndex != newValue.itemIndex) {
                return;
            }

            if (oldValue.piecesPerCarton * oldValue.totalCartons != newValue.piecesPerCarton * newValue.totalCartons) {
                $scope.editingItem.quantity = newValue.piecesPerCarton * newValue.totalCartons;
            }

        }, true)

        angular.forEach(ViewData.order.items, function (item) {
            item.itemName = itemMap[item.itemId].displayName;
        });

        $scope.order = angular.merge({
            orderNumber: "",
            customerId: 1,
            items: [
                angular.copy(orderItem)
            ],
            orderDate: moment().format("DD/MM/YYYY"),
            estimatedTimeOfArrival: moment().format("DD/MM/YYYY"),
            estimatedTimeOfDeparture: moment().format("DD/MM/YYYY"),
            total: 0.00,


        }, ViewData.order);

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

        $scope.addItem = function () {
            $scope.order.items.push(angular.copy(orderItem))
        }

        $scope.clearLines = function () {
            $scope.order.items = [];
        }

        var validateOrderItems = function (order) {
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

        var clearValidationErrors = function () {
            $scope.order.orderNumberError = "";
            $scope.order.orderDateError = "";
            $scope.order.estimatedTimeOfArrivalError = "";
            $scope.order.estimatedTimeOfDepartureError = "";
        }

        $scope.removeItem = function () {
            if ($scope.order.items.length <= 1) {
                return;
            }
            var itemIndex = $scope.editingItem.itemIndex;
            $scope.editingItem = null;
            $scope.order.items.splice(itemIndex, 1);

        }

        $scope.saveOrder = function () {

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
                    console.log(created);
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
    }]);


angular.module("orders")
    .controller("EditEstimateCtrl", ["$scope", "Items", "Estimates", "Estimate", function ($scope, Items, Estimates, Estimate) {

        var assemblyItemMap = _.indexBy(ViewData.assemblyItems, "id");
        var inventoryItemMap = _.indexBy(ViewData.inventoryItems, "id");

        $scope.expenseItems = ViewData.expenseItems;
        $scope.editingItem = null;

        $scope.estimate = new Estimate(ViewData.estimate);

        $scope.estimate.items.forEach(function (item) {
            item.itemName = assemblyItemMap[item.itemId].displayName;
            Items.one(item.itemId).getList("boms").then(function (response) {
                item.boms = response.plain();
            }, function (error) {
                console.log(error);
            });
        });

        var selectBOM = function (bomId) {

            var bom = _.findWhere($scope.editingItem.boms, {id: parseInt(bomId)});

            if (bom) {
                angular.forEach(bom.items, function (item) {
                    var inventoryItem = inventoryItemMap[item.itemId];
                    if (inventoryItem) {
                        item.itemName = inventoryItem.displayName;
                        var attributeMap = _.indexBy(inventoryItem.attributes, "attributeKey");

                        item.costPerWeightUnit = attributeMap["unitCost"].attributeValue;
                    }
                });
                $scope.editingItem.materials = angular.copy(bom.items);
            } else {
                $scope.editingItem.materials = null;
            }
        };

        var addNewExpenseItem = function () {
            if (!$scope.editingItem.expenseItems) {
                $scope.editingItem.expenseItems = [];
            }

            $scope.editingItem.expenseItems.push({
                itemId: $scope.expenseItems[0].id,
                total: 0,
            });

        }

        var showEditCaculationItem = function (item, index) {

            $scope.editingItem = item;
            $scope.editingItem.index = index;
            $("#modal_item_production_cost").modal("show");
        }

        var saveCalculation = function () {
            $scope.estimate.items[$scope.editingItem.index].materials = $scope.editingItem.materials;
            $("#modal_item_production_cost").modal("hide");

        }

        var saveEstimate = function () {

            $scope.estimate.items.forEach(function (item) {

                if (item.materials) {
                    item.materials.forEach(function (material) {
                        material.total = parseFloat(material.costPerWeightUnit) * parseFloat(material.quantity);
                    })
                }

                item.costItems = (item.materials || []).concat((item.expenseItems || []));
            });

            Estimates.post($scope.estimate).then(function (response) {

                console.log(response);
            }, function (error) {

            })
        }

        // Scope functions
        $scope.selectBOM = selectBOM;
        $scope.addNewExpenseItem = addNewExpenseItem;
        $scope.showEditCaculationItem = showEditCaculationItem;
        $scope.saveCalculation = saveCalculation;
        $scope.saveEstimate = saveEstimate;
    }]);


