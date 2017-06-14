(function() {
    'use strict';

    angular
        .module('bexWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('manage-address', {
            parent: 'entity',
            url: '/manage-address?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserAddresses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/userpages/manage-user-address/user-addresses.html',
                    controller: 'UserAddressController',
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
        .state('manage-address-detail', {
            parent: 'manage-address',
            url: '/manage-address/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserAddress'
            },
            views: {
                'content@': {
                    templateUrl: 'app/userpages/manage-user-address/user-address-detail.html',
                    controller: 'UserAddressDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserAddress', function($stateParams, UserAddress) {
                    return UserAddress.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'manage-address',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('manage-address-detail.edit', {
            parent: 'manage-address-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/userpages/manage-user-address/user-address-dialog.html',
                    controller: 'UserAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserAddress', function(UserAddress) {
                            return UserAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('manage-address.new', {
            parent: 'manage-address',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/userpages/manage-user-address/user-address-dialog.html',
                    controller: 'UserAddressDialogController',
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
                    $state.go('manage-address', null, { reload: 'manage-address' });
                }, function() {
                    $state.go('manage-address');
                });
            }]
        })
        .state('manage-address.edit', {
            parent: 'manage-address',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/userpages/manage-user-address/user-address-dialog.html',
                    controller: 'UserAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserAddress', function(UserAddress) {
                            return UserAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('manage-address', null, { reload: 'manage-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('manage-address.delete', {
            parent: 'manage-address',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/userpages/manage-user-address/user-address-delete-dialog.html',
                    controller: 'UserAddressDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserAddress', function(UserAddress) {
                            return UserAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('manage-address', null, { reload: 'manage-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
