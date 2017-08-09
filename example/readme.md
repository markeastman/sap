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

We have used spring security along with user roles to provent access to certain features of the application.
The `SapRoles` object holds a definition of all the features that we are allowing within the system.
Each feature is governed by a role that matches it and also a menu item that provides access to it. 
So for menu item XXX we have a role called XXX and we display the menu item
to the user if they have that role. 
In addition we have to prevent the controllers from accessing that feature if the user has not got it and they just enter the URL for a
known feature that they do not have. In this case we add a `antmatchers` row to the `httpsecurity` object. 
Note also that for a given user the roles are also based upon which company they have active.
The user can switch their active company after they have logged into the system.

To gain access to the whole site the user has to be authenticated. 
For now I just assume the password matches the userid. Within the mock data I have two users, admin and steve that
have different roles per companies.

For some entities such as invoice, we have overloaded the use of another role that matches a menu role. So for that 
menu role the user gets the menu and also an associated additional capability.

### Adding a new controller with security

If we start by looking at the `OrdersController` and the `InvoiceController` we want to secure access to the list of new orders and the invoice viewer using the
same security role. In the case we have created a `SapRoles.NEW_ORDERS` and have added it to a number of users within `MockData.

To protect the controller for the URL we add to the `httpSecurity` object within `SecurityConfiguration` a set of url patterns and an authority role string such as:

```
   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            ...
            .antMatchers( "/orders/**", "/invoices/**").hasAuthority(NEW_ORDERS)
            ...
```

Within `layout.html` we have the menu option for the features so we protect the menu item with:

```html
<li id="menu_orders" sec:authorize="hasAuthority('NEW_ORDERS')" ><a href="/orders"><i class="fa fa-cart-arrow-down"></i> <span>New Orders</span></a></li>
```

In summary then we create a role and add it to `SapRoles` and then assign it to users. We protect the
controller via the `SecurityConfguration` and finally prevent the link appearing in the menu by adding the `hasAuthority()`
attribute to the html menu entry.



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