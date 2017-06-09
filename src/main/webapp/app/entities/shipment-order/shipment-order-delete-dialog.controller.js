(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentOrderDeleteController',ShipmentOrderDeleteController);

    ShipmentOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'ShipmentOrder'];

    function ShipmentOrderDeleteController($uibModalInstance, entity, ShipmentOrder) {
        var vm = this;

        vm.shipmentOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ShipmentOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
