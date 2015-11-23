angular.module("expenses", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("expenses")
    .controller("ListExpensesCtrl", ["$scope", "$location", "Expenses", "NotificationService", function ($scope, $location, Expenses, NotificationService) {

        $scope.expenses = ViewData.expenses;

        $scope.totalExpenses = ViewData.expenses.length;

        $scope.loadExpenses = function () {

            Expenses.getList({q: $scope.searchKey, page: $scope.page})
                .then(function (response) {
                    $scope.expenses = response.plain();

                }, function (error) {
                    console.log(error);
                });
        };

        $scope.viewExpense = function (expense) {
            $window.location.href = "/expenses/edit/" + expense.id;
        }

        $scope.deleteExpense = function (expense) {
            var confirmDelete = confirm("Are you sure you want to delete this expense?");
            if (!confirmDelete) { return; }

            Expenses.one(expense.id).remove().then(function (response) {
                NotificationService.notifySuccess("Expense deleted successfully");
                $scope.loadExpenses();
            }, function (error) {
                NotificationService.notifySuccess("Expense cannot be deleted");
            });
        }

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