<#import "layout/adminLayout.ftl" as layout>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {orders: ${orders}};
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
                <table id="orders" class="table table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>PO Number</th>
                        <th>Customer</th>
                        <th>Order date</th>
                        <th>Estimated time of arrival</th>
                        <th>Estimated time of departure</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr dir-paginate="order in orders | itemsPerPage: 10" total-items="{{totalOrders}}" pagination-id="order" ng-click="viewOrder(order)">
                        <td>
                            <input type="checkbox">
                        </td>
                        <td><span ng-bind="order.orderNumber"></span></td>
                        <td><span ng-bind="order.customer.displayName"></span></td>
                        <td><span ng-bind="order.orderDate"></span></td>
                        <td><span ng-bind="order.estimatedTimeOfArrival"></span></td>
                        <td><span ng-bind="order.estimatedTimeOfDeparture"></span></td>
                        <td><span ng-bind="order.total | currency"></span></td>
                        <td>New</td>
                        <td>

                        </td>
                    </tr>
                    </tbody>

                </table>
            </div>

    </div>
</div>
</@layout.admin>