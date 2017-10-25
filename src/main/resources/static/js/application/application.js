angular.module('crudApplication',['ui.router', 'ngResource', 'ngMaterial', 'ngMessages'])
    .config(function($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'userListView.html',
                controller:'userController',
                controllerAs:'ctrl'
            });

        $urlRouterProvider.otherwise('/');

    })
    .run(function() {
    });
