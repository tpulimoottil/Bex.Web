(function() {
    'use strict';
    angular
        .module('bexWebApp')
        .factory('ShipmentOrderAudit', ShipmentOrderAudit);

    ShipmentOrderAudit.$inject = ['$resource', 'DateUtils'];

    function ShipmentOrderAudit ($resource, DateUtils) {
        var resourceUrl =  'api/shipment-order-audits/:id';

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
