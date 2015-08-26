<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>

</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {items: ${items}};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/items/app.js"></script>
</#assign>

<@layout.admin stylesheets=stylesheets top_scripts=top_scripts content_header_title="Items" bottom_scripts=bottom_scripts ng_app="items">

<div class="row" ng-controller="ListItemsCtrl">
    <div class="col-xs-12">

        <div class="box-header">
            <h3 class="box-title">Items</h3>

            <div class="buttons pull-right">
                <a href="/items/new" class="btn btn-primary">New item</a>
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
                    <input type="text" class="form-control" placeholder="Find an item"/>
                </div>
                <div class="col-md-2 pull-right">
                    <select custom-select class="form-control"
                            ng-options="type.id as type.displayName for type in itemTypes"
                            ng-model="filter.itemType"
                            ng-change="changeType()"
                            required>
                    </select>
                </div>
            </div>
            <table id="items" class="table table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>Item</th>
                    <th></th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr dir-paginate="item in items | itemsPerPage: 10" total-items="{{totalItems}}" pagination-id="item">
                    <td class="select">
                        <input type="checkbox">
                    </td>
                    <td class="item">
                        <a href="{{ '/items/edit/' +  item.id }}"><h3 ng-bind="item.displayName"></h3></a>
                    </td>
                    <td><span ng-bind=""></span></td>
                    <td></td>
                </tr>
                </tbody>

            </table>
            <dir-pagination-controls class="pull-right" pagination-id="item"></dir-pagination-controls>
        </div>

    </div>
    <#include "partials/new-customer.ftl">
</div>

</@layout.admin>