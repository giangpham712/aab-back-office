<#import "layout/adminLayout.ftl" as layout>

<#assign stylesheets>
</#assign>

<#assign top_scripts>
<script>
    //<![CDATA[
    window.ViewData = {machine: ${machine}, mode: "${mode}"};
    //]]>
</script>
</#assign>

<#assign bottom_scripts>
<script type="text/javascript" src="/scripts/angular/machines/app.js"></script>
<script>

</script>
</#assign>

<@layout.admin sidebar_class="sidebar-collapse" content_header_title="${headerTitle}" stylesheets=stylesheets top_scripts=top_scripts bottom_scripts=bottom_scripts ng_app="machines">
<div class="row clearfix" ng-controller="EditMachineCtrl">
    <form name=" newMachineForm" novalidate>
        <div class="col-xs-12">
            <div class="margin-bottom-20">

                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-3">
                        <label class="small">Display name</label>
                        <input type="text" class="form-control" placeholder="Name"
                               name="displayName"
                               ng-model="machine.name"
                               required>
                    </div>
                </div>
                <div class="row clearfix margin-bottom-10">
                    <div class="col-md-5">
                        <label class="small">Description</label>
                        <textarea class="form-control" placeholder="Description"
                                  name="description" rows="4"
                                  ng-model="machine.description">
                        </textarea>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-xs-8">

            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active">
                        <a href data-target="#machine_information" aria-controls="machine_information" role="tab"
                           data-toggle="tab">Machine information</a>
                    </li>
                </ul>
                <div class="tab-content" ng-cloak>
                    <div class="tab-pane clearfix active" id="machine_information">
                        <div>

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

                <button type="button" class="btn btn-info" ng-click="saveMachine()">Save</button>
            </div>
        </div>
    </form>
</div>
</@layout.admin>