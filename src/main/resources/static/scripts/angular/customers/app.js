angular.module("customers", ["AAB.rest", "angularUtils.directives.dirPagination"]);

angular.module("customers")
    .controller("ListCustomersCtrl", ["$rootScope", "$scope", "Customers", "ModalService", function ($rootScope, $scope, Customers, ModalService) {
        $scope.customers = ViewData.customers;

        $scope.showNewCustomer = function () {

            ModalService.showModal("new_customer");
            $scope.newCustomer = {
                error: {}
            }

            return true;
        }

        $scope.saveNewCustomer = function () {

            $rootScope.pageLoading = true;

            Customers.post($scope.newCustomer).then(function (response) {
                var created = response.plain();
                $scope.customers.push(created);
                $rootScope.pageLoading = false;
                ModalService.closeModal("new_customer");
            });
        }
    }]);


angular.module("customers")
    .run(function ($rootScope) {
        $rootScope.pageLoading = false;
    });


angular.module("customers")
    .directive("ngModal", function () {
        return {
            restrict: "AE",
            scope: {
                modalId: '@',
            },
            controller: function ($scope, $element, $attrs, ModalService) {

                $element.modal({
                    show: false
                });

                ModalService.registerModal($scope.modalId, $element);
            }
        }
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


angular.module("customers")
    .factory("ModalService", function () {
        var modalService = {
            modals: {},
            registerModal: function (name, modal) {
                this.modals[name] = modal;
            },
            showModal: function (name) {
                this.modals[name].modal("show");
            },
            closeModal: function (name) {
                this.modals[name].modal("hide");
            }
        };

        return modalService;
    });

