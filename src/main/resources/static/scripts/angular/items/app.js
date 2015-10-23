angular.module("items", ["AAB.rest", "AAB.directives", "AAB.services", "angularUtils.directives.dirPagination"]);

angular.module("items")
    .controller("ListItemsCtrl", ["$scope", "$location", "Items", function ($scope, $location, Items) {

        $scope.itemTypes = [
            {
                id: "ASSEMBLY_ITEM",
                displayName: "Assembly item"
            },
            {
                id: "INVENTORY_ITEM",
                displayName: "Inventory item"
            },
            {
                id: "EXPENSE_ITEM",
                displayName: "Expense item"
            }
        ];

        $scope.filter = {
            itemType: 'ASSEMBLY_ITEM'
        }

        $scope.changeType = function () {
            Items.getList({type: $scope.filter.itemType}).then(function (response) {
                $scope.items = response.plain();
            }, function (error) {
                console.log(error);
            });
        }

        $scope.totalItems = 10;

        $scope.items = ViewData.items;
    }]);


angular.module("items")
    .controller("EditItemCtrl", ["$scope", "$rootScope", "Items", "NotificationService", function ($scope, $rootScope, Items, NotificationService) {

        $scope.type = ViewData.type;

        $scope.itemTypes = [
            {
                id: "ASSEMBLY_ITEM",
                displayName: "Assembly item"
            },
            {
                id: "INVENTORY_ITEM",
                displayName: "Inventory item"
            },
            {
                id: "EXPENSE_ITEM",
                displayName: "Expense item"
            }
        ];

        $scope.trackInventoryOptions = [
            {
                value: true,
                text: "Don't track inventory"
            },
            {
                value: false,
                text: "Track inventory"
            }
        ];

        var newItem = {
            type: "ASSEMBLY_ITEM",
            displayName: "",
            description: "",
            trackInventory: false,
            quantityOnHand: 0,
            attributes: [
                {
                    group: "Measurement",
                    attributeKey: "thickness",
                    attributeName: "Thickness (mm)",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Measurement",
                    attributeKey: "width",
                    attributeName: "Width (cm)",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Measurement",
                    attributeKey: "blowingWidth",
                    attributeName: "Blowing width (cm)",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Measurement",
                    attributeKey: "length",
                    attributeName: "Length (cm)",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Production",
                    attributeKey: "material",
                    attributeName: "Material (cm)",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Production",
                    attributeKey: "filmColor",
                    attributeName: "Film color",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Production",
                    attributeKey: "printing",
                    attributeName: "Printing",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Outerbag",
                    attributeKey: "outerbagPrinting",
                    attributeName: "Printing",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Outerbag",
                    attributeKey: "piecesPerOuterbag",
                    attributeName: "Pieces per outerbag",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Others",
                    attributeKey: "unitCost",
                    attributeName: "Unit cost",
                    attributeValue: "",
                    type: "INVENTORY_ITEM"
                },
                {
                    group: "Measurement",
                    attributeKey: "handleWidth",
                    attributeName: "Handle width (cm)",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Measurement",
                    attributeKey: "handleLength",
                    attributeName: "Handle length (cm)",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Measurement",
                    attributeKey: "handleRatio",
                    attributeName: "Handle ratio (%)",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Production",
                    attributeKey: "bagType",
                    attributeName: "Bag type",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
                {
                    group: "Production",
                    attributeKey: "emboss",
                    attributeName: "Emboss",
                    attributeValue: "",
                    type: "ASSEMBLY_ITEM"
                },
            ],
            billsOfMaterials: [],
        };

        // Initialization
        (function () {

            $scope.item = angular.merge(newItem, ViewData.item);

            Items.getList({type: "INVENTORY_ITEM"}).then(function (response) {
                $scope.inventoryItems = response.plain();
            }, function (error) {
                console.log(error);
            });

        })();

        $scope.attributeGroups = _.groupBy($scope.item.attributes, "group");

        $scope.notEmpty = function (array, type) {

            for (var i in array) {
                if (array[i].type === type)
                    return true;
            }

        }

        $scope.showNewBOM = function () {

            var newBOM = {
                id: 0,
                name: "",
                items: [
                    {
                        itemId: $scope.inventoryItems[0] == null ? 0 : $scope.inventoryItems[0].id,
                        quantity: 0
                    }
                ],
                errors: [],
                $isNew: true,
            };

            $scope.showEditBOM(newBOM);
        }

        $scope.showEditBOM = function (bom) {
            $scope.editingBOM = angular.copy(bom);
            $("#modal_edit_bom").modal("show");
        }

        $scope.addBOMItem = function (bom) {
            bom.items.push({
                itemId: $scope.inventoryItems[0].id,
                quantity: 0
            });
        }

        $scope.removeBOMItem = function (bom, item, index) {
            if (bom.items.length <= 1) {
                return;
            }
            bom.items.splice(index, 1);
        }

        $scope.saveBOM = function (bom) {

            bom.errors = {};

            if (bom.$isNew) {
                if (_.some($scope.item.billsOfMaterials, {"name": bom.name})) {
                    // BOM name already exists
                    bom.errors.name = "Name already exists";
                }
            }

            if (_.uniq(bom.items, function (item) {
                    return item.itemId;
                }).length < bom.items.length) {
                // BOM items use duplicate inventory item
                bom.errors.items = "Each inventory item cannot be used more than once";
            }

            if (Object.keys(bom.errors).length == 0) {

                if (bom.$isNew) {

                    Items.one($scope.item.id).post("boms", bom).then(function (response) {

                        $scope.item.billsOfMaterials.push(response.plain());
                        $("#modal_edit_bom").modal("hide");
                        NotificationService.notifySuccess("Bill of materials saved successfully");

                    }, function (error) {
                        console.log(error);
                    });

                } else {

                    Items.one($scope.item.id).one("boms", bom.id).customPUT(bom).then(function (response) {

                        bom = response.plain();
                        $("#modal_edit_bom").modal("hide");
                        NotificationService.notifySuccess("Bill of materials saved successfully");

                    }, function (error) {
                        console.log(error);
                    })
                }
            }
        }

        $scope.confirmDeleteBOM = function (bom, index) {
            $scope.deletingBOM = angular.copy(bom);
            $scope.deletingBOM.index = index;
            $("#modal_delete_bom").modal("show");
        }

        $scope.deleteBOM = function (bom) {
            Items.one($scope.item.id).one("boms", bom.id).remove().then(function (response) {
                $("#modal_delete_bom").modal("hide");
                $scope.item.billsOfMaterials.splice(bom.index);
            }, function (error) {
                console.log(error);
            });
        }

        $scope.saveItem = function () {
            $rootScope.pageLoading = true;

            if ($scope.type === "new") {

                $scope.item.attributes = _.reject($scope.item.attributes, function (attribute) {
                    return attribute.type !== $scope.item.type;
                });

                Items.post($scope.item).then(function (response) {
                    var created = response.plain();
                    NotificationService.notifySuccess("Item updated successfully");
                    $rootScope.pageLoading = false;

                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });
            } else if ($scope.type === "edit") {

                Items.one($scope.item.id).customPUT($scope.item).then(function (response) {
                    var updated = response.plain();
                    console.log(updated);
                    NotificationService.notifySuccess("Item updated successfully");
                    $rootScope.pageLoading = false;

                }, function (error) {
                    console.log(error);
                    $rootScope.pageLoading = false;
                });

                return;

            }
        }
    }]);