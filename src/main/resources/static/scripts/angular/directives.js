"use strict";

angular.module("AAB.directives", ["selectize"]);

angular.module("AAB.directives")
    .directive("editableGrid", function () {

        return {
            restrict: "AE",
            replace: true,
            controller: function ($document, $scope, $element, $attrs) {
                var $rowEditor = $(".row-editor");
                $scope.editingItem = null;

                $document.on("click", function (e) {

                    if ($(e.target).parents(".select2-container").length > 0 ||
                        $(e.target).parents(".row-editor").length) {
                        return;
                    }

                    if ($scope.editingItem) {
                        _saveRow();
                        $scope.$apply();
                    }
                })


                var _saveRow = function () {
                    $scope.editingItem = null;
                    $rowEditor.hide();
                }

                $scope.editRow = function (event, item, index) {

                    if ($(event.target).parents("td.select").length > 0) {
                        return;
                    }

                    if ($scope.editingItem) {
                        _saveRow();
                    }

                    var row = event.currentTarget
                    var newOffset = row.offsetTop + 1;

                    var btnOptions = $rowEditor.find("button.options");

                    if (btnOptions.attr("aria-expanded") === "true") {
                        btnOptions.click();
                    }

                    $rowEditor.css("top", newOffset + "px");
                    $rowEditor.show();
                    var cell = $(event.target).closest('td');


                    $scope.editingItem = item;
                    $scope.editingItem.itemIndex = index;

                    setTimeout(function () {
                        if (cell && cell[0].className !== "item") {
                            var field = cell[0].className;
                            $rowEditor.find("td." + field + " input").first().focus();

                        }
                    }, 0);


                    event.stopPropagation();
                }
            }
        }
    });

angular.module("AAB.directives")
    .directive('ngDatetimePicker', function ($parse) {
        return {
            restrict: 'AE',
            require: 'ngModel',
            link: function (scope, element, attrs, ngModel) {

                var dateFormat = attrs.dateFormat;
                var defaultDate = scope.$eval(attrs.ngModel);
                var resetValue = false;

                var picker = element.datetimepicker({
                    inline: false,
                    locale: "vi",
                    format: dateFormat

                })

                if (attrs.greaterThan) {
                    scope.$watch(attrs.greaterThan, function (newValue, oldValue) {

                        if (_.isEmpty(newValue)) {
                            return;
                        }

                        var etd = $parse(attrs.greaterThan)(scope);
                        setTimeout(function () {

                            picker.data("DateTimePicker").minDate(moment(etd, dateFormat))
                        }, 0);
                    })
                }


                picker.data("DateTimePicker").date(defaultDate);

                picker.on("dp.change", function (e) {
                    return scope.$apply(function () {
                        var i, obj, objPath, path, _i, _len, _results;
                        if (!e.date) return;

                        objPath = attrs.ngModel.split(".");
                        obj = scope;
                        _results = [];
                        for (i = _i = 0, _len = objPath.length; _i < _len; i = ++_i) {
                            path = objPath[i];
                            if (!obj[path]) {
                                obj[path] = {};
                            }
                            if (i === objPath.length - 1) {
                                if (resetValue) {
                                    resetValue = false;
                                    _results.push(obj[path] = null);
                                } else {
                                    _results.push(obj[path] = e.date.format(dateFormat));
                                }
                            } else {
                                _results.push(obj = obj[path]);
                            }
                        }
                        return _results;
                    });
                });

                var nextElement = element.next();
                if (nextElement.hasClass("input-group-addon")) {
                    nextElement.on('click', function () {
                        picker.data("DateTimePicker").toggle();
                    })
                }

                scope.$watch(attrs.ngModel, function (newValue, oldValue) {

                    if (oldValue && !newValue) {
                        return resetValue = true;
                    }

                });
            }
        };
    });

angular.module("AAB.directives")
    .directive("customSelect", function () {

        return {
            restrict: "AE",
            scope: {
                dataPlaceHolder: '@'
            },
            replace: true,
            controller: function ($scope, $element, $attrs, $timeout) {
                $timeout(function () {
                    $element.selectize({
                        create: true,
                    });
                });


            }
        }
    });

angular.module("AAB.directives")
    .directive("ngModal", function () {
        return {
            restrict: "AE",
            scope: {
                modalId: '@',
            },
            controller: function ($scope, $element, $attrs, ModalService) {
                $element.modal({ show: false });
                ModalService.registerModal($scope.modalId, $element);
            }
        }
    });

angular.module("AAB.directives")
    .directive("customCheckbox", function () {
        return {
            restrict: "AE",
            replace: true,
            require: 'ngModel',
            controller: function ($scope, $element, $attrs, $parse) {

                setTimeout(function () {

                    var icheck = $element.iCheck({
                        checkboxClass: 'icheckbox_minimal-blue',
                    });

                    icheck.on("ifChecked", function (event) {

                        var model = $parse($attrs.ngModel);
                        var modelSetter = model.assign;
                        $scope.$apply(function() {
                            modelSetter($scope, true);
                        });

                    });

                    icheck.on("ifUnchecked", function (event) {

                        var model = $parse($attrs.ngModel);
                        var modelSetter = model.assign;
                        $scope.$apply(function() {
                            modelSetter($scope, false);
                        });

                    });
                });



            }
        }
    });

angular.module("AAB.directives")
    .directive('ngAutoNumeric', function ($parse) {

        var options = {
            currency: {aSep: "", aDec: ".", mDec: 6, lZero: "deny", pSign: "p", aSign: "", vMax: "1000000000"},
            days: {aSep: ".", aDec: ",", mDec: 0, lZero: "deny", vMin: "0", vMax: "90"},
            integer: {aSep: "", aDec: ".", mDec: 0, wEmpty: 'zero', lZero: "deny", vMinx: 0, vMax: "1000000"},
            decimal: {aSep: ",", aDec: ".", mDec: 4, wEmpty: 'zero', lZero: "deny", vMinx: 0, vMax: "1000000"},
        };

        return {
            restrict: 'A',
            require: '^ngModel',
            scope: {
                config: "=?"
            },
            link: function (scope, element, attrs, ngModel) {

                var format = attrs.ngAutoNumeric;
                var settings = angular.extend(options[format], scope.config);
                element.autoNumeric('init', settings)

                element.on("keyup", function () {
                    ngModel.$setViewValue(element.val());
                });
            }
        }
    });