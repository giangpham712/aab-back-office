<#import "layout/adminLayout.ftl" as layout>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {orders: ${orders}, customers: ${customers}};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/orders/app.js"></script>
</#assign>

<@layout.admin content_header_title="Orders" top_scripts=top_scripts ng_app="orders" bottom_scripts=bottom_scripts>
<div class="row" ng-controller="ListOrdersCtrl">
    <div class="col-xs-12">

        <div class="box-header">
            <h3 class="box-title">Orders</h3>

            <div class="buttons pull-right">
                <a href="/orders/new" type="button" class="btn btn-primary">New order</a>
            </div>
        </div>

        <div class="box-body">
            <div class="row margin-bottom-10 clearfix">
                <div class="col-md-3">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                aria-expanded="false">
                            Batch actions &nbsp;
                            <i class="fa fa-caret-down"></i>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">Action</a></li>
                            <li><a href="#">Another action</a></li>
                            <li class="divider"></li>
                            <li><a href="#">Separated link</a></li>
                        </ul>
                    </div>

                    <div class="btn-group">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                aria-expanded="false">
                            Sort &nbsp;
                            <i class="fa fa-caret-up"></i>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">Sort by PO number</a></li>
                            <li><a href="#">Sort by customer</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group search-box">
                        <input type="text" class="form-control" ng-model="searchKey"
                               placeholder="Find by order number"/>
                        <span class="input-group-addon" ng-click="searchOrders()"><span
                                class="glyphicon glyphicon-search"></span></span>
                    </div>
                </div>
            </div>
            <table id="orders" class="table table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>PO Number</th>
                    <th>Customer</th>
                    <th>Order date</th>
                    <th>Estimated time of departure</th>
                    <th>Estimated time of arrival</th>
                    <th>Total</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr dir-paginate="order in orders | itemsPerPage: 20" total-items="{{totalOrders}}"
                    pagination-id="order">
                    <td>
                        <input type="checkbox">
                    </td>
                    <td><a href="" ng-click="viewOrder(order)"><span ng-bind="order.orderNumber"></span></a></td>
                    <td><span ng-bind="order.customerName"></span></td>
                    <td><span ng-bind="order.orderDate"></span></td>
                    <td><span ng-bind="order.estimatedTimeOfDeparture"></span></td>
                    <td><span ng-bind="order.estimatedTimeOfArrival"></span></td>
                    <td><span ng-bind="order.total | currency"></span></td>
                    <td>New</td>
                    <td>
                        <div class="btn-group">
                            <button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown"
                                    aria-expanded="false">
                                Action &nbsp;
                                <i class="fa fa-caret-down"></i>
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                <li><a href="javascript:void(0);" ng-click="viewOrder(order)">View</a></li>
                                <li><a href="javascript:void(0);">Print production sheet</a></li>
                                <li><a href="javascript:void(0);">View estimate</a></li>
                            </ul>
                        </div>
                    </td>
                </tr>
                </tbody>

            </table>
        </div>

    </div>
</div>
</@layout.admin>