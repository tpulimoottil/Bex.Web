(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentAddressDeleteController',ShipmentAddressDeleteController);

    ShipmentAddressDeleteController.$inject = ['$uibModalInstance', 'entity', 'ShipmentAddress'];

    function ShipmentAddressDeleteController($uibModalInstance, entity, ShipmentAddress) {
        var vm = this;

        vm.shipmentAddress = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ShipmentAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
