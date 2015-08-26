<div id="modal_new_customer" class="modal modal fade in" ng-modal modal-id="new_customer">
    <div class="modal-dialog">
        <div class="modal-content">
            <form name="newCustomerForm" ng-submit="saveNewCustomer()">
                <div class="modal-header clearfix">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <div class="col-md-12">
                        <h3 class="modal-title">Customer Information</h3>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="clearfix margin-bottom-10">
                        <div class="col-md-6">
                            <table>
                                <tr>
                                    <td class="title">
                                        <label>Title</label>
                                        <input type="text" class="form-control" ng-model="newCustomer.title">
                                    </td>
                                    <td>
                                        <label>First name</label>
                                        <input type="text" class="form-control" ng-model="newCustomer.firstName">
                                    </td>
                                    <td>
                                        <label>Middle name</label>
                                        <input type="text" class="form-control" ng-model="newCustomer.middleName">
                                    </td>
                                    <td>
                                        <label>Last name</label>
                                        <input type="text" class="form-control" ng-model="newCustomer.lastName">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="company" colspan="4">
                                        <label>Company</label>
                                        <input type="text" class="form-control" ng-model="newCustomer.companyName">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <label>Display name</label><span class="text-red required">*</span>
                                        <input type="text" class="form-control" name="displayName"
                                               ng-model="newCustomer.displayName"
                                               required>
                                        <p class="error small text-red"
                                           ng-show="newCustomerForm.displayName.$error.required && !newCustomerForm.displayName.$pristine">Display name is required</p>
                                    </td>
                                </tr>
                            </table>

                        </div>

                        <div class="col-md-6">
                            <table class="">
                                <tr>
                                    <td class="email" colspan="3">
                                        <label>Email</label>
                                        <input type="email" class="form-control">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="phone">
                                        <label>Phone</label>
                                        <input type="text" class="form-control">
                                    </td>
                                    <td class="website" colspan="2">
                                        <label>Website</label>
                                        <input type="text" class="form-control">
                                    </td>
                                </tr>
                                <tr></tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary" ng-disabled="newCustomerForm.$invalid">Save</button>
                    </div>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div><!-- /.modal -->