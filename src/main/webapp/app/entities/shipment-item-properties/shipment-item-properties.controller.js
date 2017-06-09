(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemPropertiesController', ShipmentItemPropertiesController);

    ShipmentItemPropertiesController.$inject = ['ShipmentItemProperties'];

    function ShipmentItemPropertiesController(ShipmentItemProperties) {

        var vm = this;

        vm.shipmentItemProperties = [];

        loadAll();

        function loadAll() {
            ShipmentItemProperties.query(function(result) {
                vm.shipmentItemProperties = result;
                vm.searchQuery = null;
            });
        }
    }
})();
