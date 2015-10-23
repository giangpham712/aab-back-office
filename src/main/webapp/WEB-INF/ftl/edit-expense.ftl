<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {expense: ${expense}, mode: "${mode}"};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/expenses/app.js"></script>
<script>

</script>
</#assign>

<@layout.admin sidebar_class="sidebar-collapse" content_header_title="${headerTitle}" stylesheets=stylesheets top_scripts=top_scripts bottom_scripts=bottom_scripts ng_app="expenses">
<div class="row clearfix" ng-controller="EditExpenseCtrl">
    <form name=" newExpenseForm" novalidate>
        <div class="col-xs-12">
            <div class="margin-bottom-20">

                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-3">
                        <label class="small">Display name</label>
                        <input type="text" class="form-control" placeholder="Name"
                               name="displayName"
                               ng-model="expense.name"
                               required>
                    </div>
                </div>
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-5">
                        <label class="small">Description</label>
                        <textarea class="form-control" placeholder="Description"
                                  name="description" rows="4"
                                  ng-model="expense.description">
                        </textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-8">

            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active">
                        <a href data-target="#expense_information" aria-controls="expense_information" role="tab"
                           data-toggle="tab">Expense
                            information</a>
                    </li>
                </ul>
                <div class="tab-content" ng-cloak>
                    <div class="tab-pane clearfix active" id="expense_information">

                        <div>
                            <div class="clearfix margin-bottom-20">
                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Unit cost (USD/kg)</label>
                                    <input type="text" class="form-control" placeholder="Unit cost"
                                           ng-model="expense.unitCost">
                                </div>
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

                <button type="button" class="btn btn-info" ng-click="saveExpense()">Save</button>
            </div>
        </div>
    </form>
</div>
</@layout.admin>