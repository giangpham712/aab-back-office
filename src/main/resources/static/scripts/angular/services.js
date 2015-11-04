angular.module("AAB.services", []);


angular.module("AAB.services")
    .service("NotificationService", function () {


        this.notifySuccess = function (message) {
            alertify.logPosition("right");
            alertify.success('<i class="icon fa fa-check"></i><span class="title">Successful</span><span class="message">' + message + '</span>');
        }

        this.notifyError = function (message) {
            alertify.logPosition("right");
            alertify.error('<i class="icon fa fa-times"></i><span class="title">Error</span></i><span class="message">' + message + '</span>');
        }

    })

angular.module("AAB.services")
    .factory("ModalService", function () {
        var modalService = {
            modals: {},
            registerModal: function (name, modal) {
                this.modals[name] = modal;
            },
            showModal: function (name) {
                this.modals[name].modal("show");
            },
            closeModal: function (name) {
                this.modals[name].modal("hide");
            }
        };

        return modalService;
    });