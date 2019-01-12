angular.module("customers", ["AAB.rest", "AAB.services", "AAB.directives", "angularUtils.directives.dirPagination"]);

angular.module("customers")
    .controller("ListCustomersCtrl", ["$rootScope", "$scope", "Customers", "NotificationService", "ModalService", function ($rootScope, $scope, Customers, NotificationService, ModalService) {
        $scope.customers = ViewData.customers;

        $scope.showNewCustomer = function () {

            $scope.customer = {
                $isNew: true,
                $index: -1,
                error: {}
            };

            ModalService.showModal("edit_customer");

            return true;
        }

        $scope.showEditCustomer = function (index) {

            var customer = $scope.customers[index];

            $scope.customer = angular.copy(customer);
            $scope.customer.$isNew = false;
            $scope.customer.$index = index;

            ModalService.showModal("edit_customer");

            return true;
        }

        $scope.saveCustomer = function () {

            $rootScope.pageLoading = true;

            if ($scope.customer.$isNew) {

                Customers.post($scope.customer).then(function (response) {
                    var created = response.plain();
                    $scope.customers.push(created);
                    $rootScope.pageLoading = false;
                    ModalService.closeModal("edit_customer");
                    NotificationService.notifySuccess("Customer created successfully");

                }, function (error) {

                });

            } else {

                Customers.one($scope.customer.id).customPUT($scope.customer).then(function (response) {
                    var updated = response.plain();

                    $scope.customers[$scope.customer.$index] = updated;

                    $rootScope.pageLoading = false;
                    ModalService.closeModal("edit_customer");

                    NotificationService.notifySuccess("Customer updated successfully");

                }, function (error) {

                });

            }
        }
    }]);


angular.module("customers")
    .run(function ($rootScope) {
        $rootScope.pageLoading = false;
    });

angular.module("customers")
    .directive("icheck", function () {
        return {
            restrict: "AE",
            link: function (scope, element, attributes) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue',
                    increaseArea: '20%' // optional
                });
            }
        }
    });




