<div id="modal_edit_customer" class="modal modal fade in" ng-modal modal-id="edit_customer">
    <div class="modal-dialog">
        <div class="modal-content">
            <form name="editCustomerForm" ng-submit="saveCustomer()">
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
                                        <input type="text" class="form-control" ng-model="customer.title">
                                    </td>
                                    <td>
                                        <label>First name</label>
                                        <input type="text" class="form-control" ng-model="customer.firstName">
                                    </td>
                                    <td>
                                        <label>Middle name</label>
                                        <input type="text" class="form-control" ng-model="customer.middleName">
                                    </td>
                                    <td>
                                        <label>Last name</label>
                                        <input type="text" class="form-control" ng-model="customer.lastName">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="company" colspan="4">
                                        <label>Company</label>
                                        <input type="text" class="form-control" ng-model="customer.companyName">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <label>Display name</label><span class="text-red required">*</span>
                                        <input type="text" class="form-control" name="displayName"
                                               ng-model="customer.displayName"
                                               required>

                                        <p class="error small text-red"
                                           ng-show="editCustomerForm.displayName.$error.required && !editCustomerForm.displayName.$pristine">
                                            Display name is required</p>
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
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary" ng-disabled="editCustomerForm.$invalid">Save
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div><!-- /.modal -->