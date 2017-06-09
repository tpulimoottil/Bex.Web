(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentOrderDetailController', ShipmentOrderDetailController);

    ShipmentOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ShipmentOrder', 'ShipmentAddress', 'User', 'ShipmentOrderAudit', 'ShipmentItem'];

    function ShipmentOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, ShipmentOrder, ShipmentAddress, User, ShipmentOrderAudit, ShipmentItem) {
        var vm = this;

        vm.shipmentOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bexWebApp:shipmentOrderUpdate', function(event, result) {
            vm.shipmentOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
