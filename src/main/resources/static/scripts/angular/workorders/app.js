angular.module("workorders", ["AAB.models", "AAB.rest", "AAB.services", "AAB.schemas", "AAB.directives", "angularUtils.directives.dirPagination"]);

angular.module("workorders")
    .controller("EditWorkOrderCtrl", ["$scope", "$window", "WorkOrder", function ($scope, $window, WorkOrder) {

        var assemblyItemMap = _.indexBy(ViewData.assemblyItems, "id");
        var inventoryItemMap = _.indexBy(ViewData.inventoryItems, "id");
        var expenseItemMap = _.indexBy(ViewData.expenseItems, "id");

        var estimate = ViewData.estimate;
        var estimateItemMap = _.indexBy(estimate.items, "itemId");

        $scope.workOrder = new WorkOrder(ViewData.workOrder);

        $scope.workOrder.rawMaterialMap = {};

        $scope.workOrder.items.forEach(function (item) {
            item.itemName = assemblyItemMap[item.itemId].displayName;

            item.rawMaterials = estimateItemMap[item.itemId].costItems.filter(function (costItem) {
                return costItem.type === "INVENTORY_ITEM";
            });

            item.rawMaterials.forEach(function (material) {
                material.itemName = inventoryItemMap[material.itemId].displayName;
                material.quantity = Math.ceil(material.quantity * (item.totalWeight / 1000));

                $scope.workOrder.rawMaterialMap[material.itemId] = ($scope.workOrder.rawMaterialMap[material.itemId] || 0) + material.quantity;

                material.quantityOnHand = inventoryItemMap[material.itemId].quantityOnHand;
            });
        });

        // Scope functions

        $scope.saveWorkOrder = function () {

        };
    }]);