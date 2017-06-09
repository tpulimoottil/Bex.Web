(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .controller('ShipmentItemDialogController', ShipmentItemDialogController);

    ShipmentItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ShipmentItem', 'ShipmentItemProperties', 'User', 'ShipmentItemCategory', 'ShipmentOrder'];

    function ShipmentItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ShipmentItem, ShipmentItemProperties, User, ShipmentItemCategory, ShipmentOrder) {
        var vm = this;

        vm.shipmentItem = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.itemproperties = ShipmentItemProperties.query({filter: 'shipmentitem-is-null'});
        $q.all([vm.shipmentItem.$promise, vm.itemproperties.$promise]).then(function() {
            if (!vm.shipmentItem.itemPropertiesId) {
                return $q.reject();
            }
            return ShipmentItemProperties.get({id : vm.shipmentItem.itemPropertiesId}).$promise;
        }).then(function(itemProperties) {
            vm.itemproperties.push(itemProperties);
        });
        vm.users = User.query();
        vm.shipmentitemcategories = ShipmentItemCategory.query();
        vm.shipmentorders = ShipmentOrder.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shipmentItem.id !== null) {
                ShipmentItem.update(vm.shipmentItem, onSaveSuccess, onSaveError);
            } else {
                ShipmentItem.save(vm.shipmentItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bexWebApp:shipmentItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.freeStorageEndingDate = false;
        vm.datePickerOpenStatus.createdOn = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
