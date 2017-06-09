(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('UserAddressDialogController', UserAddressDialogController);

    UserAddressDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserAddress', 'State', 'User'];

    function UserAddressDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserAddress, State, User) {
        var vm = this;

        vm.userAddress = entity;
        vm.clear = clear;
        vm.save = save;
        vm.states = State.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userAddress.id !== null) {
                UserAddress.update(vm.userAddress, onSaveSuccess, onSaveError);
            } else {
                UserAddress.save(vm.userAddress, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bexWebApp:userAddressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
