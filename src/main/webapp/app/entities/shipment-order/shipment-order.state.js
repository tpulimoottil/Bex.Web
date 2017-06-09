(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shipment-order', {
            parent: 'entity',
            url: '/shipment-order?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentOrders'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-order/shipment-orders.html',
                    controller: 'ShipmentOrderController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('shipment-order-detail', {
            parent: 'shipment-order',
            url: '/shipment-order/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentOrder'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-order/shipment-order-detail.html',
                    controller: 'ShipmentOrderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ShipmentOrder', function($stateParams, ShipmentOrder) {
                    return ShipmentOrder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shipment-order',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shipment-order-detail.edit', {
            parent: 'shipment-order-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-order/shipment-order-dialog.html',
                    controller: 'ShipmentOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentOrder', function(ShipmentOrder) {
                            return ShipmentOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-order.new', {
            parent: 'shipment-order',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-order/shipment-order-dialog.html',
                    controller: 'ShipmentOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                createdOn: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shipment-order', null, { reload: 'shipment-order' });
                }, function() {
                    $state.go('shipment-order');
                });
            }]
        })
        .state('shipment-order.edit', {
            parent: 'shipment-order',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-order/shipment-order-dialog.html',
                    controller: 'ShipmentOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentOrder', function(ShipmentOrder) {
                            return ShipmentOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-order', null, { reload: 'shipment-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-order.delete', {
            parent: 'shipment-order',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-order/shipment-order-delete-dialog.html',
                    controller: 'ShipmentOrderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ShipmentOrder', function(ShipmentOrder) {
                            return ShipmentOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-order', null, { reload: 'shipment-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
