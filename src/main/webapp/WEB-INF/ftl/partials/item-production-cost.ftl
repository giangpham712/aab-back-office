<div id="modal_item_production_cost" class="modal modal fade in" ng-modal modal-id="item_production_cost" style="">
    <div class="modal-dialog">
        <div class="modal-content">
            <form name="itemProductionCostForm" ng-submit="saveCalculation()" novalidate>
                <div class="modal-header clearfix">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <div class="col-md-12">
                        <h3 class="modal-title">{{editingItem.itemName}}</h3>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="clearfix margin-bottom-10">
                        <div class="col-md-12">
                            <label>Materials</label>

                            <p>
                                Configure the required materials to produce 1 ton of {{editingItem.itemName}}
                            </p>
                        </div>


                        <div class="col-md-6 margin-bottom-20">
                            <selectize class="form-control"
                                       ng-model="editingItem.selectedBOMId"
                                       config="{ valueField: 'id', labelField: 'name', searchField: 'name', maxItems: 1, placeholder: 'Select a bill of materials' }"
                                       options="editingItem.boms"></selectize>

                        </div>
                        <div class="pull-left">
                            <button class="btn btn-default" type="button"
                                    ng-click="selectBOM(editingItem.selectedBOMId)">Apply BOM
                            </button>
                        </div>

                        <div class="col-md-12">

                            <table class="table table-simple table-bordered">
                                <thead>
                                <tr>
                                    <th>Item</th>
                                    <th>Quantity (kg)</th>
                                    <th>Ratio</th>
                                    <th>Cost per kg (USD)</th>
                                    <th>Total</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-repeat="item in editingItem.materials">
                                    <td style="width: 150px">{{item.itemName}}</td>
                                    <td style="width: 80px"><input class="form-control" type="text" ng-model="item.quantity" ng-auto-numeric="integer" config="{mDec:0}"/></td>
                                    <td>{{item.quantity / editingItem.getTotalQuantity() * 100 | number:2}}%</td>
                                    <td style="width: 120px"><input class="form-control" type="text" ng-model="item.costPerWeightUnit" ng-auto-numeric="currency" config="{mDec:4}"/>
                                    </td>
                                    <td style="width: 100px">{{item.quantity * item.costPerWeightUnit | number:2}}</td>
                                </tr>
                                <tr ng-show="!editingItem.materials || editingItem.materials.length == 0">
                                    <td colspan="5">No items</td>
                                </tr>
                                </tbody>
                            </table>

                        </div>
                    </div>

                    <div class="clearfix margin-bottom-10">
                        <div class="col-md-12 margin-bottom-20">
                            <label>Other expenses</label>
                        </div>

                        <div class="col-md-12">
                            <div>
                                <table class="table table-simple table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Item</th>
                                        <th>Total</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="item in editingItem.expenseItems">
                                        <td>
                                            <selectize class="form-control"
                                                    ng-model="item.itemId"
                                                    config="{ valueField: 'id', labelField: 'displayName', searchField: 'displayName', maxItems: 1 }"
                                                    options="expenseItems"></selectize>
                                        </td>
                                        <td style="width: 250px;">
                                            <input type="text" class="form-control" ng-model="item.total" ng-auto-numeric="currency" config="{mDec:2}"/>
                                        </td>
                                    </tr>
                                    <tr ng-show="!editingItem.expenseItems || editingItem.expenseItems.length == 0">
                                        <td colspan="2">No items</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div>
                                <button type="button" class="btn btn-sm btn-default" ng-click="addNewExpenseItem()">Add expense item</button>
                            </div>
                        </div>
                    </div>

                    <div class="clearfix margin-bottom-10">
                        <div class="col-md-5 pull-right text-right">
                            <label>Total cost:</label> {{editingItem.getCostPerWeightUnit()}} USD per ton
                        </div>
                    </div>
                </div>

                <div class="modal-footer clearfix">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary" ng-disabled="itemProductionCostForm.$invalid">
                            Save
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>