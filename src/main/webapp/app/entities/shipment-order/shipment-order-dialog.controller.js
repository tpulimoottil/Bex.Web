(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentOrderDialogController', ShipmentOrderDialogController);

    ShipmentOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ShipmentOrder', 'ShipmentAddress', 'User', 'ShipmentOrderAudit', 'ShipmentItem'];

    function ShipmentOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ShipmentOrder, ShipmentAddress, User, ShipmentOrderAudit, ShipmentItem) {
        var vm = this;

        vm.shipmentOrder = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.shipmentaddresses = ShipmentAddress.query({filter: 'shipmentorder-is-null'});
        $q.all([vm.shipmentOrder.$promise, vm.shipmentaddresses.$promise]).then(function() {
            if (!vm.shipmentOrder.shipmentAddressId) {
                return $q.reject();
            }
            return ShipmentAddress.get({id : vm.shipmentOrder.shipmentAddressId}).$promise;
        }).then(function(shipmentAddress) {
            vm.shipmentaddresses.push(shipmentAddress);
        });
        vm.users = User.query();
        vm.shipmentorderaudits = ShipmentOrderAudit.query();
        vm.shipmentitems = ShipmentItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shipmentOrder.id !== null) {
                ShipmentOrder.update(vm.shipmentOrder, onSaveSuccess, onSaveError);
            } else {
                ShipmentOrder.save(vm.shipmentOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bexWebApp:shipmentOrderUpdate', result);
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
