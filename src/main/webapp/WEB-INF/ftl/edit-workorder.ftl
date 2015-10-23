<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#if mode == 'edit'>
    <#assign title = "Edit work order">
<#else>
    <#assign title = "New work order">
</#if>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {
        assemblyItems: ${assemblyItems},
        inventoryItems: ${inventoryItems},
        expenseItems: ${expenseItems},
        order: ${order},
        workOrder: ${workOrder},
        estimate: ${estimate},
        mode: '${mode}'
    };
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/workorders/app.js"></script>
<script>

</script>
</#assign>

<@layout.admin sidebar_class="sidebar-collapse" content_header_title=title stylesheets=stylesheets top_scripts=top_scripts bottom_scripts=bottom_scripts ng_app="workorders" >
<div class="row clearfix" ng-controller="EditWorkOrderCtrl">

    <form name="editWorkOrderForm" novalidate ng-cloak>

        <div class="col-xs-12">

            <div class="margin-bottom-20">

                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-2">
                        <label>Order: </label>
                        <a href="/orders/edit/{{workOrder.orderId}}">{{workOrder.orderNumber}}</a>

                    </div>

                    <div class="col-md-2 pull-right text-right">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default">Print production sheet</button>
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    aria-expanded="false">
                                <span class="caret"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#">Print production sheet</a></li>

                            </ul>
                        </div>
                    </div>
                </div>

                <div class="nav-tabs-custom">

                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation"
                            ng-repeat="item in workOrder.items"
                            ng-class="{'active': $index == 0}">
                            <a href="#tab_{{item.itemId}}" aria-controls="home" role="tab" data-toggle="tab">{{item.itemName}}</a>
                        </li>
                    </ul>

                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane clearfix" id="tab_{{item.itemId}}"
                             ng-repeat="item in workOrder.items"
                             ng-class="{'active': $index == 0}">

                            <br/>

                            <div class="col-md-6 margin-bottom-20">
                                <p>
                                    <label>Thickness: </label>
                                    <span>{{item.thickness | number}} MM</span>
                                </p>

                                <p>
                                    <label>Width: </label>
                                    <span>{{item.width}} MM</span>
                                </p>

                                <p>
                                    <label>Length: </label>
                                    <span>{{item.length}} MM</span>
                                </p>

                                <p>
                                    <label>Gusset: </label>
                                    <span>{{item.gusset}} MM</span>
                                </p>

                                <p>
                                    <label>Handle length: </label>
                                    <span>{{item.handleLength}} MM</span>
                                </p>

                                <p>
                                    <label>Handle width: </label>
                                    <span>{{item.handleWidth}} MM</span>
                                </p>

                                <p>
                                    <label>Emboss: </label>
                                    <span>{{item.emboss}}</span>
                                </p>
                            </div>

                            <div class="col-md-6 margin-bottom-20">
                                <p>
                                    <label>Weight per metres: </label>
                                    <span>{{item.weightPerLengthUnit | number}} G</span>
                                </p>

                                <p>
                                    <label>Length per roll: </label>
                                    <span>{{item.lengthPerRoll | number}} M</span>
                                </p>

                                <p>
                                    <label>Total rolls: </label>
                                    <span>{{item.totalRolls | number}}</span>
                                </p>

                                <p>
                                    <label>Weight per roll: </label>
                                    <span>{{item.weightPerRoll | number}} KG</span>
                                </p>

                                <p>
                                    <label>Total blowing weight: </label>
                                    <span>{{item.totalBlowingWeight | number}} KG</span>
                                </p>
                            </div>

                            <div class="col-md-7">
                                <table class="table table-simple table-bordered" style="width: 100%">
                                    <thead>
                                    <tr>
                                        <th>Item</th>
                                        <th>Quantity required (for this product)</th>
                                        <th>Quantity required (for this order)</th>
                                        <th>Quantity on hand</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="material in item.rawMaterials">
                                        <td>{{material.itemName}}</td>
                                        <td>{{material.quantity}}</td>
                                        <td>{{workOrder.rawMaterialMap[material.itemId]}}</td>
                                        <td ng-class="{'bg-danger': workOrder.rawMaterialMap[material.itemId] > material.quantityOnHand }">
                                            {{material.quantityOnHand}}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>

            </div>

        </div>

        <div class="bottom-bar">
            <div>
                <button class="btn btn-default">Cancel</button>
            </div>
            <div class="links text-center">

            </div>
            <div class="text-right">
                <button type="button" class="btn btn-info" ng-click="saveWorkOrder()">Save</button>
            </div>
        </div>

    </form>
</div>

</@layout.admin>