(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentOrderAuditDialogController', ShipmentOrderAuditDialogController);

    ShipmentOrderAuditDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ShipmentOrderAudit', 'ShipmentOrder', 'User'];

    function ShipmentOrderAuditDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ShipmentOrderAudit, ShipmentOrder, User) {
        var vm = this;

        vm.shipmentOrderAudit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.shipmentorders = ShipmentOrder.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shipmentOrderAudit.id !== null) {
                ShipmentOrderAudit.update(vm.shipmentOrderAudit, onSaveSuccess, onSaveError);
            } else {
                ShipmentOrderAudit.save(vm.shipmentOrderAudit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bexWebApp:shipmentOrderAuditUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdOn = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
