(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemDetailController', ShipmentItemDetailController);

    ShipmentItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ShipmentItem', 'ShipmentItemProperties', 'User', 'ShipmentItemCategory', 'ShipmentOrder'];

    function ShipmentItemDetailController($scope, $rootScope, $stateParams, previousState, entity, ShipmentItem, ShipmentItemProperties, User, ShipmentItemCategory, ShipmentOrder) {
        var vm = this;

        vm.shipmentItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bexWebApp:shipmentItemUpdate', function(event, result) {
            vm.shipmentItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
