<#import "layout/adminLayout.ftl" as layout>

<@layout.admin content_header_title="Content not found">

<!-- Main content -->
<section class="content">

    <div class="error-page">
        <div class="error-content">
            <h3><i class="fa fa-warning text-yellow"></i> Oops! Content not found.</h3>

            <p>
                We could not find the page you were looking for.
                Meanwhile, you may <a href='/'>return to dashboard</a> or try using the search form.
            </p>


        </div>

    </div>

</section>

</@layout.admin>