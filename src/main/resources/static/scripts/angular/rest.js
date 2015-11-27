angular.module("AAB.rest", ["restangular"]);

angular.module("AAB.rest").config(function (RestangularProvider) {
    RestangularProvider.setBaseUrl('/api');
});

angular.module("AAB.rest")
    .service("Products", ["Restangular", function (Restangular) {
        var Products = Restangular.service("products");
        Restangular.extendModel('products', function (model) {
            return model;
        });
        return Products;
    }]);

angular.module("AAB.rest")
    .service("Materials", ["Restangular", function (Restangular) {
        var Materials = Restangular.service("materials");
        Restangular.extendModel('materials', function (model) {
            return model;
        });
        return Materials;
    }]);

angular.module("AAB.rest")
    .service("Expenses", ["Restangular", function (Restangular) {
        var Expenses = Restangular.service("expenses");
        Restangular.extendModel('expenses', function (model) {
            return model;
        });
        return Expenses;
    }]);

angular.module("AAB.rest")
    .service("BOMs", ["Restangular", function (Restangular) {
        var BOMs = Restangular.service("boms");
        Restangular.extendModel('boms', function (model) {
            return model;
        });
        return BOMs;
    }]);


angular.module("AAB.rest")
    .service("Orders", ["Restangular", function (Restangular) {
        var Orders = Restangular.service("orders");

        Restangular.extendModel('orders', function (model) {
            return model;
        });

        return Orders;
    }]);

angular.module("AAB.rest")
    .service("Estimates", ["Restangular", function (Restangular) {
        var Estimates = Restangular.service("estimates");

        Restangular.extendModel('estimates', function (model) {
            return model;
        });

        return Estimates;
    }]);

angular.module("AAB.rest")
    .service("Customers", ["Restangular", function (Restangular) {
        var Customers = Restangular.service("customers");

        Restangular.extendModel('customers', function (model) {
            return model;
        });

        return Customers;
    }]);

angular.module("AAB.rest")
    .service("Machines", ["Restangular", function (Restangular) {
        var Machines = Restangular.service("machines");
        Restangular.extendModel("machines", function (model) {
            return model;
        });
        return Machines;
    }]);