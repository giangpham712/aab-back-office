<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>

</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {boms: ${boms}};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/boms/app.js"></script>
</#assign>

<@layout.admin stylesheets=stylesheets top_scripts=top_scripts content_header_title="BOMs" bottom_scripts=bottom_scripts ng_app="boms">

<div class="row" ng-controller="ListBOMsCtrl">
    <div class="col-xs-12">

        <div class="box-header">
            <h3 class="box-title">BOMs</h3>

            <div class="buttons pull-right">
                <a href="/boms/new" class="btn btn-primary">New bom</a>
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
                        <input type="text" class="form-control" placeholder="Find by bom name"/>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span>
                    </div>
                </div>

            </div>
            <table id="boms" class="table table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>BOM</th>
                    <th></th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr dir-paginate="bom in boms | itemsPerPage: 100" total-boms="{{totalBOMs}}" pagination-id="bom">
                    <td class="select">
                        <input type="checkbox">
                    </td>
                    <td class="bom">
                        <a href="{{ '/boms/edit/' +  bom.id }}"><h3 ng-bind="bom.name"></h3></a>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>

            </table>
            <dir-pagination-controls class="pull-right" pagination-id="bom"></dir-pagination-controls>
        </div>

    </div>

</div>

</@layout.admin>