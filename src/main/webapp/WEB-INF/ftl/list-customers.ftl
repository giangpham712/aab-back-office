<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>

</#assign>

<#assign top_scripts>
<script>
    var ViewData = ViewData || {};
    ViewData.customers = ${customers};
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/customers/app.js"></script>
</#assign>

<@layout.admin stylesheets=stylesheets top_scripts=top_scripts content_header_title="Customers" bottom_scripts=bottom_scripts ng_app="customers">

<div class="row" ng-controller="ListCustomersCtrl">
    <div class="col-xs-12">

        <div class="box-header">
            <h3 class="box-title">Customers</h3>

            <div class="buttons pull-right">
                <button type="button" class="btn btn-primary" ng-click="showNewCustomer()">New customer</button>
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
                            <li><a href="#">Sort by name</a></li>
                            <li><a href="#">Sort by company</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-4">
                    <input type="text" class="form-control" placeholder="Find a customer or company"/>
                </div>
            </div>
            <table id="customers" class="table table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>Customer</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="customer in customers">
                    <td class="select">
                        <input type="checkbox" icheck>
                    </td>
                    <td class="customer">
                        <a href=""><h3 ng-bind="customer.displayName"></h3></a>
                        <small ng-bind="customer.companyName"></small>
                    </td>
                    <td><span ng-bind="customer.email"></span></td>
                    <td></td>
                </tr>
                </tbody>

            </table>
            <!-- <dir-pagination-controls class="pull-right" pagination-id="customer"></dir-pagination-controls> -->
        </div>

    </div>
    <#include "partials/new-customer.ftl">
</div>

</@layout.admin>