(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentAddressDetailController', ShipmentAddressDetailController);

    ShipmentAddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ShipmentAddress', 'State'];

    function ShipmentAddressDetailController($scope, $rootScope, $stateParams, previousState, entity, ShipmentAddress, State) {
        var vm = this;

        vm.shipmentAddress = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bexWebApp:shipmentAddressUpdate', function(event, result) {
            vm.shipmentAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
