'use strict';
angular.module('crudApplication').service('userService', function ($resource) {
    var resource = $resource('api/user/:userId',
        {userId: '@userId'},
        {update: {
            method: 'PUT',
            isArray: false
        }});

    return {
        getAllUsers: function (params) {
            return resource.query(params);
        },
        get: function(params) {
            return resource.get(params);
        },
        delete: function(params) {
            return resource.delete(params);
        },
        save: function(params) {
            return resource.save(params);
        },
        update: function(params) {
            return resource.update(params);
        }
    }
});