(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shipment-item', {
            parent: 'entity',
            url: '/shipment-item?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentItems'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-item/shipment-items.html',
                    controller: 'ShipmentItemController',
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
        .state('shipment-item-detail', {
            parent: 'shipment-item',
            url: '/shipment-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentItem'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-item/shipment-item-detail.html',
                    controller: 'ShipmentItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ShipmentItem', function($stateParams, ShipmentItem) {
                    return ShipmentItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shipment-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shipment-item-detail.edit', {
            parent: 'shipment-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item/shipment-item-dialog.html',
                    controller: 'ShipmentItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentItem', function(ShipmentItem) {
                            return ShipmentItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-item.new', {
            parent: 'shipment-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item/shipment-item-dialog.html',
                    controller: 'ShipmentItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                longDescription: null,
                                price: null,
                                freeStorageEndingDate: null,
                                createdOn: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shipment-item', null, { reload: 'shipment-item' });
                }, function() {
                    $state.go('shipment-item');
                });
            }]
        })
        .state('shipment-item.edit', {
            parent: 'shipment-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item/shipment-item-dialog.html',
                    controller: 'ShipmentItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentItem', function(ShipmentItem) {
                            return ShipmentItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-item', null, { reload: 'shipment-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-item.delete', {
            parent: 'shipment-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item/shipment-item-delete-dialog.html',
                    controller: 'ShipmentItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ShipmentItem', function(ShipmentItem) {
                            return ShipmentItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-item', null, { reload: 'shipment-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
