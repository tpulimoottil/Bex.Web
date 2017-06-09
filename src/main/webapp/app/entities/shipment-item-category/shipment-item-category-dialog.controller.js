(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemCategoryDialogController', ShipmentItemCategoryDialogController);

    ShipmentItemCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ShipmentItemCategory'];

    function ShipmentItemCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ShipmentItemCategory) {
        var vm = this;

        vm.shipmentItemCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.shipmentitemcategories = ShipmentItemCategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shipmentItemCategory.id !== null) {
                ShipmentItemCategory.update(vm.shipmentItemCategory, onSaveSuccess, onSaveError);
            } else {
                ShipmentItemCategory.save(vm.shipmentItemCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bexWebApp:shipmentItemCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
