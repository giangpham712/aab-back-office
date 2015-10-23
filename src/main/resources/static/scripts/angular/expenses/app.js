angular.module("expenses", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("expenses")
    .controller("ListExpensesCtrl", ["$scope", "$location", "Expenses", function ($scope, $location, Expenses) {

        $scope.expenses = ViewData.expenses;

        $scope.totalExpenses = ViewData.expenses.length;

    }]);


angular.module("expenses")
    .controller("EditExpenseCtrl", ["$scope", "$rootScope", "$location", "Expenses", "NotificationService", function ($scope, $rootScope, $location, Expenses, NotificationService) {

        $scope.expense = ViewData.expense;
        $scope.mode = ViewData.mode;

        $scope.saveExpense = function () {
            $rootScope.pageLoading = true;

            if ($scope.mode === "new") {

                Expenses.post($scope.expense).then(function (response) {
                    var created = response.plain();
                    NotificationService.notifySuccess("Expense created successfully");
                    $rootScope.pageLoading = false;

                    window.location.href = "/expenses";
                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

            } else if ($scope.mode === "edit") {

                Expenses.one($scope.expense.id).customPUT($scope.expense).then(function (response) {
                    var updated = response.plain();
                    NotificationService.notifySuccess("Expense updated successfully");
                    $rootScope.pageLoading = false;

                    window.location.href = "/expenses";
                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

                return;

            }
        }
    }]);