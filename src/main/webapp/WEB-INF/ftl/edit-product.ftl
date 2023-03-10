<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {product: ${product}, boms: ${boms}, mode: "${mode}"};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/products/app.js"></script>
<script>

</script>
</#assign>

<@layout.admin sidebar_class="sidebar-collapse" content_header_title="${headerTitle}" stylesheets=stylesheets top_scripts=top_scripts bottom_scripts=bottom_scripts ng_app="products">
<div class="row clearfix" ng-controller="EditProductCtrl">
    <form name=" newProductForm" novalidate>
        <div class="col-xs-12">
            <div class="margin-bottom-20">

                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-3">
                        <label class="small">Display name</label>
                        <input type="text" class="form-control" placeholder="Name"
                               name="displayName"
                               ng-model="product.name"
                               required>
                    </div>
                    <div class="col-md-5"></div>

                    <div class="col-md-4 text-right" ng-show="product.id">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default"><i class="fa fa-cog"></i></button>
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                <span class="caret"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                <li ng-if="product.active"><a href="javascript:void(0);"
                                                              ng-click="setInactive(product)">Set inactive</a></li>
                                <li ng-if="!product.active"><a href="javascript:void(0);" ng-click="setActive(product)">Set
                                    active</a></li>
                                <li><a href="javascript:void(0);" ng-click="deleteProduct(product)">Delete</a></li>

                            </ul>
                        </div>
                    </div>

                </div>
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-5">
                        <label class="small">Description</label>
                        <textarea class="form-control" placeholder="Description"
                                  name="description" rows="4"
                                  ng-model="product.description">
                        </textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-8">

            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active">
                        <a href data-target="#product_information" aria-controls="product_information" role="tab"
                           data-toggle="tab">Product nformation</a>
                    </li>
                    <li role="presentation">
                        <a href data-target="#bill_of_materials" aria-controls="bill_of_materials" role="tab"
                           data-toggle="tab">Bill of materials</a>
                    </li>
                    <li role="presentation">
                        <a href data-target="#production" aria-controls="bill_of_materials" role="tab"
                           data-toggle="tab">Production</a>
                    </li>
                    <li role="presentation">
                        <a href data-target="#carton_box" aria-controls="carton_box" role="tab"
                           data-toggle="tab">Carton box</a>
                    </li>
                    <li role="presentation">
                        <a href data-target="#readings" aria-controls="readings" role="tab"
                           data-toggle="tab">Readings</a>
                    </li>
                    <li role="presentation">
                        <a href data-target="#variables" aria-controls="variables" role="tab"
                           data-toggle="tab">Variables</a>
                    </li>
                </ul>
                <div class="tab-content" ng-cloak>
                    <div class="tab-pane clearfix active" id="product_information">

                        <div>
                            <div class="clearfix margin-bottom-20">
                                <h4>Measurement</h4>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Thickness (mm)</label>
                                    <input type="text" class="form-control" placeholder="Thickness"
                                           ng-model="product.thickness">
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Width (mm)</label>
                                    <input type="text" class="form-control" placeholder="Width"
                                           ng-model="product.width">
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Blowing width (mm)</label>
                                    <input type="text" class="form-control" placeholder="Blowing width"
                                           ng-model="product.blowingWidth">
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Length (mm)</label>
                                    <input type="text" class="form-control" placeholder="Length"
                                           ng-model="product.length">
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Handle length (mm)</label>
                                    <input type="text" class="form-control" placeholder="Handle length"
                                           ng-model="product.handleLength">
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Handle width (mm)</label>
                                    <input type="text" class="form-control" placeholder="Handle width"
                                           ng-model="product.handleWidth">
                                </div>
                            </div>
                        </div>

                        <div>
                            <div class="clearfix margin-bottom-20">
                                <h4></h4>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Material</label>
                                    <select class="form-control"
                                            ng-model="product.material"
                                            ng-init="product.material = product.material || materials[0]"
                                            ng-options="material for material in materials">
                                    </select>
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Bag type</label>
                                    <select class="form-control"
                                            ng-model="product.bagType"
                                            ng-init="product.bagType = product.bagType || bagTypes[0]"
                                            ng-options="bagType for bagType in bagTypes">
                                    </select>
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Emboss</label>
                                    <select class="form-control"
                                            ng-model="product.emboss"
                                            ng-init="product.emboss = product.emboss || embossTypes[0]"
                                            ng-options="emboss for emboss in embossTypes">
                                    </select>
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Film color</label>
                                    <select class="form-control"
                                            ng-model="product.filmColor"
                                            ng-init="product.filmColor = product.filmColor || filmColors[0]"
                                            ng-options="color for color in filmColors">
                                    </select>
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Printing</label>
                                    <select class="form-control"
                                            ng-model="product.printing"
                                            ng-init="product.printing = product.printing || printingTypes[0]"
                                            ng-options="printing for printing in printingTypes">
                                    </select>
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Outerbag printing</label>
                                    <select class="form-control"
                                            ng-model="product.outerbagPrinting"
                                            ng-init="product.outerbagPrinting = product.outerbagPrinting || outerbagPrintingTypes[0]"
                                            ng-options="outerbagPrinting for outerbagPrinting in outerbagPrintingTypes">
                                    </select>
                                </div>

                            </div>
                        </div>

                        <div>
                            <div class="clearfix margin-bottom-20">
                                <h4>Others</h4>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Pieces per outerbag</label>
                                    <input type="text" class="form-control" placeholder="Pieces per outerbag"
                                           ng-model="product.piecesPerOuterbag">
                                </div>

                                <div class="col-md-3" style="margin-bottom: 10px">
                                    <label class="small">Pieces per carton</label>
                                    <input type="text" class="form-control" placeholder="Pieces per carton"
                                           ng-model="product.piecesPerCarton">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane clearfix" id="bill_of_materials">
                        <div>
                            <div class="clearfix margin-bottom-20">
                                <div class="col-md-4" style="margin-bottom: 10px">
                                    <label class="small">Default bill of materials</label>
                                    <selectize class="form-control"
                                               ng-model="product.defaultBillId"
                                               config="{ valueField: 'id', labelField: 'name', searchField: 'name', maxItems: 1, placeholder: 'Select a bill of materials' }"
                                               options="boms"></selectize>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane clearfix" id="production">
                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Paint per KG</label>
                            <input type="text" class="form-control" placeholder="Paint per KG"
                                   ng-model="product.painPerUnitWeight">
                        </div>

                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Master Batch code</label>
                            <input type="text" class="form-control" placeholder="Master Batch code"
                                   ng-model="product.masterBatchCode">
                        </div>

                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Master Batch amount</label>
                            <input type="text" class="form-control" placeholder="Master Batch amount"
                                   ng-model="product.masterBatchAmount">
                        </div>

                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Ink code</label>
                            <input type="text" class="form-control" placeholder="Ink code"
                                   ng-model="product.inkCode">
                        </div>

                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Ink amount</label>
                            <input type="text" class="form-control" placeholder="Ink amount"
                                   ng-model="product.inkAmount">
                        </div>
                    </div>

                    <div class="tab-pane clearfix" id="carton_box">
                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Layer of Carton Box</label>
                            <input type="text" class="form-control" placeholder="Layer of Carton Box"
                                   ng-model="product.cartonBoxLayer">
                        </div>

                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Carton Length (mm)</label>
                            <input type="text" class="form-control" placeholder="Carton Length"
                                   ng-model="product.cartonLength">
                        </div>

                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Carton Width (mm)</label>
                            <input type="text" class="form-control" placeholder="Carton Width"
                                   ng-model="product.cartonWidth">
                        </div>

                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Carton Height (mm)</label>
                            <input type="text" class="form-control" placeholder="Carton Height"
                                   ng-model="product.cartonHeight">
                        </div>
                    </div>

                    <div class="tab-pane clearfix" id="readings">
                        <div class="col-md-6" style="margin-bottom: 10px">
                            <div class="clearfix margin-bottom-20">
                                <div class="row clearfix">
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-default" ng-click="showNewReading()">Add reading</button>
                                    </div>
                                </div>
                            </div>

                            <table class="table table-simple table-bordered" width="100%">
                                <thead>
                                <tr>
                                    <th class="text-center" style="width: 50px">#</th>
                                    <th style="width: 150px">Value</th>
                                    <th class="text-right" style="width: 120px">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-repeat="reading in product.readings">
                                    <td>{{$index + 1}}</td>
                                    <td style="width: 150px"><span style="line-height: 34px">{{reading.value | number:2}}</span></td>
                                    <td class="text-right">
                                        <a href="javascript:void(0)" ng-click="deleteReading($index)">Delete</a>
                                    </td>
                                </tr>
                                <tr ng-show="!product.readings || product.readings.length == 0">
                                    <td colspan="6">
                                        No readings
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="tab-pane clearfix" id="variables">
                        <div class="col-md-3" style="margin-bottom: 10px">
                            <label class="small">Recycle rate</label>
                            <input type="text" class="form-control" placeholder="Recycle rate"
                                   ng-model="product.recycleRate">
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

                <button type="button" class="btn btn-info" ng-click="saveProduct()">Save</button>
            </div>
        </div>
    </form>
    <#include "partials/modal-edit-reading.ftl">
</div>
</@layout.admin>