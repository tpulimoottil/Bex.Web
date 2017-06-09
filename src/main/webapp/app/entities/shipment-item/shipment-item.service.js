(function() {
    'use strict';
    angular
        .module('bexWebApp')
        .factory('ShipmentItem', ShipmentItem);

    ShipmentItem.$inject = ['$resource', 'DateUtils'];

    function ShipmentItem ($resource, DateUtils) {
        var resourceUrl =  'api/shipment-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.freeStorageEndingDate = DateUtils.convertDateTimeFromServer(data.freeStorageEndingDate);
                        data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
