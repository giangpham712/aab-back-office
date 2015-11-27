angular.module("machines", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("machines")
    .controller("ListMachinesCtrl", ["$scope", "$location", "Machines", "NotificationService", function ($scope, $location, Machines, NotificationService) {

        $scope.machines = ViewData.machines;

        $scope.totalMachines = ViewData.machines.length;

        $scope.loadMachines = function () {

            Machines.getList({q: $scope.searchKey, page: $scope.page})
                .then(function (response) {
                    $scope.machines = response.plain();

                }, function (error) {
                    console.log(error);
                });
        };

        $scope.viewMachine = function (machine) {
            $window.location.href = "/machines/edit/" + machine.id;
        }

        $scope.deleteMachine = function (machine) {
            var confirmDelete = confirm("Are you sure you want to delete this machine?");
            if (!confirmDelete) { return; }

            Machines.one(machine.id).remove().then(function (response) {
                NotificationService.notifySuccess("Machine deleted successfully");
                $scope.loadMachines();
            }, function (error) {
                NotificationService.notifySuccess("Machine cannot be deleted");
            });
        }

    }]);


angular.module("machines")
    .controller("EditMachineCtrl", ["$scope", "$rootScope", "$location", "Machines", "NotificationService", function ($scope, $rootScope, $location, Machines, NotificationService) {

        $scope.machine = ViewData.machine;
        $scope.mode = ViewData.mode;

        $scope.saveMachine = function () {
            $rootScope.pageLoading = true;

            if ($scope.mode === "new") {

                Machines.post($scope.machine).then(function (response) {
                    var created = response.plain();
                    NotificationService.notifySuccess("Machine created successfully");
                    $rootScope.pageLoading = false;

                    window.location.href = "/machines";
                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

            } else if ($scope.mode === "edit") {

                Machines.one($scope.machine.id).customPUT($scope.machine).then(function (response) {
                    var updated = response.plain();
                    NotificationService.notifySuccess("Machine updated successfully");
                    $rootScope.pageLoading = false;

                    window.location.href = "/machines";
                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

                return;

            }
        }
    }]);