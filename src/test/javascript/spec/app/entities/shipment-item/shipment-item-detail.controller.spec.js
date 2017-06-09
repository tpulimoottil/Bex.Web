'use strict';

describe('Controller Tests', function() {

    describe('ShipmentItem Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockShipmentItem, MockShipmentItemProperties, MockUser, MockShipmentItemCategory, MockShipmentOrder;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockShipmentItem = jasmine.createSpy('MockShipmentItem');
            MockShipmentItemProperties = jasmine.createSpy('MockShipmentItemProperties');
            MockUser = jasmine.createSpy('MockUser');
            MockShipmentItemCategory = jasmine.createSpy('MockShipmentItemCategory');
            MockShipmentOrder = jasmine.createSpy('MockShipmentOrder');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ShipmentItem': MockShipmentItem,
                'ShipmentItemProperties': MockShipmentItemProperties,
                'User': MockUser,
                'ShipmentItemCategory': MockShipmentItemCategory,
                'ShipmentOrder': MockShipmentOrder
            };
            createController = function() {
                $injector.get('$controller')("ShipmentItemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bexWebApp:shipmentItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
