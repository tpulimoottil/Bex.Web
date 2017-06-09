(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shipment-item-category', {
            parent: 'entity',
            url: '/shipment-item-category',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentItemCategories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-item-category/shipment-item-categories.html',
                    controller: 'ShipmentItemCategoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('shipment-item-category-detail', {
            parent: 'shipment-item-category',
            url: '/shipment-item-category/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ShipmentItemCategory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shipment-item-category/shipment-item-category-detail.html',
                    controller: 'ShipmentItemCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ShipmentItemCategory', function($stateParams, ShipmentItemCategory) {
                    return ShipmentItemCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shipment-item-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shipment-item-category-detail.edit', {
            parent: 'shipment-item-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item-category/shipment-item-category-dialog.html',
                    controller: 'ShipmentItemCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentItemCategory', function(ShipmentItemCategory) {
                            return ShipmentItemCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-item-category.new', {
            parent: 'shipment-item-category',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item-category/shipment-item-category-dialog.html',
                    controller: 'ShipmentItemCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shipment-item-category', null, { reload: 'shipment-item-category' });
                }, function() {
                    $state.go('shipment-item-category');
                });
            }]
        })
        .state('shipment-item-category.edit', {
            parent: 'shipment-item-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item-category/shipment-item-category-dialog.html',
                    controller: 'ShipmentItemCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShipmentItemCategory', function(ShipmentItemCategory) {
                            return ShipmentItemCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-item-category', null, { reload: 'shipment-item-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shipment-item-category.delete', {
            parent: 'shipment-item-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shipment-item-category/shipment-item-category-delete-dialog.html',
                    controller: 'ShipmentItemCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ShipmentItemCategory', function(ShipmentItemCategory) {
                            return ShipmentItemCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shipment-item-category', null, { reload: 'shipment-item-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
