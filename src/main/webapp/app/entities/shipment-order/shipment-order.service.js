(function() {
    'use strict';
    angular
        .module('bexWebApp')
        .factory('ShipmentOrder', ShipmentOrder);

    ShipmentOrder.$inject = ['$resource', 'DateUtils'];

    function ShipmentOrder ($resource, DateUtils) {
        var resourceUrl =  'api/shipment-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
