'use strict';

angular.module('crudApplication').controller('userController', function($scope, $mdDialog, userService){
    var self = this;

    $scope.users = [];
    $scope.edit = editUser;
    $scope.delete = deleteUser;
    $scope.add = addUser;

    function loadUsers(){
        userService.getAllUsers().$promise.then(function(users) {
            $scope.users = users;
        });
    };

    function userFormDialogController($scope, $mdDialog, title, user) {
        $scope.userFormDialog = {
            title: title,
            user: user,
            addUser: function() {
                userService.save(user).$promise.then(function(){
                    $mdDialog.hide();
                    loadUsers();
                });
            },
            editUser: function() {
                var requestParams = angular.copy(user);
                requestParams.userId = user.id;
                userService.update(requestParams).$promise.then(function() {
                    $mdDialog.hide();
                    loadUsers();
                });
            }
        };
        $scope.userFormDialog.cancel = function() {
            $mdDialog.cancel();
        }
    };

    function showEditView(event, title, user) {
        $mdDialog.show({
            controller: userFormDialogController,
            locals: {
                title: title,
                user: user
            },
            templateUrl: 'userForm.html',
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose:true
        });
    }

    function addUser(event) {
        var user = {
            firstName: null,
            lastName: null,
            age: null
        };
        showEditView(event, "Dodanie nowego u\u017Cytkownika", user);
    };

    function editUser(id) {
        var params = {
            userId: id
        };
        userService.get(params).$promise.then(function (user) {
            var user = user;
            showEditView(event, "Edycja u\u017Cytkownika", user);
        });
    };

    loadUsers();
});