(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemDeleteController',ShipmentItemDeleteController);

    ShipmentItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'ShipmentItem'];

    function ShipmentItemDeleteController($uibModalInstance, entity, ShipmentItem) {
        var vm = this;

        vm.shipmentItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ShipmentItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
