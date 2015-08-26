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

            this.blowingWidth = this.blowingWidth;
            this.width = this.width;
            this.gusset = this.gusset;
            this.length = this.length;
            this.unitWeight = (this.width + this.gusset * 2) * this.length * 0.095 * this.thickness * 2 / 100;
            this.totalWeight = this.unitWeight * this.quantity / 1000;
            this.pricePerWeightUnit = this.total / this.totalWeight;

            // Cost calculation
            this.actualThickness = this.calculateActualThickness(this.thickness);
            this.actualUnitWeight = (this.width + this.gusset * 2) * this.length * 0.09 * this.actualThickness * 2 * 0.9 / 100;
            this.actualTotalWeight = this.actualUnitWeight * this.quantity / 1000;
            this.actualPricePerWeightUnit = this.total / this.actualTotalWeight;
        }

        EstimateItem.prototype.calculateActualThickness = function () {

            var tolerance;
            if (this.thickness < 0.013) {
                tolerance = 0;
            } else if (this.thickness < 0.014) {
                tolerance = 2;
            } else {
                tolerance = 5;
            }

            return (100 - tolerance) * this.thickness / 100;
        }

        EstimateItem.prototype.getTotalQuantity = function () {
            return _.reduce(this.materials || [], function (memo, item) {
                return memo + parseFloat(item.quantity);
            }, 0);
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
