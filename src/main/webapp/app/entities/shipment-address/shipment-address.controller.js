(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentAddressController', ShipmentAddressController);

    ShipmentAddressController.$inject = ['ShipmentAddress'];

    function ShipmentAddressController(ShipmentAddress) {

        var vm = this;

        vm.shipmentAddresses = [];

        loadAll();

        function loadAll() {
            ShipmentAddress.query(function(result) {
                vm.shipmentAddresses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
