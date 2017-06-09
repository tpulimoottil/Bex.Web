(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentAddressDialogController', ShipmentAddressDialogController);

    ShipmentAddressDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ShipmentAddress', 'State'];

    function ShipmentAddressDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ShipmentAddress, State) {
        var vm = this;

        vm.shipmentAddress = entity;
        vm.clear = clear;
        vm.save = save;
        vm.states = State.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shipmentAddress.id !== null) {
                ShipmentAddress.update(vm.shipmentAddress, onSaveSuccess, onSaveError);
            } else {
                ShipmentAddress.save(vm.shipmentAddress, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bexWebApp:shipmentAddressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
