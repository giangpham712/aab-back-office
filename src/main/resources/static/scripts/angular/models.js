angular.module("AAB.models", []);

angular.module("AAB.models")
    .factory("Estimate", function (EstimateItem) {

        var Estimate = function (estimate) {
            angular.extend(this, estimate);
            this.items = estimate.items.map(function (item) {
                return new EstimateItem(item);
            });
        }

        Estimate.prototype.getTotal = function () {
            return _.reduce(this.items, function (memo, item) {
                return memo + item.total;
            }, 0);
        }

        Estimate.prototype.getTotalProductionCost = function () {
            return _.reduce(this.items, function (memo, item) {
                return memo + item.getTotal();
            }, 0);
        }

        return Estimate;
    });

angular.module("AAB.models")
    .factory("EstimateItem", function () {

        var EstimateItem = function (item) {
            angular.extend(this, item);
        }

        EstimateItem.prototype.getTotalMaterialQuantity = function () {
            var materialQuantity = _.reduce(this.materials || [], function (memo, material) {
                return memo + parseFloat(material.quantity);
            }, 0);

            return materialQuantity;
        }

        EstimateItem.prototype.getTotalMaterialCost = function () {
            var materialsCost = _.reduce(this.materials || [], function (memo, item) {
                return memo + parseFloat(item.quantity) * item.costPerWeightUnit;
            }, 0);

            return materialsCost;
        }

        EstimateItem.prototype.getTotalMaterialCostPerTon = function () {
            var materialsCost = this.getTotalMaterialCost() / this.getTotalMaterialQuantity() * 1000;

            if (isNaN(materialsCost)) {
                materialsCost = 0;
            }

            return materialsCost;
        }

        EstimateItem.prototype.getTotalExpenseCostPerTon = function () {
            var expensesCost = _.reduce(this.expenses || [], function (memo, item) {
                return memo + parseFloat(item.total);
            }, 0);

            if (isNaN(expensesCost)) {
                expensesCost = 0;
            }

            return expensesCost;
        }

        EstimateItem.prototype.getCostPerWeightUnit = function () {

            var materialsCost = this.getTotalMaterialCostPerTon();
            var expensesCost = this.getTotalExpenseCostPerTon();

            return parseFloat(materialsCost) + parseFloat(expensesCost);
        }

        EstimateItem.prototype.getTotal = function () {
            return this.getCostPerWeightUnit() * this.actualTotalWeight / 1000;
        }

        return EstimateItem;

    });
