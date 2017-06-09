(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemCategoryDetailController', ShipmentItemCategoryDetailController);

    ShipmentItemCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ShipmentItemCategory'];

    function ShipmentItemCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, ShipmentItemCategory) {
        var vm = this;

        vm.shipmentItemCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bexWebApp:shipmentItemCategoryUpdate', function(event, result) {
            vm.shipmentItemCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
