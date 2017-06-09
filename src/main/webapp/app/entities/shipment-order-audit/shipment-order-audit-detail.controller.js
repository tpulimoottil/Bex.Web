(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentOrderAuditDetailController', ShipmentOrderAuditDetailController);

    ShipmentOrderAuditDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ShipmentOrderAudit', 'ShipmentOrder', 'User'];

    function ShipmentOrderAuditDetailController($scope, $rootScope, $stateParams, previousState, entity, ShipmentOrderAudit, ShipmentOrder, User) {
        var vm = this;

        vm.shipmentOrderAudit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bexWebApp:shipmentOrderAuditUpdate', function(event, result) {
            vm.shipmentOrderAudit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
