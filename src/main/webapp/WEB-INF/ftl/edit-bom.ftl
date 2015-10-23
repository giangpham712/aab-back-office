<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {bom: ${bom}, mode: "${mode}"};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/boms/app.js"></script>
<script>

</script>
</#assign>

<@layout.admin sidebar_class="sidebar-collapse" content_header_title="${headerTitle}" stylesheets=stylesheets top_scripts=top_scripts bottom_scripts=bottom_scripts ng_app="boms">
<div class="row clearfix" ng-controller="EditBOMCtrl">
    <form name="newBOMForm" novalidate>
        <div class="col-xs-12">
            <div class="margin-bottom-20">

                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-3">
                        <label class="small">Name</label>
                        <input type="text" class="form-control" placeholder="Name"
                               name="displayName"
                               ng-model="bom.name"
                               required>
                    </div>
                </div>
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-5">
                        <label class="small">Description</label>
                        <textarea class="form-control" placeholder="Description"
                                  name="description" rows="4"
                                  ng-model="bom.description">
                        </textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-8">

            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active">
                        <a href data-target="#bom_information" aria-controls="bom_information" role="tab"
                           data-toggle="tab">Materials
                        </a>
                    </li>
                </ul>
                <div class="tab-content" ng-cloak>
                    <div class="tab-pane clearfix active" id="materials">


                        <div class="clearfix margin-bottom-20">
                            <div class="row clearfix">
                                <div class="col-md-4">
                                    <selectize
                                            ng-model="newItem.materialId"
                                            config="{ valueField: 'id', labelField: 'name', searchField: 'name', maxItems: 1, placeholder: 'Select a material' }"
                                            options="materials">
                                    </selectize>
                                </div>

                                <div class="col-md-2">
                                    <button type="button" class="btn btn-default" ng-click="addItem()">Add item</button>
                                </div>
                            </div>
                        </div>

                        <table class="table table-simple table-bordered">
                            <thead>
                            <tr>
                                <th class="text-center" style="width: 50px">#</th>
                                <th style="width: 250px">Material</th>

                                <th style="width: 150px">Quantity (kg)</th>
                                <th style="width: 150px">Ratio (%)</th>
                                <th class="text-right" style="width: 120px">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="item in bom.items">
                                <td>{{index + 1}}</td>
                                <td style="width: 250px">{{item.materialName}}</td>

                                <td style="width: 150px"><input type="text" ng-auto-numeric="decimal" config="{mDec: 2}" class="form-control" ng-model="item.quantity"></td>
                                <td style="width: 150px"><span style="line-height: 34px">{{item.ratio | number:2}}</span></td>

                                <td class="text-right">
                                    <a href="javascript:void(0)" ng-click="deleteItem($index)">Delete</a>
                                </td>
                            </tr>
                            <tr ng-show="bom.items.length == 0">
                                <td colspan="6">
                                    No materials
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="2"><b style="line-height: 34px">Total</b></td>
                                    <td><input type="text" class="form-control" ng-model="totalQuantity" readonly></td>
                                    <td><span style="line-height: 34px">100</span></td>
                                </tr>
                            </tfoot>
                        </table>

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

                <button type="button" class="btn btn-info" ng-disabled="newBOMForm.$invalid" ng-click="saveBOM()">Save</button>
            </div>
        </div>
    </form>
</div>
</@layout.admin>