(function() {
    'use strict';
    angular
        .module('bexWebApp')
        .factory('ShipmentAddress', ShipmentAddress);

    ShipmentAddress.$inject = ['$resource'];

    function ShipmentAddress ($resource) {
        var resourceUrl =  'api/shipment-addresses/:id';

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
