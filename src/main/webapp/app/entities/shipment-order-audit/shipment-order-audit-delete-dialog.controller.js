(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentOrderAuditDeleteController',ShipmentOrderAuditDeleteController);

    ShipmentOrderAuditDeleteController.$inject = ['$uibModalInstance', 'entity', 'ShipmentOrderAudit'];

    function ShipmentOrderAuditDeleteController($uibModalInstance, entity, ShipmentOrderAudit) {
        var vm = this;

        vm.shipmentOrderAudit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ShipmentOrderAudit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
