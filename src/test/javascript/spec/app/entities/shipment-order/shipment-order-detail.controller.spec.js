'use strict';

describe('Controller Tests', function() {

    describe('ShipmentOrder Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockShipmentOrder, MockShipmentAddress, MockUser, MockShipmentOrderAudit, MockShipmentItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockShipmentOrder = jasmine.createSpy('MockShipmentOrder');
            MockShipmentAddress = jasmine.createSpy('MockShipmentAddress');
            MockUser = jasmine.createSpy('MockUser');
            MockShipmentOrderAudit = jasmine.createSpy('MockShipmentOrderAudit');
            MockShipmentItem = jasmine.createSpy('MockShipmentItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ShipmentOrder': MockShipmentOrder,
                'ShipmentAddress': MockShipmentAddress,
                'User': MockUser,
                'ShipmentOrderAudit': MockShipmentOrderAudit,
                'ShipmentItem': MockShipmentItem
            };
            createController = function() {
                $injector.get('$controller')("ShipmentOrderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bexWebApp:shipmentOrderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
