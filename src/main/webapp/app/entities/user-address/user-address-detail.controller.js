(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('UserAddressDetailController', UserAddressDetailController);

    UserAddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserAddress', 'State', 'User'];

    function UserAddressDetailController($scope, $rootScope, $stateParams, previousState, entity, UserAddress, State, User) {
        var vm = this;

        vm.userAddress = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bexWebApp:userAddressUpdate', function(event, result) {
            vm.userAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
