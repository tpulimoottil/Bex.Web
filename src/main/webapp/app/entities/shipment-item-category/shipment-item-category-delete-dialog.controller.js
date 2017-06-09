(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemCategoryDeleteController',ShipmentItemCategoryDeleteController);

    ShipmentItemCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'ShipmentItemCategory'];

    function ShipmentItemCategoryDeleteController($uibModalInstance, entity, ShipmentItemCategory) {
        var vm = this;

        vm.shipmentItemCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ShipmentItemCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
