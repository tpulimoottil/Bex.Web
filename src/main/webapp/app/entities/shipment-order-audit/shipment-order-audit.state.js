(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shipment-order-audit', {
            parent: 'entity',
            url: '/shipment-order-audit',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentOrderAudits'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-order-audit/shipment-order-audits.html',
                    controller: 'ShipmentOrderAuditController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('shipment-order-audit-detail', {
            parent: 'shipment-order-audit',
            url: '/shipment-order-audit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentOrderAudit'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-order-audit/shipment-order-audit-detail.html',
                    controller: 'ShipmentOrderAuditDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ShipmentOrderAudit', function($stateParams, ShipmentOrderAudit) {
                    return ShipmentOrderAudit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shipment-order-audit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shipment-order-audit-detail.edit', {
            parent: 'shipment-order-audit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-order-audit/shipment-order-audit-dialog.html',
                    controller: 'ShipmentOrderAuditDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentOrderAudit', function(ShipmentOrderAudit) {
                            return ShipmentOrderAudit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-order-audit.new', {
            parent: 'shipment-order-audit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-order-audit/shipment-order-audit-dialog.html',
                    controller: 'ShipmentOrderAuditDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                comments: null,
                                additionalComments: null,
                                createdOn: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shipment-order-audit', null, { reload: 'shipment-order-audit' });
                }, function() {
                    $state.go('shipment-order-audit');
                });
            }]
        })
        .state('shipment-order-audit.edit', {
            parent: 'shipment-order-audit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-order-audit/shipment-order-audit-dialog.html',
                    controller: 'ShipmentOrderAuditDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentOrderAudit', function(ShipmentOrderAudit) {
                            return ShipmentOrderAudit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-order-audit', null, { reload: 'shipment-order-audit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-order-audit.delete', {
            parent: 'shipment-order-audit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-order-audit/shipment-order-audit-delete-dialog.html',
                    controller: 'ShipmentOrderAuditDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ShipmentOrderAudit', function(ShipmentOrderAudit) {
                            return ShipmentOrderAudit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-order-audit', null, { reload: 'shipment-order-audit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
