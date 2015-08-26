<div id="modal_edit_bom" class="modal modal fade in" ng-modal modal-id="edit_bom" style="">
    <div class="modal-dialog">
        <div class="modal-content">
            <form name="editBOMForm" ng-submit="saveBOM(editingBOM)" novalidate>
                <div class="modal-header clearfix">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <div class="col-md-12">
                        <h3 class="modal-title">Bill of Materials</h3>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="clearfix margin-bottom-10">
                        <div class="col-md-6 margin-bottom-20">
                            <input type="hidden" ng-model="editingBOM.id"/>
                            <input type="text" class="form-control margin-bottom-10" placeholder="Name"
                                   ng-model="editingBOM.name"
                                   required/>

                            <p class="text-danger margin-bottom-10" ng-show="editingBOM.errors.name">
                                {{editingBOM.errors.name}}
                            </p>
                            <textarea class="form-control" rows="3" placeholder="Description"
                                      ng-model="editingBOM.description"></textarea>
                        </div>

                        <div class="col-md-12">
                            <label>Items</label>
                            <table class="table table-simple table-bordered">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Item</th>
                                    <th>Quantity (kg)</th>
                                    <th>Ratio</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-repeat="(index, item) in editingBOM.items">
                                    <td style="width: 40px;">{{index + 1}}</td>
                                    <td style="width: 280px">
                                        <selectize class="form-control"
                                                   ng-model="item.itemId"
                                                   config="{ valueField: 'id', labelField: 'displayName', searchField: 'displayName', maxItems: 1, placeholder: 'Select an item' }"
                                                   options="inventoryItems"
                                                   required></selectize>
                                    </td>
                                    <td style="width: 100px">
                                        <input type="text" class="form-control" ng-auto-numeric="integer"
                                               ng-model="item.quantity"/>
                                    </td>
                                    <td style="width: 70px"></td>
                                    <td>
                                        <a href="javascript:void(0);"
                                           ng-show="editingBOM.items.length > 1"
                                           ng-click="removeBOMItem(editingBOM, item, index)">Remove</a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="clearfix">
                                <div class="pull-left">
                                    <p class="text-danger" ng-show="editingBOM.errors.items">{{editingBOM.errors.items}}</p>
                                </div>
                                <div class="pull-right text-right">
                                    <button type="button" class="btn btn-sm btn-default"
                                            ng-click="addBOMItem(editingBOM)">
                                        Add new line
                                    </button>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary" ng-disabled="editBOMForm.$invalid">Save</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>