(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shipment-item-properties', {
            parent: 'entity',
            url: '/shipment-item-properties',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentItemProperties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-item-properties/shipment-item-properties.html',
                    controller: 'ShipmentItemPropertiesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('shipment-item-properties-detail', {
            parent: 'shipment-item-properties',
            url: '/shipment-item-properties/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentItemProperties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-item-properties/shipment-item-properties-detail.html',
                    controller: 'ShipmentItemPropertiesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ShipmentItemProperties', function($stateParams, ShipmentItemProperties) {
                    return ShipmentItemProperties.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shipment-item-properties',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shipment-item-properties-detail.edit', {
            parent: 'shipment-item-properties-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item-properties/shipment-item-properties-dialog.html',
                    controller: 'ShipmentItemPropertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentItemProperties', function(ShipmentItemProperties) {
                            return ShipmentItemProperties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-item-properties.new', {
            parent: 'shipment-item-properties',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item-properties/shipment-item-properties-dialog.html',
                    controller: 'ShipmentItemPropertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                height: null,
                                length: null,
                                width: null,
                                weight: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shipment-item-properties', null, { reload: 'shipment-item-properties' });
                }, function() {
                    $state.go('shipment-item-properties');
                });
            }]
        })
        .state('shipment-item-properties.edit', {
            parent: 'shipment-item-properties',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item-properties/shipment-item-properties-dialog.html',
                    controller: 'ShipmentItemPropertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentItemProperties', function(ShipmentItemProperties) {
                            return ShipmentItemProperties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-item-properties', null, { reload: 'shipment-item-properties' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-item-properties.delete', {
            parent: 'shipment-item-properties',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item-properties/shipment-item-properties-delete-dialog.html',
                    controller: 'ShipmentItemPropertiesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ShipmentItemProperties', function(ShipmentItemProperties) {
                            return ShipmentItemProperties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-item-properties', null, { reload: 'shipment-item-properties' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
