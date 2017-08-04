# Sap Example for Ascarii
This is a sample project for the ascaii team to look at the new proposed web based user interface the SAP b1 system

The main example has a java SapFacade interface to abstract the Sap B1 system itself from the rest of the application. To enable us to develop and test the web front end we have implemented a MockSapFacade class to provide the business logic and dummy data.

The web system uses springboot to provide the framework and then thymeleaf to provide the web layer.

In addition we have used [adminlte.io](http://adminlte.io) theme to provide a lot of the user widgets. We have also looked at 
[patternfly](http://patternfly.org) and copied some of the approaches for some of the lists and features.

## Security

We have used spring security along with user roles to provent access to certain features of the application. The SapRoles object holds a definition of all the features that we are allowing within the system. Each
feaature is governed by a role that matches it and also a menu item that provides access to it. So for menu item XXX we have a role called XXX and we display the menu item
to the user if they have that role. In addition we have to prevent the controllers from accessing that feature if the user has not got it and they just enter the URL for a
known feature that they do not have. In this case we add a antmatchers row to the httpsecurity object. Note also that for a given user the roles are also based upon which company they have active.
The user can switch their active company after they have logged into the system.

To gain access to the whole site the user has to be authenticated. For now I just assume the password matches the userid. Within the mock data I have two users, admin and steve that
have different roles per companies.

For some entities such as invoice, we have overloaded the use of another role that matches a menu role. So for that 
menu role the user gets the menu and also an associated additional capability.

## Screens so far
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
