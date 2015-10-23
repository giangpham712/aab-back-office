<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {item: ${item}, type: "${type}"};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/items/app.js"></script>
<script>

</script>
</#assign>

<@layout.admin sidebar_class="sidebar-collapse" content_header_title="${headerTitle}" stylesheets=stylesheets top_scripts=top_scripts bottom_scripts=bottom_scripts ng_app="items">
<div class="row clearfix" ng-controller="EditItemCtrl">
    <form name=" newItemForm" novalidate>
        <div class="col-xs-12">
            <div class="margin-bottom-20">
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-2">
                        <select custom-select class="form-control"
                                ng-options="type.id as type.displayName for type in itemTypes"
                                ng-model="item.type"
                                required>
                        </select>
                    </div>
                </div>
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-2">
                        <label class="small">Item number</label>
                        <input type="text" class="form-control" placeholder="Item number"
                               name="itemNumber"
                               ng-model="item.itemNumber"
                               required>
                    </div>

                    <div class="col-md-3">
                        <label class="small">Display name</label>
                        <input type="text" class="form-control" placeholder="Display name"
                               name="displayName"
                               ng-model="item.displayName"
                               required>
                    </div>
                </div>
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-5">
                        <label class="small">Description</label>
                        <textarea class="form-control" placeholder="Description"
                                  name="description" rows="4"
                                  ng-model="item.description">
                        </textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-8">

            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#product_information" data-toggle="tab">Product information</a></li>
                    <li><a href="#inventory" data-toggle="tab" ng-show="item.type === 'INVENTORY_ITEM'">Inventory</a>
                    </li>
                    <li><a href="#bill_of_materials" data-toggle="tab" ng-show="item.type === 'ASSEMBLY_ITEM'">Bills of
                        materials</a></li>
                    <li><a href="" data-toggle="tab"></a></li>
                </ul>
                <div class="tab-content" ng-cloak>
                    <div class="tab-pane clearfix active" id="product_information">

                        <div ng-repeat="(group, attributes) in attributeGroups">
                            <div class="clearfix margin-bottom-20" ng-show="notEmpty(attributes, item.type)">
                                <h4>{{group}}</h4>

                                <div class="col-md-3" style="margin-bottom: 10px"
                                     ng-repeat="attribute in attributes | filter: {type: item.type}">
                                    <label class="small">{{attribute.attributeName}}</label>
                                    <input type="text" class="form-control" placeholder="{{attribute.attributeName}}"
                                           ng-model="attribute.attributeValue">
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="tab-pane clearfix" id="inventory"
                         ng-show="item.type === 'INVENTORY_ITEM'">
                        <div class="col-md-12">
                            <div class="col-md-3" style="margin-bottom: 10px">
                                <label class="small">Inventory management</label>

                                <label class="custom-checkbox">
                                    <input type="checkbox" custom-checkbox="" ng-model="item.trackInventory"
                                           name="trackInventory"/>
                                    Track inventory
                                </label>
                            </div>

                            <div class="col-md-3" style="margin-bottom: 10px">
                                <label class="small">Quantity on hand</label>
                                <input type="text" class="form-control" placeholder="Quantity on hand"
                                       ng-model="item.quantityOnHand"
                                       ng-disabled="item.trackInventory != 1">
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane clearfix" id="bill_of_materials"
                         ng-show="item.type === 'ASSEMBLY_ITEM'">
                        <div class="col-md-12">
                            <table class="table table-simple table-bordered">
                                <thead>
                                <tr>
                                    <th class="text-center" style="width: 50px">#</th>
                                    <th>Name</th>

                                    <th>Date Created</th>
                                    <th>Date Modified</th>
                                    <th class="text-right" style="width: 120px">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-repeat="(index, bom) in item.billsOfMaterials">
                                    <td>{{index + 1}}</td>
                                    <td>{{bom.name}}</td>

                                    <td>{{bom.createdAt}}</td>
                                    <td>{{bom.updatedAt}}</td>

                                    <td class="text-right">
                                        <a href="javascript:void(0)" ng-click="showEditBOM(bom)">Edit</a>
                                        &nbsp;
                                        <a href="javascript:void(0)" ng-click="confirmDeleteBOM(bom)">Delete</a>
                                    </td>
                                </tr>
                                <tr ng-hide="item.billsOfMaterials && item.billsOfMaterials.length > 0">
                                    <td colspan="6">
                                        There is no bills of materials for this item yet. <a href=""
                                                                                             ng-click="showNewBOM()">Click
                                        to add</a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="text-right">
                                <button type="button" class="btn btn-sm btn-default" ng-click="showNewBOM()">
                                    Add new BOM
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="height: 150px;"></div>
        </div>
        <div class="bottom-bar">
            <div>
                <button class="btn btn-default">Cancel</button>
            </div>
            <div class="links text-center">

            </div>
            <div class="text-right">
                <button type="button" class="btn btn-info" ng-click="saveItem()">Save</button>
            </div>
        </div>
    </form>
    <#include "partials/edit-bom.ftl">
    <#include "partials/delete-bom.ftl">
</div>
</@layout.admin>