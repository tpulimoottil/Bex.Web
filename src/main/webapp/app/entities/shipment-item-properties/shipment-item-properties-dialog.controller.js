(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemPropertiesDialogController', ShipmentItemPropertiesDialogController);

    ShipmentItemPropertiesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ShipmentItemProperties'];

    function ShipmentItemPropertiesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ShipmentItemProperties) {
        var vm = this;

        vm.shipmentItemProperties = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shipmentItemProperties.id !== null) {
                ShipmentItemProperties.update(vm.shipmentItemProperties, onSaveSuccess, onSaveError);
            } else {
                ShipmentItemProperties.save(vm.shipmentItemProperties, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bexWebApp:shipmentItemPropertiesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
