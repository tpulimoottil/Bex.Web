(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemCategoryController', ShipmentItemCategoryController);

    ShipmentItemCategoryController.$inject = ['ShipmentItemCategory'];

    function ShipmentItemCategoryController(ShipmentItemCategory) {

        var vm = this;

        vm.shipmentItemCategories = [];

        loadAll();

        function loadAll() {
            ShipmentItemCategory.query(function(result) {
                vm.shipmentItemCategories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
