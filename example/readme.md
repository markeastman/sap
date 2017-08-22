# Sap Example for Ascarii
This is a sample project for the ascaii team to look at the new proposed web based user interface the SAP b1 system

The main example has a java `SapFacade` interface to abstract the Sap B1 system itself from the rest of the application.
To enable us to develop and test the web front end we have implemented a `MockSapFacade` class to provide the business logic and dummy data.

The web system uses springboot to provide the framework and then thymeleaf to provide the web layer templating.

In addition we have used [adminlte.io](http://adminlte.io) theme to provide a lot of the user widgets. We have also looked at 
[patternfly](http://patternfly.org) and copied some of the approaches for some of the lists and features.

## Useful Links for icons etc.
[AdminLte.io](http://adminlte.io)

[patternfly.org](http://patternfly.org)

[fontawesome.io](http://fontawesome.io/icons/)

[ionicons.io](http://ionicons.com)

[glyphicons.com](http://glyphicons.com)

## Security

### Authentication

For this system we will want to provide a custom authentication mechanism to `spring` as we 
will need to authenticate a user against the backend Sap B1 system.

Within `SecurityConfiguration` we register a custom authentication provider with a class of `SapAuthenticationProvider` as per:

````
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
````
Part of the authentication process is the use of a user token, in psring for a standard user password basic authication mechanism
the `UserPasswordAuthenticationToken` is used to pass the user id and credentials. Within our provider we need to 
indicate to the authentication process that we can process this token and provide authentication. However we want to 
handle a different token once logged in so we extend the `UserPasswordAuthenticationToken` by a class called `SapAuthenticationToken`.
`SapAuthenticationToken`. The reason for having our own is to handle the case when the user changes their company
which in turn may change the set of roles they have. For normal `spring` the set of roles for a user does not normally change 
over the period of the login.

Within the `SapAuthenticationProvider` we get the initial `UserPasswordAuthenticationToken` and check to see if the 
user is already authenticated. For a normal logon process this will be false, so we go through the 
`SapFacade` to authenticate against the backend. Upon success we then create a new `SapAuthenticationToken`
with the details from the logged on user. At times we have found that the system can check the authentication object again
however this time it will be the   `SapAuthenticationToken` that is passed in and it will have the authenticated property set to true.
In this scenario we just return the existing token.

We have trapped the following authentication aspects: disabled account, locked out account, 
expired account, credentials have expired and need refreshing. For the first three we return the user to the login page again
with a message which they will need to phone up and get sorted I guess. For the last aspect we 
have trapped this and sent the user back to the login page but witha flag indicating they need changing. 
I have written a different form within the same server page for this situation.

````html
<div th:if="${changePassword}">
    <p>Need to do change password dialog</p>
</div>
<div th:if="${changePassword == null}" class="login-box-body">
    <p class="login-box-msg">Sign in to start your session</p>
````
### Authorization

We have used spring security along with user roles to prevent access to certain features of the application.
The `SapRoles` object holds a definition of all the features that we are allowing within the system.
Each feature is governed by a role that matches it and also a menu item that provides access to it. 
So for logical menu item XXX we have a role called ROLE_XXX and we display the menu item
to the user if they have that role. 
In addition we want to secure the controller for the URLs that match that feature. We use `@Secured` to manage access to the controller functions.
Note also that for a given user the roles are also based upon which company they have active.
The user can switch their active company after they have logged into the system.

To gain access to the whole site the user has to be authenticated. 
For now I just assume the password matches the userid. Within the mock data I have two users, admin and steve that
have different roles per companies.

For some entities such as invoice, we have overloaded the use of another role that matches a menu role. So for that 
menu role the user gets the menu and also an associated additional capability.

#### Adding a new controller with security

Within `layout.html` we have the menu option for the features so we protect the menu item with:

```html
<li id="menu_orders" sec:authorize="hasRole('ROLE_ORDERS')" ><a href="/orders"><i class="fa fa-cart-arrow-down"></i> <span>New Orders</span></a></li>
```

In summary then we create a role and add it to `SapRoles` and then assign it to users. We protect the
controller via the `@Secured("ROLE_ORDERS")` annotation and finally prevent the link appearing in the menu by adding the `hasRole()`
attribute to the html menu entry.

### Tracking global aspects
When a user is logged on we can display at the top of the page the outstanding messages for him or similar. 
We could add this processing to every controller and insert into the model the relevant code, however this is
a little redundant and a better solution is provided by springboot.
Using `ÌnterceptorConfig` it is possible to register code that will get executed prior to the controller 
being invoked or after the controller is invoked.

We have 
````java
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new UserNotificationInterceptor()).addPathPatterns(
                "/home", "/dashboard", "/admin/**", "/orders/**", "/invoice/**", "/users/**");
    }
}
````
As we add more controllers to the application we will need to add additional entries to the pattern list.
I have thought about adding an "/app/" prefix to all the controllers so that we can register one pattern, but in some ways that does not look right.

You can see that this registers a `UserNotificationInterceptor` which will checks the logged on user and get the messages for them from the `SapFacade`, 
however for now it just generates a random set of messages each time the controller changes.

````java
public class UserNotificationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // We need to get some messages, events etc for this user
            if (authentication.getPrincipal() instanceof CurrentUser) {
                CurrentUser user = (CurrentUser) authentication.getPrincipal();
                // Need to get the messages for this user and output them into the model
                List<String> messages = new ArrayList<>();
                // Add a random number of messages
                int messageCount = (int)(Math.random() * 10);
                for (int i = 0; i < messageCount; i++)
                    messages.add( "Another message" );
                modelAndView.getModel().put("userMessages", messages);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
````

## Screens so Far
### Dashboard
This is the landing page for all users and it displays KPIs for the active company.

### Admin
This is a special page for admin users only and allows the user to interact with the springboot actuator for looking at key information.

### New Orders
This is a sample list screen that would show the last orders that the company have received. It would be governed by some date ot time restriction.
The listed objects are contained in logical rows that are panel displayed rather than a simple single row of data per order.

### Invoice
From one of the orders we can see the associated invoice that has been sent to the customer. Note for the invoice, there is also a print button
that shows a specific printer friendly page as an example of print capability.

### User Profile
This is an example of a user record that can be use as a basis for the HR record system. There seems
to be a position to place profile specific information, possible timeline material such as training or certification
certificates, possble health ans safty exams etc.

Not sure much of the social aspects are really relevant.

## Other Patterns

1. We need to show a data entry form / edit form which can display an entity with the fields that 
are manadatory, which ones are optional. The form entry process needs to reflect how errors
will be displayed back to the user, what validation aspects will be utilised for the management of the form fields etc. How much pre validation will be done
prior to submitting across the Facade to b1. We will then have to also handle the b1 messages as we may have additional
validation performed at that layer which has not been done at the front end layer.

1. Do we need to have a tree / hierarchy control that will show say an organisation structure. Could be the relationship of cost centers for example?

1. Do we need to have any conversatonal type displays. These involve a sequence of messages going back between people. May display on the web page a bit like skype?

1. Do we need to have a timeline display, which may show a workflow process showing
when things happened?

