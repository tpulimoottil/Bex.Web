(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemPropertiesDeleteController',ShipmentItemPropertiesDeleteController);

    ShipmentItemPropertiesDeleteController.$inject = ['$uibModalInstance', 'entity', 'ShipmentItemProperties'];

    function ShipmentItemPropertiesDeleteController($uibModalInstance, entity, ShipmentItemProperties) {
        var vm = this;

        vm.shipmentItemProperties = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ShipmentItemProperties.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
