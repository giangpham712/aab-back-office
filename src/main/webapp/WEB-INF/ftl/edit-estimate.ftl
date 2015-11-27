<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#if mode == 'edit'>
    <#assign title = "Edit estimate">
<#else>
    <#assign title = "New estimate">
</#if>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {
        products: ${products},
        materials: ${materials},
        expenses: ${expenses},
        estimate: ${estimate},
        order: ${order},
        mode: '${mode}'
    };
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/estimates/app.js"></script>
<script>

</script>
</#assign>

<@layout.admin sidebar_class="sidebar-collapse" content_header_title=title stylesheets=stylesheets top_scripts=top_scripts bottom_scripts=bottom_scripts ng_app="estimates" >
<div class="row clearfix" ng-controller="EditEstimateCtrl">

    <form name="newOrderForm" novalidate ng-cloak>

        <div class="col-xs-12">
            <div class="margin-bottom-20">
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-2">
                        <label>Order: </label>
                        <a href="/orders/edit/{{estimate.orderId}}">{{estimate.orderNumber}}</a>

                    </div>
                    <div class="col-md-6">

                    </div>
                    <div class="col-md-4 text-right" ng-show="estimate.id">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default"><i class="fa fa-cog"></i></button>
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                <span class="caret"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                <li><a href="javascript:void(0);" ng-click="updateEstimate(estimate)">Update estimate</a></li>
                                <li><a href="javascript:void(0);" ng-click="resetEstimate(estimate)">Reset estimate</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div id="list_order_items" class="order-items">
                    <label>Quote</label>

                    <div style="margin-bottom: 150px">
                        <table class="table table-hover">
                            <thead>
                            <tr>

                                <th rowspan="2" style="width: 75px">Item</th>
                                <th rowspan="2" style="width: 100px">Thickness (mm)</th>
                                <th rowspan="2" style="width: 100px">Width (mm)</th>
                                <th rowspan="2" style="width: 100px">Blowing width (mm)</th>
                                <th rowspan="2" style="width: 100px">Gusset (mm)</th>
                                <th rowspan="2" style="width: 100px">Length (mm)</th>
                                <th rowspan="2" style="width: 110px">Unit weight (G/bag)</th>

                                <th rowspan="2" class="quantity">Qty (pcs)</th>

                                <th rowspan="2" style="width: 110px">Total weight (KG)</th>

                                <th rowspan="2" style="width: 110px">Price per KG</th>
                                <th rowspan="2" style="width: 110px">Price per ton</th>
                                <th rowspan="2">Total</th>
                            </tr>

                            </thead>
                            <tbody>
                            <tr ng-repeat="item in estimate.items" ng-click="editRow($event, item, $index)"
                                ng-class="{'invalid': isInvalid(item) }">

                                <td class="item"><span>{{item.itemName}}</span></td>
                                <td class=""><span>{{item.thickness | number:4 }}</span></td>
                                <td class=""><span>{{item.width}}</span></td>
                                <td class=""><span>{{item.blowingWidth}}</span></td>
                                <td class=""><span>{{item.gusset}}</span></td>
                                <td class=""><span>{{item.length}}</span></td>
                                <td class=""><span>{{item.unitWeight | number:4}}</span></td>

                                <td class="quantity"><span ng-cloak="">{{item.quantity | number}}</span></td>


                                <td class="total-weight"><span ng-cloak="">{{item.totalWeight | number:0}}</span></td>

                                <td class="unit-price"><span
                                        ng-cloak="">{{item.pricePerWeightUnit | currency:'$':5}}</span></td>
                                <td class="total"><span
                                        ng-cloak="">{{item.pricePerWeightUnit * 1000 | currency:'$':1}}</span></td>
                                <td class="total"><span ng-cloak="">{{item.total | currency:'$':1}}</span></td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                    <div class="clearfix margin-bottom-20">

                        <div class="pull-right">
                            <div class="order-total">
                                <label>Total</label>
                                <span ng-cloak>{{estimate.getTotal() | currency}}</span>
                            </div>
                        </div>
                    </div>
                </div>


                <div id="list_order_items" class="order-items" style="">
                    <label>Actual</label>

                    <div style="margin-bottom: 150px">
                        <table class="table table-hover" style="width: auto">
                            <thead>
                            <tr>

                                <th rowspan="2" style="width: 75px">Item</th>
                                <th rowspan="2" style="width: 100px">Thickness (mm)</th>
                                <th rowspan="2" style="width: 100px">Width (mm)</th>
                                <th rowspan="2" style="width: 100px">Blowing width (mm)</th>
                                <th rowspan="2" style="width: 100px">Gusset (mm)</th>
                                <th rowspan="2" style="width: 100px">Length (mm)</th>
                                <th rowspan="2" style="width: 110px">Unit weight (G/bag)</th>

                                <th rowspan="2" class="quantity">Qty (pcs)</th>

                                <th rowspan="2" style="width: 110px">Total weight (KG)</th>

                                <th rowspan="2" style="width: 110px">Price per KG</th>

                                <th rowspan="2" style="width: 110px">Cost per ton</th>
                                <th rowspan="2" style="width: 110px">Total cost</th>

                            </tr>

                            </thead>
                            <tbody>
                            <tr ng-repeat="item in estimate.items" ng-click="editRow($event, item, $index)"
                                ng-class="{'invalid': isInvalid(item) }">

                                <td class="item"><span>{{item.itemName}}</span></td>
                                <td class=""><span>{{item.actualThickness | number:4}}</span></td>
                                <td class=""><span>{{item.width}}</span></td>
                                <td class=""><span>{{item.blowingWidth}}</span></td>
                                <td class=""><span>{{item.gusset}}</span></td>
                                <td class=""><span>{{item.length}}</span></td>
                                <td class=""><span>{{item.actualUnitWeight | number:4}}</span></td>

                                <td class="quantity"><span ng-cloak="">{{item.quantity | number}}</span></td>


                                <td class="total-weight"><span ng-cloak="">{{item.actualTotalWeight | number:0}}</span>
                                </td>

                                <td class="unit-price"><span ng-cloak="">{{item.actualPricePerWeightUnit | currency:'$':5}}</span>
                                </td>

                                <td class="" style="width: 110px" ng-click="showEditCaculationItem(item, $index)"><span>
                                    <a href="javascript:void(0)">{{item.getCostPerWeightUnit() | number}}</a>
                                </span></td>
                                <td class="" style="width: 110px"><span>{{item.getTotal() | currency:'':3}}</span></td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                    <div class="clearfix margin-bottom-20">

                        <div class="pull-right">
                            <div class="order-total">
                                <label>Total Production Cost</label>
                                <span ng-cloak>{{estimate.getTotalProductionCost() | currency}}</span>
                            </div>
                        </div>
                    </div>


                </div>
                <div class="clearfix margin-bottom-20" style="padding-bottom:150px">
                    <div class="pull-right">
                        <div class="order-total">
                            <label>Profit</label>
                            <span ng-cloak>{{estimate.getTotal() - estimate.getTotalProductionCost() | currency}}</span>
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
                <div>
                    <a href="/orders/edit/{{estimate.orderId}}">View Order</a>
                </div>
            </div>
            <div class="text-right">
                <button type="button" class="btn btn-info" ng-click="saveEstimate()">Save</button>
            </div>
        </div>
    </form>
    <#include "partials/modal-item-production-cost.ftl">
</div>

</@layout.admin>