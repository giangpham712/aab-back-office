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

        EstimateItem.prototype.getCostPerWeightUnit = function () {

            var materialsCost = _.reduce(this.materials || [], function (memo, item) {
                return memo + parseFloat(item.quantity) * item.costPerWeightUnit;
            }, 0);

            var expensesCost = _.reduce(this.expenseItems || [], function (memo, item) {
                return memo + parseFloat(item.total);
            }, 0);

            return parseFloat(materialsCost) + parseFloat(expensesCost);
        }

        EstimateItem.prototype.getTotal = function () {
            return this.getCostPerWeightUnit() * this.actualTotalWeight / 1000;
        }

        return EstimateItem;

    });

angular.module("AAB.models")
    .factory("WorkOrder", function (WorkOrderItem) {

        var WorkOrder = function (workOrder) {
            angular.extend(this, workOrder);
            this.items = workOrder.items.map(function (item) {
                return new WorkOrderItem(item);
            });
        };

        return WorkOrder;

    });

angular.module("AAB.models")
    .factory("WorkOrderItem", function () {

        var WorkOrder = function (item) {
            angular.extend(this, item);

            this.weightPerLengthUnit = this.thickness * this.blowingWidth * 2 * 9 / 10;
            this.lengthPerRoll = this.calculateLengthPerRoll();
            this.weightPerRoll = this.lengthPerRoll * this.weightPerLengthUnit;
            this.totalBlowingWeight = this.totalWeight * 1.16;
            this.totalRolls = Math.ceil(this.totalBlowingWeight / this.weightPerRoll * 1000);

        };

        WorkOrder.prototype.calculateLengthPerRoll = function () {
            var lengthPerRoll = 4000;
            while (lengthPerRoll * this.weightPerLengthUnit / 1000 > 90) {
                lengthPerRoll = lengthPerRoll - 500;
            }

            return lengthPerRoll;
        };

        return WorkOrder;


    });