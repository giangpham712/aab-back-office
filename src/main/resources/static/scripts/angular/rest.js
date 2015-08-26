angular.module("AAB.rest", ["restangular"]);

angular.module("AAB.rest").config(function (RestangularProvider) {
    RestangularProvider.setBaseUrl('/api');
});

angular.module("AAB.rest")
    .service("Items", ["Restangular", function (Restangular) {
        var Item = Restangular.service("items");

        Restangular.extendModel('items', function (model) {
            return model;
        });

        return Item;
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