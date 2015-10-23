angular.module("estimates", ["AAB.models", "AAB.rest", "AAB.services", "AAB.schemas", "AAB.directives", "angularUtils.directives.dirPagination"]);

angular.module("estimates")
    .controller("EditEstimateCtrl",
    [
        "$scope",
        "Items",
        "Estimates",
        "BOMs",
        "Estimate",
        "ModalService",
        "NotificationService",
        function ($scope,
                  Items,
                  Estimates,
                  BOMs,
                  Estimate,
                  ModalService,
                  NotificationService) {

            var productMap = _.indexBy(ViewData.products, "id");
            var materialMap = _.indexBy(ViewData.materials, "id");
            var expenseMap = _.indexBy(ViewData.expenses, "id");

            $scope.expenses = ViewData.expenses;
            $scope.editingItem = null;

            $scope.estimate = new Estimate(ViewData.estimate);

            angular.forEach($scope.estimate.items, function (item) {

                item.itemName = productMap[item.productId].name;

            });

            BOMs.getList().then(function (response) {
                $scope.billsOfMaterials = response.plain();
            });

            var saveEstimate = function () {

                if (ViewData.mode === "new") {
                    Estimates.post($scope.estimate).then(function (response) {
                        NotificationService.notifySuccess("Estimate saved successfully");
                    }, function (error) {
                        NotificationService.notifyError("Estimate cannot be saved due to errors");
                    })
                } else {
                    Estimates.one($scope.estimate.id).customPUT($scope.estimate).then(function (response) {
                        NotificationService.notifySuccess("Estimate saved successfully");
                    }, function (error) {
                        NotificationService.notifyError("Estimate cannot be saved due to errors");
                    })
                }
            }

            var showEditCaculationItem = function (item, index) {
                $scope.editingItem = angular.copy(item);
                ModalService.showModal("item_production_cost");
            }

            var applyBOM = function (bomId) {
                if (!bomId) {
                    return;
                }

                BOMs.one(bomId).get().then(function (response) {
                    var bom = response.plain();
                    var totalQuantity = _.reduce(bom.items, function (memo, item) { return parseFloat(memo) + parseFloat(item.quantity); }, 0);
                    $scope.editingItem.materials = bom.items.map(function (item) {
                        var material = {};
                        material.quantity = item.quantity;
                        material.itemName = materialMap[item.id].name;
                        material.costPerWeightUnit = materialMap[item.id].unitCost;

                        return material;
                    });
                });
            };

            var addNewExpenseItem = function () {
                $scope.editingItem.expenses.push({});
            }

            var saveCalculation = function () {
                console.log($scope.editingItem);
            }

            // Scope functions
            $scope.saveEstimate = saveEstimate;

            $scope.showEditCaculationItem = showEditCaculationItem;

            $scope.applyBOM = applyBOM;

            $scope.addNewExpenseItem = addNewExpenseItem;

            $scope.saveCalculation = saveCalculation;
        }]);