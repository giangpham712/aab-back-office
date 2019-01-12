<div id="modal_edit_reading" class="modal modal fade in" ng-modal modal-id="edit_reading">
    <div class="modal-dialog">
        <div class="modal-content">
            <form name="editReadingForm" ng-submit="saveReading()">
                <div class="modal-header clearfix">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <div class="col-md-12">
                        <h3 class="modal-title">Reading Information</h3>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="clearfix margin-bottom-10">
                        <div class="col-md-6">
                            <label class="small">Value</label>
                            <input type="text" class="form-control" placeholder="Value"
                                   ng-model="reading.value" autofocus>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary" ng-disabled="editReadingForm.$invalid">Save
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div><!-- /.modal -->