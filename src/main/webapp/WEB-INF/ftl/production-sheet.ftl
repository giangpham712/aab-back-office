<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {
        order: ${order},
        productionSheetOrder: ${productionSheetOrder}
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
<div class="row clearfix" ng-controller="OrderProductionSheetCtrl">
    <form name="orderProductionSheetForm" action="{{ '/orders/productionsheet/' + order.id }}" class="clearfix"
          method="POST">

        <div class="col-xs-12">
            <div class="margin-bottom-20">

                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-2">
                        <label>${message.getMessage('order.order')}: </label>
                    </div>
                    <div class="col-md-2">
                        <a href="/orders/edit/{{order.id}}">{{order.orderNumber}}</a>
                    </div>
                </div>

                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-2">
                        <label style="line-height: 34px;">Order title: </label>
                    </div>
                    <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="Order name"
                               name="orderName"
                               ng-model="order.orderName"
                               required>

                        <p></p>
                    </div>
                </div>

                <div class="nav-tabs-custom">

                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation"
                            ng-repeat="item in productionSheetOrder.productionSheetOrderItems"
                            ng-class="{'active': $index == 0}">
                            <a href="#tab_{{item.productId}}" aria-controls="home" role="tab" data-toggle="tab">{{item.productName}}</a>
                        </li>
                        <li role="presentation">
                            <a href="#tab_summary" aria-controls="" role="tab" data-toggle="tab" style="color: #72afd2">Order
                                summary</a>
                        </li>
                    </ul>

                    <div class="tab-content">

                        <div role="tabpanel" class="tab-pane clearfix" id="tab_{{item.productId}}"
                             ng-repeat="item in productionSheetOrder.productionSheetOrderItems"
                             ng-class="{'active': $index == 0}">

                            <div class="col-md-6 margin-bottom-20">
                                <p>
                                    <label>Spec: </label>
                                    <span>{{item.thickness | number}} x {{item.width | number}} / {{item.blowingWidth | number}} x {{item.length | number}}</span>
                                </p>

                                <p>
                                    <label>Thickness: </label>
                                    <span>{{item.thickness | number}} MM</span>
                                </p>

                                <p>
                                    <label>Width: </label>
                                    <span>{{item.width}} MM</span>
                                </p>

                                <p>
                                    <label>Length: </label>
                                    <span>{{item.length}} MM</span>
                                </p>

                                <p>
                                    <label>Gusset: </label>
                                    <span>{{item.gusset}} MM</span>
                                </p>

                                <p>
                                    <label>Emboss: </label>
                                    <span>{{item.emboss}}</span>
                                </p>
                            </div>

                            <div class="col-md-6 margin-bottom-20">
                                <p>
                                    <label>Weight per metre: </label>
                                    <span>{{item.weightPerLengthUnit | number}} G/M</span>
                                </p>

                                <p>
                                    <label>Length per roll: </label>
                                    <span>{{item.lengthPerRoll}} M</span>
                                </p>

                                <p>
                                    <label>Weight per roll: </label>
                                    <span>{{item.weightPerRoll | number}} KG</span>
                                </p>

                                <p>
                                    <label>Total rolls: </label>
                                    <span>{{item.totalRolls}} ROLLS</span>
                                </p>

                                <p>
                                    <label>Total weight: </label>
                                    <span>{{item.totalWeight | number}} KG</span>
                                </p>

                                <p>
                                    <label>Total blowing weight: </label>
                                    <span>{{item.totalBlowingWeight | number}} KG</span>
                                </p>
                            </div>
                        </div>

                        <div role="tabpanel" class="tab-pane clearfix" id="tab_summary">
                            <div class="clearfix" style="border-bottom: 1px solid #ccc;"
                                 ng-repeat="item in productionSheetOrder.productionSheetOrderItems">
                                <div class="col-md-12 margin-bottom-10">
                                    <h4>{{item.product.name}}</h4>
                                </div>
                                <div class="col-md-6 margin-bottom-20">
                                    <p>
                                        <label>Film Color: </label>
                                        <span>{{item.orderItem.filmColor}}</span>
                                    </p>
                                    <p>
                                        <label>Master Batch Code: </label>
                                        <span>{{item.product.masterBatchCode}}</span>
                                    </p>
                                    <p>
                                        <label>Master Batch Amount: </label>
                                        <span>{{item.product.masterBatchAmount}}</span>
                                    </p>
                                    <p>
                                        <label>Ink Code: </label>
                                        <span>{{item.product.inkCode}}</span>
                                    </p>
                                    <p>
                                        <label>Ink Amount: </label>
                                        <span>{{item.product.inkAmount}}</span>
                                    </p>
                                    <p>
                                        <label>Packaging: </label>
                                        <span>{{item.product.packaging}}</span>
                                    </p>
                                    <p>
                                        <label>Pieces per outerbag: </label>
                                        <span>{{item.orderItem.piecesPerOuterbag}}</span>
                                    </p>
                                </div>
                                <div class="col-md-6 margin-bottom-20">
                                    <p>
                                        <label>Layer of Carton Box: </label>
                                        <span>{{item.product.cartonBoxLayer}}</span>
                                    </p>
                                    <p>
                                        <label>Carton Length: </label>
                                        <span>{{item.product.cartonLength}}</span>
                                    </p>
                                    <p>
                                        <label>Carton Width: </label>
                                        <span>{{item.product.cartonWidth}}</span>
                                    </p>
                                    <p>
                                        <label>Carton Height: </label>
                                        <span>{{item.product.cartonHeight}}</span>
                                    </p>
                                    <p>
                                        <label>Number of boxes: </label>
                                        <span>{{item.orderItem.totalCartons}}</span>
                                    </p>

                                </div>
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

            </div>
            <div class="text-right">
                <button type="submit" class="btn btn-info">Generate</button>
            </div>
        </div>
    </form>

</div>
</@layout.admin>