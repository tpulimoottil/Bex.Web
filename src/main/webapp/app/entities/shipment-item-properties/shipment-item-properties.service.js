(function() {
    'use strict';
    angular
        .module('bexWebApp')
        .factory('ShipmentItemProperties', ShipmentItemProperties);

    ShipmentItemProperties.$inject = ['$resource'];

    function ShipmentItemProperties ($resource) {
        var resourceUrl =  'api/shipment-item-properties/:id';

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
