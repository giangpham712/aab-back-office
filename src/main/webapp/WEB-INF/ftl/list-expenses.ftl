<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>

</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {expenses: ${expenses}};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/expenses/app.js"></script>
</#assign>

<@layout.admin stylesheets=stylesheets top_scripts=top_scripts content_header_title="Expenses" bottom_scripts=bottom_scripts ng_app="expenses">

<div class="row" ng-controller="ListExpensesCtrl">
    <div class="col-xs-12">

        <div class="box-header">
            <h3 class="box-title">Expenses</h3>

            <div class="buttons pull-right">
                <a href="/expenses/new" class="btn btn-primary">New expense</a>
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
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Find by expense name"/>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span>
                    </div>
                </div>

            </div>
            <table id="expenses" class="table table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>Expense</th>
                    <th></th>
                    <th style="width: 120px">Action</th>
                </tr>
                </thead>
                <tbody>
                <tr dir-paginate="expense in expenses | itemsPerPage: 200" total-expenses="{{totalExpenses}}" pagination-id="expense">
                    <td class="select">
                        <input type="checkbox">
                    </td>
                    <td class="expense">
                        <a href="{{ '/expenses/edit/' +  expense.id }}"><h3 ng-bind="expense.name"></h3></a>
                    </td>
                    <td><span ng-bind=""></span></td>
                    <td>
                        <div class="btn-group">
                            <button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown"
                                    aria-expanded="false">
                                Action &nbsp;
                                <i class="fa fa-caret-down"></i>
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                <li><a href="javascript:void(0);" ng-click="viewExpense(expense)">View</a></li>
                                <li><a href="javascript:void(0);" ng-click="deleteExpense(expense)">Delete</a></li>
                            </ul>
                        </div>
                    </td>
                </tr>
                </tbody>

            </table>
            <dir-pagination-controls class="pull-right" pagination-id="expense"></dir-pagination-controls>
        </div>

    </div>

</div>

</@layout.admin>