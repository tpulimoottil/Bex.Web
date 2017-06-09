(function() {
    'use strict';
    angular
        .module('bexWebApp')
        .factory('ShipmentItemCategory', ShipmentItemCategory);

    ShipmentItemCategory.$inject = ['$resource'];

    function ShipmentItemCategory ($resource) {
        var resourceUrl =  'api/shipment-item-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
