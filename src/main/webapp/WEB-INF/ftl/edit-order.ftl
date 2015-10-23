<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {
        customers: ${customers},
        order: ${order},
        estimate: ${estimate},
        mode: '${mode}'
    };
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/orders/app.js"></script>
<script>

</script>
</#assign>

<@layout.admin sidebar_class="sidebar-collapse" content_header_title="${headerTitle}" stylesheets=stylesheets top_scripts=top_scripts bottom_scripts=bottom_scripts ng_app="orders">
<div class="row clearfix" ng-controller="EditOrderCtrl">
    <form name="editOrderForm" novalidate ng-cloak>
        <div class="col-xs-12">

            <div class="margin-bottom-20">
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="Order number"
                               name="orderNumber"
                               data-toggle="tooltip" data-original-title="{{order.orderNumberError}}"
                               data-placement="bottom"
                               ng-class="{'validation-error': order.orderNumberError != null && order.orderNumberError != ''}"
                               data-label="Order number"
                               ng-model="order.orderNumber"
                               required>

                        <p></p>
                    </div>
                    <div class="col-md-3">
                        <selectize
                                ng-model="order.customerId"
                                config="{ valueField: 'id', labelField: 'displayName', searchField: 'displayName', maxItems: 1, placeholder: 'Select a customer' }"
                                options="customers">
                        </selectize>
                    </div>
                    <div class="col-md-3">

                    </div>
                    <div class="col-md-4 text-right">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default"><i class="fa fa-cog"></i></button>
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                <span class="caret"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                <li><a href="" ng-click="generateProductionSheet()">Generate production sheet</a></li>

                                <li class="divider"></li>
                                <li><a href="#">Generate quote</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-2">
                        <label class="small">Order date</label>

                        <div class="input-group date">
                            <input type="text" class="form-control" placeholder="Order date"
                                   name="orderDate" data-label="Order date"
                                   data-toggle="tooltip" data-original-title="{{order.orderDateError}}"
                                   data-placement="bottom"
                                   ng-model="order.orderDate" ng-datetime-picker language="en"
                                   ng-class="{'validation-error': order.orderDateError != null && order.orderDateError != ''}"
                                   date-format="DD/MM/YYYY"
                                   required>

                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <label class="small">Estimated time of departure</label>

                        <div class="input-group date">
                            <input type="text" class="form-control" placeholder="ETD"
                                   name="estimatedTimeOfDeparture" data-label="Estimated time of departure"
                                   data-toggle="tooltip" data-original-title="{{order.estimatedTimeOfDepartureError}}"
                                   data-placement="bottom"
                                   ng-model="order.estimatedTimeOfDeparture" ng-datetime-picker
                                   ng-class="{'validation-error': order.estimatedTimeOfDepartureError != null && order.estimatedTimeOfDepartureError != ''}"
                                   greater-than="order.orderDate"
                                   language="en" date-format="DD/MM/YYYY"
                                   required>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <label class="small">Estimated time of arrival</label>

                        <div class="input-group date">
                            <input type="text" class="form-control" placeholder="ETA"
                                   name="estimatedTimeOfArrival" data-label="Estimated time of arrival"
                                   data-toggle="tooltip" data-original-title="{{order.estimatedTimeOfArrivalError}}"
                                   data-placement="bottom"
                                   ng-model="order.estimatedTimeOfArrival" ng-datetime-picker
                                   ng-class="{'validation-error': order.estimatedTimeOfArrivalError != null && order.estimatedTimeOfArrivalError != ''}"
                                   greater-than="order.estimatedTimeOfDeparture"
                                   language="en" date-format="DD/MM/YYYY"
                                   required>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="margin-bottom-20">
                <div class="margin-bottom-10">
                    <button type="button" class="btn btn-sm btn-default hidden">Fill with data of previous order
                    </button>
                </div>
                <div id="list_order_items" class="order-items" style="">
                    <div editable-grid style="padding-bottom: 150px">
                        <table id="table_order_items" class="table table-hover">
                            <thead>
                            <tr>
                                <th rowspan="2"><span></span></th>
                                <th rowspan="2">Item</th>
                                <th rowspan="2">Measurement</th>
                                <th rowspan="2">Handle width</th>
                                <th rowspan="2">Handle length</th>
                                <th rowspan="2">Material</th>
                                <th rowspan="2">Bag type</th>
                                <th rowspan="2">Film color</th>
                                <th rowspan="2">Emboss</th>
                                <th rowspan="2">Printing</th>
                                <th rowspan="2" class="quantity">Qty (pcs)</th>
                                <th colspan="2">Outerbag</th>
                                <th colspan="2">Carton</th>
                                <th rowspan="2">Unit price</th>
                                <th rowspan="2">Total</th>
                            </tr>
                            <tr>
                                <th>Printing</th>
                                <th title="Pieces per outerbag">Pieces</th>
                                <th title="Pieces per carton">Pieces</th>
                                <th>Total</th>

                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="item in order.items" ng-click="editRow($event, item, $index)"
                                ng-class="{'invalid': isInvalid(item) }">
                                <td class="select">
                                    <span class="select">
                                        <i class="error text-danger fa fa-exclamation-triangle"></i>
                                    </span>
                                    <input type="hidden" ng-model="item.productId"/>
                                </td>
                                <td class="item"><span ng-cloak="">{{item.itemName}}</span></td>
                                <td class="size"><span ng-bind="getSize(item)"></span></td>

                                <td class="handle-width"><span ng-bind="item.handleWidth"></span></td>
                                <td class="handle-length"><span ng-bind="item.handleLength"></span></td>

                                <td class="material"><span ng-bind="item.material"></span></td>
                                <td class="bag-type"><span ng-bind="item.bagType"></span></td>

                                <td class="film-color"><span ng-bind="item.filmColor"></span></td>

                                <td class="emboss"><span ng-bind="item.emboss"></span></td>

                                <td class="printing"><span ng-bind="item.printing"></span></td>
                                <td class="quantity"><span ng-cloak="">{{item.quantity | number}}</span></td>
                                <td class="outerbag-printing"><span ng-cloak="">{{item.outerbagPrinting}}</span></td>
                                <td class="pieces-per-outerbag"><span
                                        ng-cloak="">{{item.piecesPerOuterbag | number}}</span></td>
                                <td class="pieces-per-carton"><span ng-cloak="">{{item.piecesPerCarton | number}}</span>
                                </td>
                                <td class="total-cartons"><span ng-cloak="">{{item.totalCartons | number}}</span></td>
                                <td class="unit-price"><span ng-cloak="">{{item.unitPrice | currency:'$':5}}</span></td>
                                <td class="total"><span ng-cloak="">{{getLineTotal(item) | currency:'$':1}}</span></td>
                            </tr>

                            </tbody>
                        </table>

                        <table class="table row-editor" ng-show="editingItem != null" ng-cloak="">
                            <tr>
                                <td class="select">
                                    <span class="select">
                                        <div class="btn-group-vertical">
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-default dropdown-toggle options"
                                                        data-toggle="dropdown" aria-expanded="false">
                                                    <span class="caret"></span>
                                                </button>
                                                <ul class="dropdown-menu">
                                                    <li>
                                                        <a href ng-click="fillProductInfo($event, editingItem)">Load
                                                            product info
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href ng-click="removeItem($event)">Remove item
                                                        </a>
                                                    </li>
                                                    <li><a href="#">Move down</a></li>
                                                    <li><a href="#">Move up</a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </span>
                                </td>
                                <td class="item">
                                <span>
                                    <selectize class="form-control"
                                               config="{valueField: 'id', labelField: 'name', searchField: 'name', maxItems: 1, placeholder: 'Select a product'}"
                                               options="products"
                                               ng-model="editingItem.productId"></selectize>

                                </span>
                                </td>
                                <td class="size">
                                <span>
                                    <input type="text" class="form-control thickness"
                                           config="{'vMax':'1'}"
                                           ng-auto-numeric="decimal"
                                           ng-model="editingItem.thickness">
                                    <input type="text" class="form-control width"
                                           config="{'vMax':'1000'}"
                                           ng-auto-numeric="integer"
                                           ng-model="editingItem.width">
                                    <input type="text" class="form-control blowing-width"
                                           config="{'vMax':'1000'}"
                                           ng-auto-numeric="integer"
                                           ng-model="editingItem.blowingWidth">
                                    <input type="text" class="form-control length"
                                           config="{'vMax':'1000'}"
                                           ng-auto-numeric="integer"
                                           ng-model="editingItem.length">
                                </span>
                                </td>

                                <td class="handle-width">
                                    <span><input type="text" class="form-control"
                                                 ng-model="editingItem.handleWidth"
                                                 ng-auto-numeric="integer"></span>
                                </td>

                                <td class="handle-length">
                                    <span><input type="text" class="form-control"
                                                 ng-model="editingItem.handleLength"
                                                 ng-auto-numeric="integer"></span>
                                </td>

                                <td class="material">
                                    <span>
                                        <select class="form-control" ng-model="editingItem.material"
                                                ng-options="material for material in materials">
                                        </select>
                                    </span>
                                </td>

                                <td class="bag-type">
                                    <span>
                                        <select class="form-control" ng-model="editingItem.bagType" style="width: auto"
                                                ng-options="bagType for bagType in bagTypes">
                                        </select>
                                    <span>
                                </td>


                                <td class="film-color">
                                    <span>
                                        <select class="form-control" ng-model="editingItem.filmColor"
                                                ng-options="filmColor for filmColor in filmColors">
                                        </select>
                                    <span>
                                </td>

                                <td class="emboss">
                                    <span>
                                        <select class="form-control" ng-model="editingItem.emboss"
                                                ng-options="emboss for emboss in embossTypes">
                                        </select>
                                    </span>
                                </td>

                                <td class="printing">
                                    <span>
                                        <select class="form-control" ng-model="editingItem.printing"
                                                ng-options="printing for printing in printingTypes">
                                        </select>
                                    </span>
                                </td>
                                <td class="quantity">
                                <span>
                                    <input type="text" class="form-control"
                                           config="{'vMax':'10000000'}"
                                           ng-auto-numeric="integer"
                                           ng-model="editingItem.quantity"
                                           readonly>
                                </span>
                                </td>
                                <td class="outerbag-printing">
                                    <span>
                                        <select class="form-control" ng-model="editingItem.outerbagPrinting"
                                                ng-options="outerbagPrinting for outerbagPrinting in outerbagPrintingTypes">
                                        </select>
                                    </span>
                                </td>
                                <td class="pieces-per-outerbag">
                                    <span><input type="text" class="form-control"
                                                 config="{'vMax':'1000'}"
                                                 ng-auto-numeric="integer"
                                                 ng-model="editingItem.piecesPerOuterbag"></span>
                                </td>
                                <td class="pieces-per-carton">
                                    <span><input type="text" class="form-control"
                                                 config="{'vMax':'10000'}"
                                                 ng-auto-numeric="integer"
                                                 ng-model="editingItem.piecesPerCarton"></span>
                                </td>
                                <td class="total-cartons">
                                    <span><input type="text" class="form-control"
                                                 config="{'vMax':'10000'}"
                                                 ng-auto-numeric="integer"
                                                 ng-model="editingItem.totalCartons"></span>
                                </td>
                                <td class="unit-price">
                                    <span><input type="text" class="form-control"
                                                 config="{'vMax':'10'}"
                                                 ng-auto-numeric="currency"
                                                 ng-model="editingItem.unitPrice"></span>
                                </td>
                                <td class="total">
                                    <span><input type="text" class="form-control"
                                                 config="{'vMax':'10'}"
                                                 ng-auto-numeric="currency"
                                                 ng-model="editingItem.total"></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="clearfix margin-bottom-20" style="padding-bottom: 150px;">
                        <div class="pull-left">
                            <button type="button" class="btn btn-sm btn-default" ng-click="addItem()">Add line</button>
                        </div>

                        <div class="pull-right">
                            <div class="order-total">
                                <label>Total</label>
                                <span ng-cloak>{{orderTotal() | currency}}</span>
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
                <div ng-if="mode == 'edit'">
                    <a href="/estimates/edit/{{estimate.id}}" ng-if="estimate != null">View Estimate</a>
                    <a href="/estimates/new?orderId={{order.id}}" ng-if="estimate == null">Create Estimate</a>
                </div>
            </div>
            <div class="text-right">
                <button type="button" class="btn btn-info" ng-click="saveOrder()">Save</button>
            </div>
        </div>
    </form>
</div>
</@layout.admin>

