(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemPropertiesDetailController', ShipmentItemPropertiesDetailController);

    ShipmentItemPropertiesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ShipmentItemProperties'];

    function ShipmentItemPropertiesDetailController($scope, $rootScope, $stateParams, previousState, entity, ShipmentItemProperties) {
        var vm = this;

        vm.shipmentItemProperties = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bexWebApp:shipmentItemPropertiesUpdate', function(event, result) {
            vm.shipmentItemProperties = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
