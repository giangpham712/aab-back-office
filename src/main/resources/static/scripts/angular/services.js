angular.module("AAB.services", []);

angular.module("AAB.services")
    .service("ModalService", function () {



    });

angular.module("AAB.services")
    .service("NotificationService", function () {


        this.notifySuccess = function (message) {
            alertify.success('<i class="icon fa fa-check"></i><span class="title">Successful</span><span class="message">' + message + '</span>');
        }

        this.notifyError = function (message) {
            alertify.error('<i class="icon fa fa-times"><span class="title">Error</span></i><span class="message">' + message + '</span>');
        }

    })