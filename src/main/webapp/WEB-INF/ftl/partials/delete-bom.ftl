<div id="modal_delete_bom" class="modal modal fade in" ng-modal modal-id="delete_bom" style="">
    <div class="modal-dialog">
        <div class="modal-content">
            <form name="editBOMForm" ng-submit="deleteBOM(deletingBOM)" novalidate>
                <div class="modal-header clearfix">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <div class="col-md-12">
                        <h3 class="modal-title">Delete Bill of Materials</h3>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="clearfix margin-bottom-10">
                        <div class="col-md-12">
                            <p>You are about to delete Bill of Materials <strong>{{deletingBOM.name}}</strong>. Are you sure to
                                proceed?
                            </p>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">No</button>
                        <button type="submit" class="btn btn-primary">Yes</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>