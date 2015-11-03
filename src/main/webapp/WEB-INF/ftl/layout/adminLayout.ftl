<#macro admin sidebar_class="" content_header_title="" stylesheets="" top_scripts="" bottom_scripts="" ng_app="">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/bower_components/bootstrap/dist/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/bower_components/bootstrap3-datetimepicker/build/css/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" type="text/css" href="/bower_components/angular/angular-csp.css"/>
    <link rel="stylesheet" type="text/css" href="/bower_components/font-awesome/css/font-awesome.min.css"/>

    <link rel="stylesheet" type="text/css" href="/bower_components/jquery-icheck/skins/minimal/blue.css"/>
    <link rel="stylesheet" type="text/css" href="/bower_components/selectize/dist/css/selectize.bootstrap3.css"/>

    <link rel="stylesheet" type="text/css" href="/bower_components/alertify.js/dist/css/alertify.css"/>


    <link rel="stylesheet" type="text/css" href="/css/glyphicons.css"/>
    <link rel="stylesheet" type="text/css" href="/css/skins/skin-blue.css"/>
    <link rel="stylesheet" type="text/css" href="/css/styles.css"/>
    <link rel="stylesheet" type="text/css" href="/css/custom.css"/>
${stylesheets}
${top_scripts}
</head>
<body class="skin-blue fixed sidebar-mini ${sidebar_class}" ng-app="${ng_app}">
<div class="wrapper">
    <header class="main-header">
        <#include "../partials/main-header.ftl"/>
    </header>
    <aside class="main-sidebar">
        <#include "../partials/sidebar.ftl"/>
    </aside>
    <div class="content-wrapper">
        <header class="content-header">
            <h1>
            ${content_header_title}
                <small></small>
            </h1>

        </header>
        <section class="content">
            <#nested>
        </section>

    </div>
</div>
<div class="loading-container" ng-show="$root.pageLoading">
    <div class="bouncer">
        <div class="double-bounce1"></div>
        <div class="double-bounce2"></div>
    </div>
</div>

<script type="text/javascript" src="/bower_components/jquery/dist/jquery.min.js"></script>

<script type="text/javascript" src="/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="/bower_components/jquery-icheck/icheck.min.js"></script>

<script type="text/javascript" src="/bower_components/moment/min/moment-with-locales.min.js"></script>

<script type="text/javascript" src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/bower_components/bootstrap3-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>

<script type="text/javascript" src="/bower_components/underscore/underscore-min.js"></script>

<script type="text/javascript" src="/bower_components/tv4/tv4.js"></script>

<script type="text/javascript" src="/bower_components/angular/angular.min.js"></script>
<script type="text/javascript" src="/bower_components/restangular/dist/restangular.min.js"></script>
<script type="text/javascript" src="/bower_components/angular-utils-pagination/dirPagination.js"></script>


<script type="text/javascript" src="/bower_components/selectize/dist/js/standalone/selectize.min.js"></script>
<script type="text/javascript" src="/bower_components/angular-selectize2/dist/selectize.js"></script>
<script type="text/javascript" src="/bower_components/autoNumeric/autoNumeric.js"></script>
<script type="text/javascript" src="/bower_components/alertify.js/dist/js/alertify.js"></script>

<script type="text/javascript" src="/scripts/main.js"></script>
<script type="text/javascript" src="/scripts/angular/models.js"></script>
<script type="text/javascript" src="/scripts/angular/rest.js"></script>
<script type="text/javascript" src="/scripts/angular/schemas.js"></script>
<script type="text/javascript" src="/scripts/angular/services.js"></script>
<script type="text/javascript" src="/scripts/angular/directives.js"></script>

${bottom_scripts}

</body>
</html>
</#macro>