'use strict';

describe('Controller Tests', function() {

    describe('ShipmentOrderAudit Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockShipmentOrderAudit, MockShipmentOrder, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockShipmentOrderAudit = jasmine.createSpy('MockShipmentOrderAudit');
            MockShipmentOrder = jasmine.createSpy('MockShipmentOrder');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ShipmentOrderAudit': MockShipmentOrderAudit,
                'ShipmentOrder': MockShipmentOrder,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ShipmentOrderAuditDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bexWebApp:shipmentOrderAuditUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
