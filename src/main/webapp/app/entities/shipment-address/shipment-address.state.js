(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shipment-address', {
            parent: 'entity',
            url: '/shipment-address',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentAddresses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-address/shipment-addresses.html',
                    controller: 'ShipmentAddressController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('shipment-address-detail', {
            parent: 'shipment-address',
            url: '/shipment-address/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentAddress'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-address/shipment-address-detail.html',
                    controller: 'ShipmentAddressDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ShipmentAddress', function($stateParams, ShipmentAddress) {
                    return ShipmentAddress.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shipment-address',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shipment-address-detail.edit', {
            parent: 'shipment-address-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-address/shipment-address-dialog.html',
                    controller: 'ShipmentAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentAddress', function(ShipmentAddress) {
                            return ShipmentAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-address.new', {
            parent: 'shipment-address',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-address/shipment-address-dialog.html',
                    controller: 'ShipmentAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                attentionTo: null,
                                addressLine1: null,
                                addressLine2: null,
                                landmark: null,
                                street: null,
                                postalCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shipment-address', null, { reload: 'shipment-address' });
                }, function() {
                    $state.go('shipment-address');
                });
            }]
        })
        .state('shipment-address.edit', {
            parent: 'shipment-address',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-address/shipment-address-dialog.html',
                    controller: 'ShipmentAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentAddress', function(ShipmentAddress) {
                            return ShipmentAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-address', null, { reload: 'shipment-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-address.delete', {
            parent: 'shipment-address',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-address/shipment-address-delete-dialog.html',
                    controller: 'ShipmentAddressDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ShipmentAddress', function(ShipmentAddress) {
                            return ShipmentAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-address', null, { reload: 'shipment-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
