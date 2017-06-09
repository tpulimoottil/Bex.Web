(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentOrderAuditController', ShipmentOrderAuditController);

    ShipmentOrderAuditController.$inject = ['ShipmentOrderAudit'];

    function ShipmentOrderAuditController(ShipmentOrderAudit) {

        var vm = this;

        vm.shipmentOrderAudits = [];

        loadAll();

        function loadAll() {
            ShipmentOrderAudit.query(function(result) {
                vm.shipmentOrderAudits = result;
                vm.searchQuery = null;
            });
        }
    }
})();
